package vku.ddd.social_network_fe.ui.screens.search

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import vku.ddd.social_network_fe.data.api.RetrofitClient
import vku.ddd.social_network_fe.ui.components.Common
import vku.ddd.social_network_fe.ui.viewmodel.PostViewModel

@Composable
fun PostSearchScreen(navController: NavHostController, queryString: String) {
    val viewModel: PostViewModel = viewModel()
    val context = LocalContext.current
    LaunchedEffect(queryString) {
        Log.d("curent route", navController.currentDestination?.route.toString())
        if (!(queryString.isBlank() || queryString.isEmpty())) {
            val response = RetrofitClient.instance.searchPosts(queryString)
            if (response.isSuccessful) {
                viewModel.posts.clear()
                viewModel.loadData(response.body()!!.data)
            } else {
                Toast.makeText(context, "Can't search posts", Toast.LENGTH_LONG).show()
            }
        }
    }
    LazyColumn (
        modifier = Modifier.fillMaxSize()

    ) {
        itemsIndexed(viewModel.posts) { index, post ->
            Common.MergedPostContent(
                navController = navController,
                post = post
            )

            // Divider dưới mỗi bài (trừ bài cuối cùng)
            if (index < viewModel.posts.lastIndex) {
                Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
            }
        }
    }
}