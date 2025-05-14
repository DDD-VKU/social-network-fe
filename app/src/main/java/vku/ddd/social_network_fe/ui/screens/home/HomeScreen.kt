package vku.ddd.social_network_fe.ui.screens.home

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import vku.ddd.social_network_fe.R
import vku.ddd.social_network_fe.data.api.RetrofitClient
import vku.ddd.social_network_fe.data.datastore.AccountDataStore
import vku.ddd.social_network_fe.data.datastore.TokenDataStore
import vku.ddd.social_network_fe.data.model.Account
import vku.ddd.social_network_fe.data.model.Post
import vku.ddd.social_network_fe.ui.components.Common
import vku.ddd.social_network_fe.ui.viewmodel.PostViewModel

@Composable
fun HomeScreen(navController: NavHostController) {

    val post = remember { mutableStateOf<Post?>(null) }
    val myAccount = remember { mutableStateOf<Account?>(null) }
    val posts = remember { mutableStateListOf<Post>() }
    val postsViewModel: PostViewModel = viewModel()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        myAccount.value = AccountDataStore(context).getAccount()
        if (myAccount.value == null) {
            navController.navigate("login")
        }
        post.value =
            fetchPost(118)
        while (posts.size < 10 && post.value != null) {
            if (fetchPost(187) != null)
                posts.add(fetchPost(187)!!)
            if (fetchPost(190) != null)
                posts.add(fetchPost(190)!!)
        }
        postsViewModel.loadData(posts)
    }

    LazyColumn (
        modifier = Modifier.fillMaxSize()
    ) {
        items (1) { i ->
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
                    ) {
                        AsyncImage(
                            model = "http://10.0.2.2:8080/social-network/api/uploads/images/${myAccount.value?.avatar ?: 0L}",
                            contentDescription = null,
                            contentScale = ContentScale.Crop,

                            modifier = Modifier
                                .size(60.dp)
                                .padding(1.dp)
                                .clip(CircleShape),
                        )
                    }
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
            Divider(color = Color(0xFFE0E0E0))
        }
        itemsIndexed(postsViewModel.posts) { _, p ->
            Common.MergedPostContent(navController = navController, post = p)
            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
        }
    }
}

suspend fun fetchPost(id: Long): Post? {
    return try {
        val response = RetrofitClient.instance.getPostById(id)

        if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d("abc def", "Response: $apiResponse")

            apiResponse?.data  // Trả về `Post`
        } else {
            Log.e("abc def", "Error: ${response.errorBody()?.string()}")
            null
        }
    } catch (e: Exception) {
        Log.e("abc def", "Exception: ${e.message}")
        null
    }
}
