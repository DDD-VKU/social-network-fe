@file:OptIn(ExperimentalLayoutApi::class)

package vku.ddd.social_network_fe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {

            val navController = rememberNavController()
            var currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            Scaffold (
                topBar = {
                    if (!(currentRoute == "profile" || currentRoute == "post"))
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
            HomeScreen(navController)
        }
        composable("following_suggestion") {
            FollowingSuggestionScreen(navController)
        }
        composable("message") {
            MessageScreen()
        }
        composable("notification") {
            NotificationScreen(navController)
        }
        composable("menu") {
            MenuScreen(navController)
        }
        composable("profile") {
            ProfileScreen(navController)
        }
        composable("post") {
            PostScreen(navController)
        }
    }
}
@Composable
fun HomeScreen(navController: NavHostController) {
    LazyColumn (
        modifier = Modifier.fillMaxSize()

    ) {
        items (1) { i ->
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 10.dp)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box (
                        modifier = Modifier
                            .size(40.dp)
                            .border(1.dp, Color.Black, shape = CircleShape)
                    ) {  }
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "What are you thinking",
                        color = Color.Gray
                    )
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Image,
                        contentDescription = "Choose image"
                    )
                }
            }
            Spacer(Modifier.height(6.dp))
            Divider(color = Color(0xFFE0E0E0))
        }
        items (20) {
                i -> Common.Post1Image()
                    Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                    Common.Post2Images()
                    Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                    Common.Post3Images(navController)
                    Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
        }
    }
}
@Composable
fun FollowingSuggestionScreen(navController: NavHostController) {
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
fun NotificationScreen(navController: NavHostController) {
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
fun MenuScreen(navController: NavHostController) {
    Column (
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Menu",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Row (
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    modifier = Modifier.size(44.dp)
                        .padding(end = 10.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon (
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings",
                    )
                }
                Button(
                    modifier = Modifier.size(44.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon (
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search"
                    )
                }
            }
        }
        Column {
            Row (
                modifier = Modifier
                    .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(14.dp))
                    .padding(horizontal = 8.dp, vertical = 14.dp)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("profile")
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box (
                    modifier = Modifier.size(36.dp)
                        .border(width =  1.dp, color =  Color.Black, shape =  CircleShape)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "Nguyễn Văn A",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black)
            }
            Spacer(Modifier.height(8.dp))
            LazyVerticalStaggeredGrid (
                columns = StaggeredGridCells.Fixed(2), // 2 columns
                modifier = Modifier.fillMaxWidth().height(300.dp).padding(vertical =  8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(1) { i ->
                    Row (
                        modifier = Modifier
                            .background(Color.White)
                            .padding(bottom = 10.dp)
                            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ) {
                        Column (
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "Home"
                            )
                            Text (
                                text = "Home",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                items (1) { i ->
                    Row (
                        modifier = Modifier
                            .background(Color.White)
                            .padding(bottom = 10.dp)
                            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ) {
                        Column (
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Following suggestion"
                            )
                            Text(
                                text = "Following suggestion",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                items (1) { i ->
                    Row (
                        modifier = Modifier
                            .background(Color.White)
                            .padding(bottom = 10.dp)
                            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ) {
                        Column (
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MailOutline,
                                contentDescription = "Messenger"
                            )
                            Text(
                                text = "Following suggestion",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                items(1) { i ->
                    Row (
                        modifier = Modifier
                            .background(Color.White)
                            .padding(bottom = 10.dp)
                            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(14.dp))
                            .padding(12.dp)

                    ) {
                        Column (
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Contacts,
                                contentDescription = "Profile"
                            )
                            Text(
                                text = "Profile",
                                fontSize = 16.sp
                            )
                        }
                    }
               }
                items(1) { i ->
                    Row (
                        modifier = Modifier
                            .background(Color.White)
                            .padding(bottom = 10.dp)
                            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(14.dp))
                            .padding(12.dp)

                    ) {
                        Column (
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Settings"
                            )
                            Text(
                                text = "Settings",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Row (
                modifier = Modifier
                    .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(14.dp))
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Đăng xuất",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black)
            }
        }
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
fun ProfileScreen(navController: NavHostController) {
    Scaffold (
        topBar = {
            TopAppBar (
                title = {
                    Text(
                        text = "Nguyễn Văn A",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle Search */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search"
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        },
        contentWindowInsets = WindowInsets(bottom = 0.dp)
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier.padding(innerPadding)
        ) {
            items(1) {
                i -> Row (
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 6.dp)
                ) {
                    Box (
                        modifier = Modifier
                            .size(72.dp)
                            .border(1.dp, Color.Black, CircleShape)
                    ) {}
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Nguyễn Văn A",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )
                        Spacer(Modifier.height(6.dp))
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "6",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "posts",
                                    fontSize = 16.sp
                                )
                            }
                            Column {
                                Text(
                                    text = "45",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "followers",
                                    fontSize = 16.sp
                                )
                            }
                            Column {
                                Text(
                                    text = "273",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "following",
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
            items(1) {
                i -> Row (
                modifier = Modifier
                    .padding(top = 10.dp, end = 10.dp, bottom = 20.dp, start = 10.dp)
                ) {
                    Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")
                }
                Divider(Modifier.height(1.dp))
            }
            items (20) {
                    i -> Common.Post1Image()
                Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                Common.Post2Images()
                Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                Common.Post3Images(navController)
                Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
            }
        }
    }
}
@Composable
fun PostScreen(navController: NavHostController) {
    val commentText = remember { mutableStateOf("") }
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    Scaffold (
        topBar = {
            TopAppBar (
                title = {
                    Text(
                        text = "Nguyễn Văn A",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle Search */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search"
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) {
        innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
                .padding(innerPadding)
        ) {
            LazyColumn (

            ) {
                item {
                    Common.Post3Images(navController)
                }
                items(10) {
                        i -> Common.Comment()
                    Common.Comment(1)
                    Common.Comment(2)
                }
            }
            Row (
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
                    .background(Color.White)
                    .align(Alignment.BottomCenter)
                    .windowInsetsPadding(WindowInsets.ime),
            ) {
                TextField(
                    value = commentText.value,
                    onValueChange = {commentText.value = it},
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = {
                        if (commentText.value.isNotBlank()) {
                            commentText.value = ""
                        }
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Send comment"
                    )
                }
            }
        }
    }
}
