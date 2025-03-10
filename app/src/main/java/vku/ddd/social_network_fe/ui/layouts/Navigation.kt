package vku.ddd.social_network_fe.ui.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import vku.ddd.social_network_fe.ui.components.Common
import vku.ddd.social_network_fe.ui.components.TopNavItem
import vku.ddd.social_network_fe.ui.screens.auth.LoginScreen
import vku.ddd.social_network_fe.ui.screens.home.*

@Composable
fun Navigation(globalNavController: NavHostController, searchNavController: NavHostController?, mainNavigation: Boolean) {
    val items = listOf(
        TopNavItem(name = "Home", route = "home", icon = Icons.Filled.Home),
        TopNavItem(name = "Follow", route = "following_suggestion", icon = Icons.Filled.Person),
        TopNavItem(name = "Message", route = "message", icon = Icons.Filled.MailOutline),
        TopNavItem(name = "Notification", route = "notification", icon = Icons.Filled.Notifications),
        TopNavItem(name = "Menu", route = "menu", icon = Icons.Filled.Menu)
    )

    Column {
        if (mainNavigation) {
            var currentRoute = globalNavController.currentBackStackEntryAsState().value?.destination?.route
            if (!(currentRoute == "profile" ||
                currentRoute == "post" ||
                currentRoute == "search" ||
                currentRoute == "post-create" ||
                currentRoute == "image-detail")) {
                Column (
                    modifier = Modifier
                        .shadow(elevation = 5.dp)
                ) {
                    if (currentRoute == "home") {
                        Row (
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxWidth()
                                .windowInsetsPadding(WindowInsets.statusBars)
                                .padding(start = 16.dp, end = 16.dp, top = 6.dp)
                        ) {
                            Text (
                                text = "Footnote",
                                color = Color.Blue,
                                fontSize = 32.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold
                            )
                            Row {
                                Button(
                                    modifier = Modifier.size(44.dp),
                                    onClick = {
                                        globalNavController.navigate("post-create")
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = Color.Black,
                                        containerColor = Color.Transparent
                                    ),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Icon (
                                        imageVector = Icons.Filled.AddCircle,
                                        contentDescription = "Create post",
                                        Modifier.size(24.dp)
                                    )
                                }
                                Button(
                                    modifier = Modifier.size(44.dp),
                                    onClick = {
                                        globalNavController.navigate("search")
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = Color.Black,
                                        containerColor = Color.Transparent
                                    ),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Icon (
                                        imageVector = Icons.Outlined.Search,
                                        contentDescription = "Search",
                                        Modifier.size(28.dp)
                                    )
                                }
                            }
                        }
                    }
                    TopNavigationBar(items, globalNavController, onItemClick = { item ->
                        globalNavController.navigate(item.route)
                    })
                }

            }
        }

        NavHost(
            navController = if (mainNavigation) globalNavController else searchNavController!!,
            startDestination = if (mainNavigation) "home" else "post-search"
        ) {
            if (mainNavigation) {
                composable("home") { HomeScreen(globalNavController) }
                composable("following_suggestion") { FollowingSuggestionScreen(globalNavController) }
                composable("message") { MessageScreen() }
                composable("notification") { NotificationScreen(globalNavController) }
                composable("menu") { MenuScreen(globalNavController) }
                composable("profile") { ProfileScreen(globalNavController) }
                composable("post") { PostScreen(globalNavController) }
                composable("search") { SearchScreen(globalNavController) }
                composable("post-create") { CreateUpdatePostScreen(globalNavController) }
                composable("image-detail") { Common.ImageDetail(globalNavController) }
                composable("login"){ LoginScreen(globalNavController) }
            } else {
                composable("post-search") { PostSearchScreen(globalNavController) }
                composable("user-search") { UserSearchScreen(globalNavController) }
            }
        }
    }
}
