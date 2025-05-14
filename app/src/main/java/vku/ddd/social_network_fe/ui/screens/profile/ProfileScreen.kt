package vku.ddd.social_network_fe.ui.screens.profile

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import vku.ddd.social_network_fe.data.api.RetrofitClient
import vku.ddd.social_network_fe.data.datastore.AccountDataStore
import vku.ddd.social_network_fe.data.model.Account
import vku.ddd.social_network_fe.data.model.Post
import vku.ddd.social_network_fe.ui.components.Common
import vku.ddd.social_network_fe.ui.viewmodel.PostViewModel

@Composable
fun ProfileScreen(navController: NavHostController, accountId: Long) {
    Log.d("ProfileScreen", "Received accountId: $accountId")
    val postViewModel: PostViewModel = viewModel()
    var account by remember { mutableStateOf<Account?>(null) }
    var loginAccount by remember { mutableStateOf<Account?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        isLoading = true
        errorMessage = null

        try {
            account = getAccountInfo(accountId, context)
            loginAccount = AccountDataStore(context).getAccount()!!
            val posts = getPosts(accountId)
            postViewModel.loadData(posts)
        } catch (e: Exception) {
            errorMessage = "Failed to load account: ${e.localizedMessage}"
            Log.e("ProfileScreen", "Error loading account", e)
        } finally {
            isLoading = false
        }
    }

    LaunchedEffect(accountId) {
        try {
            account = getAccountInfo(accountId, context)
        } catch (e: Exception) {
            Log.e("ProfileScreen", "Error reloading account", e)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = account?.run { "$lname $fname" } ?: "Profile",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("search") }) {
                        Icon(Icons.Outlined.Search, contentDescription = "Search")
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
            )
        },
        contentWindowInsets = WindowInsets(bottom = 0.dp)
    ) { innerPadding ->
        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(errorMessage ?: "Unknown error", color = Color.Red)
                }
            }
            account != null -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                ) {
                    item {
                        account?.let { ProfileHeader(it) }
                    }
                    item {
                        Divider(Modifier.height(1.dp))
                    }
                    items (1) { i ->
                        if (loginAccount!!.id == accountId) {
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp, vertical = 10.dp)
                                    .clickable {
                                        navController.navigate("post-create")
                                    }
                            ) {
                                Row (
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box (
                                        modifier = Modifier
                                            .size(40.dp)
                                            .border(1.dp, Color.Black, shape = CircleShape)
                                    ) {  }
                                    Spacer(Modifier.width(10.dp))
                                    Text(
                                        text = "What are you thinking",
                                        color = Color.Gray
                                    )
                                }
                                Button(
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = Color.Black,
                                        containerColor = Color.Transparent
                                    ),
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier
                                        .size(40.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Image,
                                        contentDescription = "Choose image"
                                    )
                                }
                            }
                            Spacer(Modifier.height(6.dp))
                            Divider(color = Color(0xFFE0E0E0), thickness = 3.dp)
                        }
                    }
                    itemsIndexed(postViewModel.posts) { _, post ->
                        Common.MergedPostContent(navController, post)
                        Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                    }
                }
            }
            else -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Account information not available")
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(account: Account) {
    Column(modifier = Modifier.padding(10.dp)) {
        Row {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .border(1.dp, Color.LightGray, CircleShape)
            ) {
                AsyncImage(
                    model = "http://10.0.2.2:8080/social-network/api/uploads/images/${account.avatar}",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(72.dp)
                        .padding(1.dp)
                        .clip(CircleShape),
                )
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    text = "${account.lname} ${account.fname}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ProfileStat(account.posts.size.toString(), "posts")
                    ProfileStat(account.followers.size.toString(), "followers")
                    ProfileStat(account.following.size.toString(), "following")
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        Text(text = account.bio ?: "")
    }
}

@Composable
fun ProfileStat(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        Text(text = label, fontSize = 16.sp)
    }
}


suspend fun getAccountInfo(id: Long, context: Context): Account? {
    return try {
        val response = RetrofitClient.instance.getAccountInfo(id)
        Log.d("Profile Screen before:", response.body().toString())
        if (response.isSuccessful) {
            Log.d("Profile Screen", id.toString())
            Log.d("Profile Screen after:", response.body().toString())
            response.body()!!.data
        } else {
            Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show()
            Log.d("Profile Screen after:", response.body().toString())
            null
        }
    } catch (e: Exception) {
        Log.e("ProfileScreen", e.message.toString())
        null
    }
}

suspend fun getPosts(accountId: Long): MutableList<Post> {
    return try {
        val response = RetrofitClient.instance.getPostByCreatorId(accountId)
        Log.d("Profile Screen before:", response.body().toString())
        if (response.isSuccessful) {
            response.body()!!.data.toMutableList()
        } else {
            emptyList<Post>().toMutableList()
        }
    } catch (e: Exception) {
        emptyList<Post>().toMutableList()
    }
}