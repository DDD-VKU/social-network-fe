package vku.ddd.social_network_fe.ui.screens.notification

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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

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