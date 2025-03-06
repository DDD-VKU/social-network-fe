package vku.ddd.social_network_fe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

object Common {
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
    fun Post3Images(navController: NavHostController) {
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
                    onClick = {
                        navController.navigate("post")
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
}