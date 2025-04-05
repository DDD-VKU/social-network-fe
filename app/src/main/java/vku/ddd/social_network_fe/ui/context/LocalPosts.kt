package vku.ddd.social_network_fe.ui.context

import androidx.compose.runtime.compositionLocalOf
import vku.ddd.social_network_fe.data.model.Post

val LocalPosts = compositionLocalOf<MutableList<Post>> {
    error("No posts provided")
}