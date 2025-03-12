package vku.ddd.social_network_fe.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun RegisterScreen(navController: NavHostController) {
    var acc by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var gMail by remember { mutableStateOf("") }
    Column ( modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF0F2F5))
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (modifier = Modifier
            .padding(20.dp)
        ){
            Text("LOGO")
        }
        Spacer(modifier = Modifier.height(70.dp))
        Row  (modifier = Modifier
            .padding(10.dp)
        ) {
            Text("Social JSX ", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1877F2))
        }
        Row  (modifier = Modifier
            .padding(6.dp)
        ) {
            Text("Connect with friends and read the best news! ")
        }
        Spacer(modifier = Modifier.height(60.dp))
        OutlinedTextField(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(0.9f),
            value = acc,
            onValueChange = { acc = it },
            label = { Text("Username") },
        )
        Spacer (modifier = Modifier .padding(6.dp))
        OutlinedTextField(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(0.9f),
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Password") },
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(0.9f),
            value = gMail,
            onValueChange = { gMail = it },
            label = { Text("Email") },
        )
        val interactionSource = remember { MutableInteractionSource() }
        val isHovered by interactionSource.collectIsPressedAsState()
        Spacer (modifier = Modifier .padding(14.dp))
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(70.dp)
                .padding(6.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if (isHovered) Color(0xFF135DB5) else Color(0xFF1877F2)
            ),
            interactionSource = interactionSource
        ) {
            Text("Create", fontSize = 19.sp, fontWeight = FontWeight.ExtraBold)
        }
        Spacer(modifier = Modifier.height(18.dp))


        Spacer(modifier = Modifier.height(18.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Divider(
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "  or  ",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Divider(
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(70.dp))
        Column ( modifier = Modifier
            .fillMaxSize(0.9f)
            .padding(bottom = 1.dp), // Cách đáy 10dp
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                Text("Have accout ?")
                Button(
                    modifier = Modifier,
                    shape = RoundedCornerShape(5.dp),
                    onClick = {navController.navigate("login")},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE573E5))
                ) { Text("Login") }
            }
        }
    }
}