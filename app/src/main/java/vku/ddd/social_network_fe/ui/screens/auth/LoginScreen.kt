package vku.ddd.social_network_fe.ui.screens.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import vku.ddd.social_network_be.dto.request.AuthenticateRequest
import vku.ddd.social_network_fe.data.api.RetrofitClient
import vku.ddd.social_network_fe.data.datastore.AccountDataStore
import vku.ddd.social_network_fe.data.datastore.TokenDataStore
import vku.ddd.social_network_fe.data.model.Account

@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current
    var acc by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var passError by remember { mutableStateOf<String?>(null) }
    var accError by  remember { mutableStateOf<String?>(null) }
    var isError by  remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val loginFailed = remember { mutableStateOf(false) }

    Column ( modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF0F2F5))
        .padding(end = 16.dp, start = 16.dp, top = 16.dp)
        .imePadding()
        .verticalScroll(rememberScrollState()),
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
        Spacer(modifier = Modifier.height(120.dp))
        //User input
        OutlinedTextField(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(0.9f),
            value = acc,
            onValueChange =
            { acc = it
              accError = if(it.isBlank()) "Can't empty" else null
            },
            label = { Text("Username") },
            isError = accError != null,
        )
        accError?.let {
            Text(text = it, color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(start = 16.dp))
        }

        Spacer(modifier = Modifier.padding(6.dp))

        //Password input
        OutlinedTextField(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(0.9f),
            value = pass,
            onValueChange =
            {
                pass = it
                passError = when {
                    it.isBlank() -> "Can't empty"
                    else -> null;
                }
            },
            label = { Text("Password") },
            isError = passError != null
        )
        passError?.let {
            Text(text = it, color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(start = 16.dp))
        }

        //Login button
        val interactionSource = remember { MutableInteractionSource() }
        val isHovered by interactionSource.collectIsPressedAsState()
        Spacer (modifier = Modifier .padding(14.dp))
        if (loginFailed.value) {
            Text (
                text = "Wrong username or password",
                color = Color.Red,
                fontSize = 14.sp,
            )
            Spacer (modifier = Modifier .padding(10.dp))
        }
        Button(
            onClick = {
                // Kiểm tra trước khi cho phép đăng nhập
                accError = if (acc.isBlank()) "Username can't empty" else null
                passError = when {
                    pass.isBlank() -> "Password can't empty"
                    pass.length < 8 -> "Can't less than 8 character"
                    else -> null
                }
                val tokenDataStore = TokenDataStore(context)
                val accountDataStore = AccountDataStore(context)
                if (accError == null && passError == null) {
                    coroutineScope.launch {
                        val request = AuthenticateRequest(
                            username = acc,
                            password = pass
                        )
                        val response = RetrofitClient.authInstance.authenticate(request)
                        if (response.isSuccessful) {
                            val token = response.body()?.data?.token.orEmpty()
                            if (token.length > 0) {
                                val expireTime = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000)
                                tokenDataStore.saveToken(token, expireTime)
                                RetrofitClient.setToken(token)
                                val account = response.body()?.data!!.account
                                accountDataStore.saveAccount(
                                    Account(
                                        id = account.id,
                                        username = account.username,
                                        fname = account.fname,
                                        lname = account.lname,
                                        email = account.email,
                                        avatar = account.avatar,
                                    )
                                )
                                navController.navigate("home")
                            } else {
                                loginFailed.value = true
                            }
                        } else {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(70.dp)
                .padding(6.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2))
        ) {
            Text("Start", fontSize = 19.sp, fontWeight = FontWeight.ExtraBold)
        }
        Spacer(modifier = Modifier.height(18.dp))

        Text("Forget Password",
            color = Color.Black,
            modifier = Modifier.clickable {  }
        )

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
                Text("Don't have account ?")
                Button(
                    modifier = Modifier,
                    shape = RoundedCornerShape(5.dp),
                    onClick = {navController.navigate("register")},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE573E5))
                ) { Text("Create account") }
            }
        }
    }
}