package vku.ddd.social_network_fe.ui.screens.menu

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import vku.ddd.social_network_fe.data.api.RetrofitClient
import vku.ddd.social_network_fe.data.datastore.AccountDataStore
import vku.ddd.social_network_fe.data.datastore.TokenDataStore
import vku.ddd.social_network_fe.data.model.Account

@Composable
fun MenuScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var account by remember { mutableStateOf<Account?>(null) }

    LaunchedEffect(Unit) {
        account = AccountDataStore(context).getAccount()
        Log.d("Login account", account.toString())
    }

    Column (
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Menu",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Row (
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    modifier = Modifier.size(44.dp)
                        .padding(end = 10.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon (
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings",
                    )
                }
                Button(
                    modifier = Modifier.size(44.dp),
                    onClick = {
                        navController.navigate("search")
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon (
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search"
                    )
                }
            }
        }
        Column {
            Row (
                modifier = Modifier
                    .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(14.dp))
                    .padding(horizontal = 8.dp, vertical = 14.dp)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("profile/${account!!.id}")
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box (
                    modifier = Modifier.size(36.dp)
                        .border(width =  1.dp, color =  Color.LightGray, shape =  CircleShape)
                ) {
                    if (account != null)
                    AsyncImage(
                        model = "http://10.0.2.2:8080/social-network/api/uploads/images/${account!!.avatar}",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(1.dp)
                            .clip(CircleShape),
                        //contentScale = ContentScale.Crop
                    )
                }
                Spacer(Modifier.width(6.dp))
                Text(
                    text = account?.let { it.lname + " " + it.fname } ?: "Nguyễn Văn A",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black)
            }
            Spacer(Modifier.height(8.dp))
            LazyVerticalStaggeredGrid (
                columns = StaggeredGridCells.Fixed(2), // 2 columns
                modifier = Modifier.fillMaxWidth().height(300.dp).padding(vertical =  8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(1) { i ->
                    Row (
                        modifier = Modifier
                            .background(Color.White)
                            .padding(bottom = 10.dp)
                            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ) {
                        Column (
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "Home"
                            )
                            Text (
                                text = "Home",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                items (1) { i ->
                    Row (
                        modifier = Modifier
                            .background(Color.White)
                            .padding(bottom = 10.dp)
                            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ) {
                        Column (
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Following suggestion"
                            )
                            Text(
                                text = "Following suggestion",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                items (1) { i ->
                    Row (
                        modifier = Modifier
                            .background(Color.White)
                            .padding(bottom = 10.dp)
                            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ) {
                        Column (
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MailOutline,
                                contentDescription = "Messenger"
                            )
                            Text(
                                text = "Following suggestion",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                items(1) { i ->
                    Row (
                        modifier = Modifier
                            .background(Color.White)
                            .padding(bottom = 10.dp)
                            .background(Color(0xFFE0E0E0))
                            .padding(12.dp)

                    ) {
                        Column (
                            modifier = Modifier.clickable { navController.navigate("profile/${account!!.id}") },
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Contacts,
                                contentDescription = "Profile"
                            )
                            Text(
                                text = "Profile",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                items(1) { i ->
                    Row (
                        modifier = Modifier
                            .background(Color.White)
                            .padding(bottom = 10.dp)
                            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(14.dp))
                            .padding(12.dp)

                    ) {
                        Column (
                            modifier = Modifier.clickable { navController.navigate("setting") },
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Settings"
                            )
                            Text(
                                text = "Settings",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Row (
                modifier = Modifier
                    .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(14.dp))
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button (
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        navController.navigate("login")
                        coroutineScope.launch {
                            val token = TokenDataStore(context).getToken()
                            RetrofitClient.authInstance.logout(token!!)
                            AccountDataStore(context).clearAccount()
                            TokenDataStore(context).clearToken()
                        }
                  },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0))
                )
                { Text("Đăng xuất", color = Color.Black ,fontSize = 16.sp, fontWeight = FontWeight.Bold) }
            }
        }
    }
}