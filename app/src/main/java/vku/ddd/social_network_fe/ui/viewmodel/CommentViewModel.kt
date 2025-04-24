package vku.ddd.social_network_fe.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import vku.ddd.social_network_fe.data.model.Comment
import vku.ddd.social_network_fe.data.model.Post

class CommentViewModel: ViewModel() {
    val comments = mutableStateListOf<Comment>()

    fun removeComment(postId: Long) {
        comments.removeAll { it.id == postId }
    }

    fun loadData(data: List<Comment>) {
        comments.addAll(data)
    }
}