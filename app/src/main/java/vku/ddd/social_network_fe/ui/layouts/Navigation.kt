package vku.ddd.social_network_fe.ui.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.google.gson.Gson
import vku.ddd.social_network_fe.data.api.RetrofitClient
import vku.ddd.social_network_fe.data.datastore.TokenDataStore
import vku.ddd.social_network_fe.data.model.Post
import vku.ddd.social_network_fe.ui.components.Common
import vku.ddd.social_network_fe.ui.components.TopNavItem
import vku.ddd.social_network_fe.ui.screens.auth.LoginScreen
import vku.ddd.social_network_fe.ui.screens.auth.RegisterScreen
import vku.ddd.social_network_fe.ui.screens.home.*
import vku.ddd.social_network_fe.ui.screens.menu.MenuScreen
import vku.ddd.social_network_fe.ui.screens.menu.SettingScreen
import vku.ddd.social_network_fe.ui.screens.notification.NotificationScreen
import vku.ddd.social_network_fe.ui.screens.post.ListImageScreen
import vku.ddd.social_network_fe.ui.screens.post.PostScreen
import vku.ddd.social_network_fe.ui.screens.profile.EditProfileScreen
import vku.ddd.social_network_fe.ui.screens.profile.ProfileScreen
import vku.ddd.social_network_fe.ui.screens.search.PostSearchScreen
import vku.ddd.social_network_fe.ui.screens.search.SearchScreen
import vku.ddd.social_network_fe.ui.screens.search.UserSearchScreen
import java.net.URLDecoder

