package vku.ddd.social_network_fe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import kotlin.math.round


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            Scaffold (
                topBar = {
                        TopNavigationBar(
                            listOf(
                                TopNavItem(
                                    name = "Home",
                                    route = "home",
                                    icon = Icons.Default.Home
                                ),
                                TopNavItem(
                                    name = "Following suggestion",
                                    route = "following_suggestion",
                                    icon = Icons.Default.Person
                                ),
                                TopNavItem(
                                    name = "Message",
                                    route = "message",
                                    icon = Icons.Default.MailOutline
                                ),
                                TopNavItem(
                                    name = "Notification",
                                    route = "notification",
                                    icon = Icons.Default.Notifications
                                ),
                                TopNavItem(
                                    name = "Menu",
                                    route = "menu",
                                    icon = Icons.Default.Menu
                                )
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                }
            ) {
                innerPadding ->
                Column (Modifier.padding(innerPadding), verticalArrangement = Arrangement.Bottom) {
                    Navigation(navController = navController)
                }

            }
        }
    }
}

@Composable
fun scaffoldComponent() {
    var text by remember { mutableStateOf("Hello") }
    var snackbarHostState = remember { SnackbarHostState() }
    var scope = rememberCoroutineScope()
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            TextField(
                value = text,
                onValueChange = {text = it},
                label = { Text("Label") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(20.dp))
            Button(onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = text,
                        actionLabel = "Cancle",
                        duration = SnackbarDuration.Short
                    )
                }
            }) {
                Text("Click me")
            }
        }
    }
}

