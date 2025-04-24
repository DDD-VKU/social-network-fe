package vku.ddd.social_network_fe.ui.screens.post
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import vku.ddd.social_network_fe.R
import vku.ddd.social_network_fe.data.model.Post
import vku.ddd.social_network_fe.ui.components.Common.LikeCommentShareButtons
import vku.ddd.social_network_fe.ui.components.Common.LikeCommentShareCounter

@Composable
fun ListImageScreen(navController: NavHostController, post: Post) {
    val postState = remember { mutableStateOf(post) }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 20.dp),
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            ) {

                Column(modifier = Modifier.padding(4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.uet),
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Inside,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Thisis Shira", fontWeight = FontWeight.Bold)
                            Text("Genshin Impact Việt Nam · 3 phút trước", fontSize = 12.sp, color = Color.Gray)
                        }
                    }

                    Text(
                        text = "Cách tui đánh dấu map khi gặp puzzle khó 🤡🤡🤡\n\n" +
                                "Sẵn tiện xin hỏi có cao nhân nào chỉ giúp tui với huhu 😭😭",
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    LikeCommentShareCounter(post = post)
                    LikeCommentShareButtons(navController = navController, post = postState.value) { updatedPost ->
                        postState.value = updatedPost
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.hust),
                        contentDescription = "Map",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Text(text = "Đây là HUST", modifier = Modifier.padding(top = 8.dp))

                    LikeCommentShareCounter(post = post)
                    LikeCommentShareButtons(navController = navController, post = postState.value) { updatedPost ->
                        postState.value = updatedPost
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.hcmus),
                        contentDescription = "In-game",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Text(text = "Đây là UET", modifier = Modifier.padding(top = 8.dp))

                    LikeCommentShareCounter(post = post)
                    LikeCommentShareButtons(navController = navController, post = postState.value) { updatedPost ->
                        postState.value = updatedPost
                    }
                }
            }
        }
    }
}
