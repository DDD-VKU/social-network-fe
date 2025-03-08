package vku.ddd.social_network_fe.ui.layouts

import android.os.Message
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import vku.ddd.social_network_fe.ui.components.Common
import vku.ddd.social_network_fe.ui.components.TopNavItem
import vku.ddd.social_network_fe.ui.screens.home.*

@Composable
fun Navigation(navController: NavHostController, mainNavigation: Boolean) {
    val items = listOf(
        TopNavItem(name = "home", route = "Home", icon = Icons.Filled.Home),
        TopNavItem(name = "search", route = "Search", icon = Icons.Filled.Search),
        TopNavItem(name = "message", route = "Message", icon = Icons.Filled.MailOutline),
        TopNavItem(name = "notification", route = "Notification", icon = Icons.Filled.Notifications),
        TopNavItem(name = "menu", route = "Menu", icon = Icons.Filled.Menu)
    )

    Column {
        if (mainNavigation) {
            TopNavigationBar(items, navController, onItemClick = { item ->
                navController.navigate(item.route)
            })
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
            } else {
                composable("post-search") { PostSearchScreen(navController) }
                composable("user-search") { UserSearchScreen(navController) }
            }
        }
    }
}