@Composable
fun lazyColumnComponent() {
    LazyColumn (
        modifier = Modifier.fillMaxSize()
    ) {
        items (60) {
                i -> Text(text = "Number $i", fontSize = 30.sp)
        }
    }
}
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
        modifier = Modifier,
        backgroundColor = Color.White,
        elevation = 5.dp,

    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                modifier = Modifier.padding(top = 15.dp),
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
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }
        composable("following_suggestion") {
            FollowingSuggestionScreen()
        }
        composable("message") {
            MessageScreen()
        }
        composable("notification") {
            NotificationScreen()
        }
        composable("menu") {
            MenuScreen()
        }
    }
}
@Composable
fun HomeScreen() {
    LazyColumn (
        modifier = Modifier.fillMaxSize()

    ) {
        items (20) {
                i -> Post1Image()
                    Post2Images()
                    Post3Images()
                    Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
        }
    }
}
@Composable
fun FollowingSuggestionScreen() {
    LazyColumn (
        modifier = Modifier.fillMaxSize()

    ) {
        items (60) {
                i -> Column {
                    var isFollowing = remember { mutableStateOf<Boolean>(false) }
            Row (
                modifier = Modifier
                    .background(Color(0xFFE7F8FF))
                    .padding(top = 10.dp, bottom = 3.dp)
                    .wrapContentHeight()
                    .background(Color(0xFFE7F8FF))
                    .fillParentMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Box (
                    modifier = Modifier
                        .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                        .size(56.dp)
                ) {  }
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(text = "Nguyễn Văn A", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(text = "30 followers", fontSize = 20.sp, color = Color.Gray)
                    Button(
                        onClick = {
                            isFollowing.value = !isFollowing.value
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isFollowing.value) Color.White else Color.Blue,
                            contentColor = if (isFollowing.value) Color.White else Color.Blue
                        ),
                        border = if (isFollowing.value) BorderStroke(1.dp, Color.Black) else null
                    ) {
                        Text(text = if (isFollowing.value) "Followed" else "Follow", color = if (isFollowing.value) Color.Blue else Color.White)
                    }
                }
            }
            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
        }
        }
    }
}
@Composable
fun MessageScreen() {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Message", fontSize = 20.sp)
    }
}
@Composable
fun NotificationScreen() {
    LazyColumn (
        modifier = Modifier.fillMaxSize()

    ) {
        items (60) {
            i -> Column {
                Row (
                    modifier = Modifier
                        .background(Color(0xFFE7F8FF))
                        .padding(top = 10.dp, bottom = 3.dp)
                        .wrapContentHeight()
                        .background(Color(0xFFE7F8FF))
                        .fillParentMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Box (
                        modifier = Modifier
                            .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                            .size(40.dp)
                    ) {  }
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(text = "Nguyễn Văn A đã thích bài viết #$i của bạn", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(text = "2020-04-04 19:00:00")

                    }
                }
                Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
            }
        }
    }
}
@Composable
fun MenuScreen() {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Menu", fontSize = 20.sp)
    }
}
@Composable
fun HeadBar() {
    Row {
        Button(onClick = {}, modifier = Modifier.height(20.dp).width(50.dp)) {
            Icon (
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Add"
            )
            Text(text = "Create new post")
        }
    }
}
@Composable
fun Post1Image() {
    Column {
        Spacer(Modifier.height(6.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 5.dp)
        ) {
            Box (
                modifier = Modifier
                    .size(40.dp)
                    .border(1.dp, Color.Black, shape = CircleShape)
            ) {  }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(text = "Nguyễn Văn A", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "2020/04/04 19:00:00")
            }
        }
        Text (
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
        )
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 0.dp, max = 400.dp)
        ) {
            Image(
                painterResource(R.drawable.hust),
                contentDescription = "Background Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row (
            modifier = Modifier
        ) {
            var isLiked = remember { mutableStateOf(false) }
            Button(
                onClick = {
                    isLiked.value = !isLiked.value
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, // Màu xanh Facebook
                    contentColor = Color.White
                ),
                shape = RectangleShape, // Bo tròn viền
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ThumbUp, // Biểu tượng Like
                    contentDescription = "Like",
                    tint = if (isLiked.value) Color.Blue else Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Like", color = if (isLiked.value) Color.Blue else Color.Gray, fontSize = 14.sp)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, // Màu xanh Facebook
                    contentColor = Color.White
                ),
                shape = RectangleShape, // Bo tròn viền
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChatBubbleOutline, // Biểu tượng Like
                    contentDescription = "Comment",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Comment", color = Color.Gray, fontSize = 14.sp)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.White
                ),
                shape = RectangleShape,
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Repeat,
                    contentDescription = "Share",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Share", color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}
@Composable
fun Post2Images() {
    Column {
        Spacer(Modifier.height(6.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 5.dp)
        ) {
            Box (
                modifier = Modifier
                    .size(40.dp)
                    .border(1.dp, Color.Black, shape = CircleShape)
            ) {  }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(text = "Nguyễn Văn A", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "2020/04/04 19:00:00")
            }
        }
        Text (
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
        )
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth()
                .heightIn(min = 0.dp, max = 400.dp)

        ) {
            items(2) {
                i ->
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 0.dp, max = 400.dp)
                ) {
                    Image(
                        painterResource(if (i == 0) R.drawable.hcmus else R.drawable.hust),
                        contentDescription = "Background Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Row (
            modifier = Modifier
        ) {
            var isLiked = remember { mutableStateOf(false) }
            Button(
                onClick = {
                    isLiked.value = !isLiked.value
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, // Màu xanh Facebook
                    contentColor = Color.White
                ),
                shape = RectangleShape, // Bo tròn viền
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ThumbUp, // Biểu tượng Like
                    contentDescription = "Like",
                    tint = if (isLiked.value) Color.Blue else Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Like", color = if (isLiked.value) Color.Blue else Color.Gray, fontSize = 14.sp)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, // Màu xanh Facebook
                    contentColor = Color.White
                ),
                shape = RectangleShape, // Bo tròn viền
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChatBubbleOutline, // Biểu tượng Like
                    contentDescription = "Comment",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Comment", color = Color.Gray, fontSize = 14.sp)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.White
                ),
                shape = RectangleShape,
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Repeat,
                    contentDescription = "Share",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Like", color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}
@Composable
fun Post3Images() {
    Column {
        Spacer(Modifier.height(6.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 5.dp)
        ) {
            Box (
                modifier = Modifier
                    .size(40.dp)
                    .border(1.dp, Color.Black, shape = CircleShape)
            ) {  }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(text = "Nguyễn Văn A", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "2020/04/04 19:00:00")
            }
        }
        Text (
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
        )
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth()
                .heightIn(min = 0.dp, max = 400.dp)

        ) {
            items(3) {
                    i ->
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 0.dp, max = 400.dp),
                ) {
                    Image(
                        painterResource(if (i == 0) R.drawable.hcmus
                                else if (i == 2) R.drawable.hust
                                else R.drawable.uet),
                        contentDescription = "Background Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Row (
            modifier = Modifier
        ) {
            var isLiked = remember { mutableStateOf(false) }
            Button(
                onClick = {
                    isLiked.value = !isLiked.value
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, // Màu xanh Facebook
                    contentColor = Color.White
                ),
                shape = RectangleShape, // Bo tròn viền
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ThumbUp, // Biểu tượng Like
                    contentDescription = "Like",
                    tint = if (isLiked.value) Color.Blue else Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Like", color = if (isLiked.value) Color.Blue else Color.Gray, fontSize = 14.sp)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, // Màu xanh Facebook
                    contentColor = Color.White
                ),
                shape = RectangleShape, // Bo tròn viền
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChatBubbleOutline, // Biểu tượng Like
                    contentDescription = "Comment",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Comment", color = Color.Gray, fontSize = 14.sp)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.White
                ),
                shape = RectangleShape,
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Repeat,
                    contentDescription = "Share",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Like", color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}
@Composable
fun Profile() {
    Column {

    }
}