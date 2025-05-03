package vku.ddd.social_network_fe.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navHostController : NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cài đặt") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigate("menu") }) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { navHostController.navigate("") }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // Personal Info Row
            ListItem(
                headlineContent = {
                    Text("Thông tin cá nhân", fontSize = 14.sp, color = Color.Gray)
                },
                supportingContent = {
                    Text("Võ Tấn Đức", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                },
                leadingContent = {
                    Image(
                        painter = rememberAsyncImagePainter("https://example.com/avatar.jpg"),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(48.dp)
                            .padding(4.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                },
                trailingContent = {
                    Icon(Icons.Default.ArrowForwardIos, contentDescription = null, tint = Color.Gray)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // navController.navigate("edit_personal_info")
                    }
            )

            Divider()

            // Phone Row
            ListItem(
                leadingContent = {
                    Icon(Icons.Default.Phone, contentDescription = null)
                },
                headlineContent = { Text("Số điện thoại") },
                supportingContent = { Text("(+84) 398 262 515") },
                trailingContent = {
                    Icon(Icons.Default.ArrowForwardIos, contentDescription = null, tint = Color.Gray)
                },
                modifier = Modifier.clickable {
                    // navController.navigate("edit_phone")
                }
            )

            Divider()

            // Email Row
            ListItem(
                leadingContent = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                headlineContent = { Text("Email") },
                supportingContent = { Text("Chưa liên kết") },
                trailingContent = {
                    Icon(Icons.Default.ArrowForwardIos, contentDescription = null, tint = Color.Gray)
                },
                modifier = Modifier.clickable {
                    // navController.navigate("edit_email")
                }
            )

            Divider()
        }
    }
}
