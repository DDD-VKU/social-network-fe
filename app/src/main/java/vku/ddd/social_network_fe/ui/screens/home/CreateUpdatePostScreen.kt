package vku.ddd.social_network_fe.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import vku.ddd.social_network_fe.ui.components.DropdownMenuBox

@Composable
fun CreateUpdatePostScreen(navController: NavHostController) {
    var postContent by remember { mutableStateOf("") }
    var selectedPrivacy by remember { mutableStateOf("Public") }
    val privacyOptions = listOf("Public", "Followers", "Private")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Create your post",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    ) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(
                        onClick = { /* Handle post action */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text("POST",
                            color = Color.Blue,
                            fontWeight = FontWeight.Bold)
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                modifier = Modifier
                    .drawBehind {
                        val strokeWidth = 1 * density
                        val y = size.height - strokeWidth / 2

                        drawLine(
                            Color.LightGray,
                            Offset(0f, y),
                            Offset(size.width, y),
                            strokeWidth
                        )
                    }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Gray, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Nguyễn Văn A", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(4.dp))
                    DropdownMenuBox(selectedPrivacy, privacyOptions) { selectedPrivacy = it }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = postContent,
                onValueChange = { postContent = it },
                placeholder = {
                    Text("What's in your mind?",
                        modifier = Modifier
                            .offset(y = -4.dp, x = 2.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                    .border(1.dp, Color.Blue, RoundedCornerShape(10.dp))
                ,
                maxLines = 6,
                minLines = 4,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Handle image selection */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008000)),
                shape = RoundedCornerShape(6.dp)
            ) {
                Icon(imageVector = Icons.Default.Image, contentDescription = "Add Image")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Image")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Handle post submission */ },
                modifier = Modifier
                    .fillMaxWidth()
                ,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text("POST", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}