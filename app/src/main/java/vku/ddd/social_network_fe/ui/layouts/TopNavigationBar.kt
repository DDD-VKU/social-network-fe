package vku.ddd.social_network_fe.ui.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import vku.ddd.social_network_fe.ui.components.TopNavItem

@Composable
fun TopNavigationBar(
    items: List<TopNavItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onItemClick: (TopNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
//    HeadBar()
    BottomNavigation (
        modifier = Modifier
            .offset(y = -4.dp),
        backgroundColor = Color.White,
        elevation = 0.dp,

        ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                modifier = Modifier.padding(top = 10.dp),
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Black,
                icon = {
                    Column (horizontalAlignment = Alignment.CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(
                                badge = {
                                    Text(text =  item.badgeCount.toString())
                                }
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name,
                                    tint = if (selected) Color.Blue else Color.Black
                                )
                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name,
                                tint = if (selected) Color.Blue else Color.Black
                            )
                        }
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                color = Color.Blue
                            )
                        }
                    }
                }
            )
        }
    }
}