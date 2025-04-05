package vku.ddd.social_network_fe.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import vku.ddd.social_network_fe.data.model.Post

class PostViewModel: ViewModel() {
    val posts = mutableStateListOf<Post>()

    fun removePost(postId: Long) {
        posts.removeAll { it.id == postId }
    }

    fun loadData(data: List<Post>) {
        posts.addAll(data)
    }
}