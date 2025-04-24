package vku.ddd.social_network_fe.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import vku.ddd.social_network_fe.data.api.RetrofitClient
import vku.ddd.social_network_fe.data.model.User
import vku.ddd.social_network_fe.ui.viewmodel.UserViewModel

@Composable
fun FollowingSuggestionScreen(navController: NavHostController) {
    val context = LocalContext.current
    val userViewModel: UserViewModel = viewModel()
    LaunchedEffect(Unit) {
        val suggestion = getFollowingSuggestion()
        userViewModel.loadData(suggestion)
        if (userViewModel.users.isEmpty()) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // Đổi kiểu index từ Long sang Int
        itemsIndexed(userViewModel.users) { index: Int, user: User ->
            Column {
                var isFollowing by remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .background(Color(0xFFE7F8FF))
                        .padding(top = 10.dp, bottom = 3.dp)
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                            .size(56.dp)
                    ) {
                        AsyncImage(
                            model = "http://10.0.2.2:8080/social-network/api/uploads/images/${user.avatar}",
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }

                    Spacer(Modifier.width(10.dp))

                    Column {
                        Text(
                            text = user.username,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${user.followersCount} followers",
                            fontSize = 20.sp,
                            color = Color.Gray
                        )

                        Button(
                            onClick = { isFollowing = !isFollowing },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isFollowing) Color.White else Color.Blue,
                                contentColor = if (isFollowing) Color.Blue else Color.White
                            ),
                            border = if (isFollowing) BorderStroke(1.dp, Color.Black) else null
                        ) {
                            Text(
                                text = if (isFollowing) "Followed" else "Follow",
                                color = if (isFollowing) Color.Blue else Color.White
                            )
                        }
                    }
                }

                Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
            }
        }
    }
}


suspend fun getFollowingSuggestion(): List<User> {
    return try {
        val response = RetrofitClient.instance.getUsers()
        if (response.isSuccessful) {
            response.body()?.data ?: emptyList()
        } else {
            emptyList<User>()
        }
    } catch (e: Exception) {
        emptyList<User>()
    }
}