package vku.ddd.social_network_fe.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import vku.ddd.social_network_fe.R

object Common {
    @Composable
    fun LikeCommentShareButtons(color : Color = Color.White, textColor: Color = Color.Gray, navController: NavHostController) {
        Row (
            modifier = Modifier
        ) {
            var isLiked = remember { mutableStateOf(false) }
            Button(
                onClick = {
                    isLiked.value = !isLiked.value
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = color, //
                    contentColor = color
                ),
                shape = RectangleShape,
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ThumbUp, // Biểu tượng Like
                    contentDescription = "Like",
                    tint = if (isLiked.value) Color.Blue else textColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Like", color = if (isLiked.value) Color.Blue else textColor, fontSize = 13.sp)
            }
            Button(
                onClick = {
                    navController.navigate("post")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = color, // Màu xanh Facebook
                    contentColor = color
                ),
                shape = RectangleShape, // Bo tròn viền
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChatBubbleOutline,
                    contentDescription = "Comment",
                    tint = textColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Comment", color = textColor, fontSize = 13.sp)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = color,
                    contentColor = color
                ),
                shape = RectangleShape,
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Repeat,
                    contentDescription = "Share",
                    tint = textColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Share", color = textColor, fontSize = 13.sp)
            }
        }
    }
    @Composable
    fun LikeCommentShareCounter(color: Color = Color.White, textColor: Color = Color.Black) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                modifier = Modifier
                    .background(color)
                    .padding(vertical = 10.dp, horizontal = 4.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "Like icon",
                    tint = Color.Blue
                )
                Spacer(Modifier.width(2.dp))
                Text("245", color = textColor)
                Spacer(Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Filled.ThumbDown,
                    contentDescription = "Dislike icon",
                    tint = Color.Red
                )
                Spacer(Modifier.width(2.dp))
                Text("245", color = textColor)
            }
        }
    }
    @Composable
    fun PostMetaData(navController: NavHostController) {
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
    }
    @Composable
    fun Post1Image(navController: NavHostController) {
        Column {
            Spacer(Modifier.height(6.dp))
            PostMetaData(navController)
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
            LikeCommentShareCounter()
            LikeCommentShareButtons(navController = navController)
        }
    }
    @Composable
    fun Post2Images(navController: NavHostController) {
        Column {
            Spacer(Modifier.height(6.dp))
            PostMetaData(navController)
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
            LikeCommentShareCounter()
            LikeCommentShareButtons(navController = navController)
        }
    }
    @Composable
    fun Post3Images(navController: NavHostController) {
        Column {
            Spacer(Modifier.height(6.dp))
            PostMetaData(navController)
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
                            else R.drawable.uet
                            ),
                            contentDescription = "Background Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("image-detail")
                                }
                        )
                    }
                }
            }
            LikeCommentShareCounter()
            LikeCommentShareButtons(navController = navController)
        }
    }
    //post 4 Image
    @Composable
    fun Post4Images(navController: NavHostController){
        Column () {
            Spacer(modifier = Modifier.padding(8.dp))
            PostMetaData(navController)
            Text (
                text = "A Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 0.dp, max = 400.dp)
            ) {
                items(4) { i->
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 0.dp, max = 400.dp),
                    ){
                        Image(
                            painter = painterResource(
                                when (i){
                                    0 -> R.drawable.uet
                                    1 -> R.drawable.hust
                                    2 -> R.drawable.uet
                                    else -> R.drawable.hust
                                }
                            ),
                            contentDescription = "dsa",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { navController.navigate("image-detail") }
                        )
                    }
                }
            }
            LikeCommentShareCounter()
            LikeCommentShareButtons(navController = navController)
        }
    }
    @Composable
    fun Comment(level : Int = 0) {
        val isLiked = remember { mutableStateOf<Boolean>(false) }
        var mutableLevel = level
        if (level == 3) mutableLevel = 2;
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(start = (mutableLevel*36).dp, end = 6.dp, top = 8.dp, bottom = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )

                Spacer(Modifier.width(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column (
                        modifier = Modifier
                            .background(color = Color(0xFFE0E0E0), RoundedCornerShape(14.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "Nguyễn Văn B",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "2020/04/04",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "19:00:00",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        TextButton(
                            onClick = {
                                isLiked.value = !isLiked.value
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = if (isLiked.value) Color.Blue else Color.Gray
                            ),
                            modifier = Modifier.wrapContentSize(),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                "Like",
                                fontSize = 12.sp
                            )
                        }
                        TextButton(
                            onClick = {},
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray),
                            modifier = Modifier.wrapContentSize(),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("Reply", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
    @Composable
    fun SearchItem(navController: NavHostController) {
        Row {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search string"
            )
        }
    }
    @Composable
    fun ImageDetail(navController: NavHostController) {
        val hidden = remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ZoomableImage(R.drawable.hust)
            Column (
                modifier = Modifier
                    .alpha(if (hidden.value) 0f else 1f)
                    .background(Color.Black.copy(alpha = 0.35f))
                    .padding(start = 6.dp, end = 6.dp, top = 4.dp)
                    .align(Alignment.BottomStart)
                    .clickable {
                        hidden.value = !hidden.value
                    }
            ) {
                Text(
                    text = "Nguyễn Văn A",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("profile")
                        }
                )
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    color = Color.White
                )
                LikeCommentShareCounter(color = Color.Transparent, textColor = Color.White)
                LikeCommentShareButtons(color = Color.Transparent, textColor = Color.White, navController = navController)
            }

        }
    }
    @Composable
    fun ZoomableImage(@DrawableRes imageRes: Int) {
        var scale = remember { mutableStateOf(1f) }
        var offset = remember { mutableStateOf(Offset(0f, 0f)) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        // Cập nhật scale với giới hạn từ 1x đến 5x
                        val newScale = (scale.value * zoom).coerceIn(1f, 5f)

                        // Tính toán giới hạn di chuyển để ảnh không bị kéo ra khỏi màn hình
                        val maxX = (newScale - 1) * 500f // Giới hạn X dựa trên scale
                        val maxY = (newScale - 1) * 500f // Giới hạn Y dựa trên scale
                        val newOffset = Offset(
                            x = (offset.value.x + pan.x).coerceIn(-maxX, maxX),
                            y = (offset.value.y + pan.y).coerceIn(-maxY, maxY)
                        )

                        scale.value = newScale
                        offset.value = newOffset
                    }
                }
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Zoomable Image",
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = scale.value,
                        scaleY = scale.value,
                        translationX = offset.value.x,
                        translationY = offset.value.y
                    )
            )
        }
    }
    @Composable
    fun ValidateTextField(
        textFieldState: MutableState<String>,
        placeholder: String,
        textFieldErrorState: MutableState<String?>,
        errorLabel: (String) -> String?,
        modifier: Modifier = Modifier
    ) {
        Column {
            OutlinedTextField(
                modifier = modifier,
                value = textFieldState.value,
                onValueChange = {
                    textFieldState.value = it
                    textFieldErrorState.value = errorLabel(it) // ✅ Không cần `?: ""`
                },
                label = {
                    Text(text = placeholder)
                },
                isError = textFieldErrorState.value != null // ✅ Cách đúng để xác định lỗi
            )
            textFieldErrorState.value?.let { errorMessage ->
                if (errorMessage.isNotBlank()) { // ✅ Chỉ hiển thị nếu có lỗi
                    Text(text = errorMessage, fontSize = 12.sp, color = Color.Red)
                }
            }
        }
    }

}