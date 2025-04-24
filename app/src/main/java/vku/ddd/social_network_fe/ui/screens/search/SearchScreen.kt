package vku.ddd.social_network_fe.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import vku.ddd.social_network_fe.ui.components.TopNavItem
import vku.ddd.social_network_fe.ui.layouts.Navigation
import vku.ddd.social_network_fe.ui.layouts.TopNavigationBar

@Composable
fun SearchScreen(navController: NavHostController) {
    val searchNavController = rememberNavController()
    val searchString = remember { mutableStateOf("") }
    var currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold (
        modifier = Modifier
            .fillMaxSize()
        ,
        topBar = {
            Column { // Bọc cả TopAppBar và TopNavigationBar vào Column
                TopAppBar(
                    backgroundColor = Color.White,
                    elevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.statusBars)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBackIosNew,
                                contentDescription = "Back"
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        TextField(
                            value = searchString.value,
                            onValueChange = {
                                searchString.value = it
                            },
                            placeholder = { Text("Search...") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                // Đưa TopNavigationBar ra ngoài TopAppBar
                TopNavigationBar(
                    listOf(
                        TopNavItem(
                            name = "Post",
                            route = "post-search",
                            icon = Icons.Default.Article
                        ),
                        TopNavItem(
                            name = "User",
                            route = "user-search",
                            icon = Icons.Default.Person
                        ),
                    ),
                    navController = navController,
                    onItemClick = {
                        searchNavController.navigate(it.route)
                    }
                )
            }
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        Column (Modifier.padding(innerPadding), verticalArrangement = Arrangement.Bottom) {
            Navigation(globalNavController = navController, searchNavController, false)
        }
    }
}