package vku.ddd.social_network_fe.ui.components

import androidx.compose.ui.graphics.vector.ImageVector

data class TopNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
    val badgeCount: Int = 0
)
