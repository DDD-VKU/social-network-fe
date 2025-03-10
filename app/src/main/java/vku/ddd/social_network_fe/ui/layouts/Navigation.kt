package vku.ddd.social_network_fe.ui.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import vku.ddd.social_network_fe.ui.components.Common
import vku.ddd.social_network_fe.ui.components.TopNavItem
import vku.ddd.social_network_fe.ui.screens.auth.LoginScreen
import vku.ddd.social_network_fe.ui.screens.home.*

@Composable
fun Navigation(navController: NavHostController, mainNavigation: Boolean) {
    val items = listOf(
        TopNavItem(name = "Home", route = "home", icon = Icons.Filled.Home),
        TopNavItem(name = "Follow", route = "following_suggestion", icon = Icons.Filled.Person),
        TopNavItem(name = "Message", route = "message", icon = Icons.Filled.MailOutline),
        TopNavItem(name = "Notification", route = "notification", icon = Icons.Filled.Notifications),
        TopNavItem(name = "Menu", route = "menu", icon = Icons.Filled.Menu)
    )

    Column {
        if (mainNavigation) {
            var currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (!(currentRoute == "profile" ||
                currentRoute == "post" ||
                currentRoute == "search" ||
                currentRoute == "post-create" ||
                currentRoute == "image-detail")) {
                TopNavigationBar(items, navController, onItemClick = { item ->
                    navController.navigate(item.route)
                })
            }
        }

        NavHost(
            navController = navController,
            startDestination = if (mainNavigation) "home" else "post-search"
        ) {
            if (mainNavigation) {
                composable("home") { HomeScreen(navController) }
                composable("following_suggestion") { FollowingSuggestionScreen(navController) }
                composable("message") { MessageScreen() }
                composable("notification") { NotificationScreen(navController) }
                composable("menu") { MenuScreen(navController) }
                composable("profile") { ProfileScreen(navController) }
                composable("post") { PostScreen(navController) }
                composable("search") { SearchScreen(navController) }
                composable("post-create") { CreateUpdatePostScreen(navController) }
                composable("image-detail") { Common.ImageDetail(navController) }
                composable("login"){ LoginScreen(navController) }
            } else {
                composable("post-search") { PostSearchScreen(navController) }
                composable("user-search") { UserSearchScreen(navController) }
            }
        }
    }
}
