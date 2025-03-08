package vku.ddd.social_network_fe.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun UserSearchScreen(navController: NavHostController) {
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