@Composable
fun Navigation(
    globalNavController: NavHostController,
    searchNavController: NavHostController?,
    mainNavigation: Boolean
) {
    val context = LocalContext.current
    val items = listOf(
        TopNavItem("Home", "home", Icons.Filled.Home),
        TopNavItem("Follow", "following_suggestion", Icons.Filled.Person),
        TopNavItem("Message", "message", Icons.Filled.MailOutline),
        TopNavItem("Notification", "notification", Icons.Filled.Notifications),
        TopNavItem("Menu", "menu", Icons.Filled.Menu)
    )
    val toLoginScreen = remember { mutableStateOf(false) }
    val isReady = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val loginAccount: String? = TokenDataStore(context).getToken()
        toLoginScreen.value = loginAccount == null
        TokenDataStore(context).getToken()?.let { token ->
            RetrofitClient.setToken(token)
        }
        isReady.value = true
    }

    Column {
        // Giữ nguyên giao diện header và bottom tabs khi mainNavigation = true
        if (mainNavigation) {
            val currentRoute = globalNavController.currentBackStackEntryAsState().value?.destination?.route
            if (!(currentRoute == "profile" ||
                        (currentRoute?.startsWith("post/") == true) ||
                        currentRoute == "search" ||
                        currentRoute == "post-create" ||
                        currentRoute == "login" ||
                        currentRoute == "register" ||
                        currentRoute == "setting" ||
                        currentRoute == "list-image" ||
                        currentRoute == "edit_profile" ||
                        currentRoute?.startsWith("post-update/") == true ||
                        currentRoute?.startsWith("profile/") == true ||
                        currentRoute?.startsWith("image-detail/") == true)) {
                Column(
                    modifier = Modifier
                        .shadow(elevation = 5.dp)
                ) {
                    if (currentRoute == "home") {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxWidth()
                                .windowInsetsPadding(WindowInsets.statusBars)
                                .padding(start = 16.dp, end = 16.dp, top = 6.dp)
                        ) {
                            Text(
                                text = "Footnote",
                                color = Color.Blue,
                                fontSize = 32.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold
                            )
                            Row {
                                Button(
                                    modifier = Modifier.size(44.dp),
                                    onClick = { globalNavController.navigate("post-create") },
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = Color.Black,
                                        containerColor = Color.Transparent
                                    ),
                                    contentPadding = PaddingValues(0.dp),
                                    shape = CircleShape
                                ) {
                                    Icon(Icons.Filled.AddCircle, contentDescription = "Create post")
                                }
                                Button(
                                    modifier = Modifier.size(44.dp),
                                    onClick = { globalNavController.navigate("search") },
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = Color.Black,
                                        containerColor = Color.Transparent
                                    ),
                                    contentPadding = PaddingValues(0.dp),
                                    shape = CircleShape
                                ) {
                                    Icon(Icons.Outlined.Search, contentDescription = "Search")
                                }
                            }
                        }
                    }
                    TopNavigationBar(
                        items = items,
                        navController = globalNavController,
                        onItemClick = { item ->
                            globalNavController.navigate(item.route)
                        }
                    )
                }
            }
        }

        // Đợi dữ liệu sẵn sàng rồi mới render NavHost
        if (isReady.value) {
            NavHost(
                navController = if (mainNavigation) globalNavController else searchNavController!!,
                startDestination = if (mainNavigation) {
                    if (toLoginScreen.value) "login" else "home"
                } else {
                    "post-search/{query}"
                },
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                val gson = Gson()

                if (mainNavigation) {
                    // Main app navigation
                    composable("home") { HomeScreen(globalNavController) }
                    composable("following_suggestion") { FollowingSuggestionScreen(globalNavController) }
                    composable("message") { MessageScreen() }
                    composable("notification") { NotificationScreen(globalNavController) }
                    composable("menu") { MenuScreen(globalNavController) }
                    composable("edit_profile") { EditProfileScreen(globalNavController) }

                    composable(
                        "profile/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.LongType })
                    ) { entry ->
                        entry.arguments?.getLong("id")?.let { ProfileScreen(globalNavController, it) }
                    }

                    composable(
                        "post/{postJson}",
                        arguments = listOf(navArgument("postJson") { type = NavType.StringType })
                    ) { entry ->
                        val decoded = URLDecoder.decode(entry.arguments?.getString("postJson") ?: "", "UTF-8")
                        gson.fromJson(decoded, Post::class.java).let { PostScreen(globalNavController, it) }
                    }

                    composable("search") { SearchScreen(globalNavController) }
                    composable("post-create") { CreatePostScreen(globalNavController) }

                    composable(
                        "post-update/{postJson}",
                        arguments = listOf(navArgument("postJson") { type = NavType.StringType })
                    ) { entry ->
                        val decoded = URLDecoder.decode(entry.arguments?.getString("postJson") ?: "", "UTF-8")
                        gson.fromJson(decoded, Post::class.java).let { UpdatePostScreen(globalNavController, it) }
                    }

                    composable(
                        "image-detail/{postJson}",
                        arguments = listOf(navArgument("postJson") { type = NavType.StringType })
                    ) { entry ->
                        val decoded = URLDecoder.decode(entry.arguments?.getString("postJson") ?: "", "UTF-8")
                        gson.fromJson(decoded, Post::class.java).let { Common.ImageDetail(globalNavController, it) }
                    }

                    composable("setting") { SettingScreen(globalNavController) }
                    composable(
                        "list-image/{postJson}",
                        arguments = listOf(navArgument("postJson") { type = NavType.StringType })
                    ) { entry ->
                        val decoded = URLDecoder.decode(entry.arguments?.getString("postJson") ?: "", "UTF-8")
                        gson.fromJson(decoded, Post::class.java).let { ListImageScreen(globalNavController, it) }
                    }

                    composable("login") { LoginScreen(globalNavController) }
                    composable("register") { RegisterScreen(globalNavController) }
                } else {
                    // Search-specific navigation
                    composable(
                        "post-search/{query}",
                        arguments = listOf(navArgument("query") { type = NavType.StringType; defaultValue = "" })
                    ) { entry ->
                        val query = entry.arguments?.getString("query") ?: ""
                        PostSearchScreen(globalNavController, query)
                    }

                    composable(
                        "user-search/{query}",
                        arguments = listOf(navArgument("query") { type = NavType.StringType; defaultValue = "" })
                    ) { entry ->
                        val query = entry.arguments?.getString("query") ?: ""
                        UserSearchScreen(globalNavController, query)
                    }
                }
            }
        }
    }
}
