package vku.ddd.social_network_fe.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import vku.ddd.social_network_fe.ui.components.Common

@Composable
fun PostScreen(navController: NavHostController) {
    val commentText = remember { mutableStateOf("") }
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val isKeyboardVisible = remember { mutableStateOf(false) }
    val navigationBarHeight = with(LocalDensity.current) {
        WindowInsets.navigationBars.getBottom(this).toDp()
    }
    LaunchedEffect (imeHeight) {
        val keyboardOpen = imeHeight > 0
        if (keyboardOpen != isKeyboardVisible.value) {
            isKeyboardVisible.value = keyboardOpen
//            onKeyboardVisible(keyboardOpen)
        }
    }
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
                    IconButton(onClick = {
                        navController.navigate("search")
                    }) {
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
                    .offset(y = if (isKeyboardVisible.value) navigationBarHeight else imeHeight.dp)
                    .fillMaxWidth()
                    .background(Color.White)
                    .align(Alignment.BottomCenter)
                    .windowInsetsPadding(WindowInsets.ime)

            ) {
                TextField(
                    value = commentText.value,
                    onValueChange = {commentText.value = it},
                    modifier = Modifier
                        .weight(1f)
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