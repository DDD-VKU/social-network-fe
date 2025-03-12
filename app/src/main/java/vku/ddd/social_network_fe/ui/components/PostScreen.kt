package vku.ddd.social_network_fe.ui.components

import android.provider.MediaStore.Images.ImageColumns
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import vku.ddd.social_network_fe.ui.components.Common.LikeCommentShareButtons
import vku.ddd.social_network_fe.ui.components.Common.LikeCommentShareCounter
import vku.ddd.social_network_fe.ui.components.Common.PostMetaData


object PostScreen {
    @Composable
    fun PostImage(navController: NavHostController, imageList: List<Int>, columns: Int){
        Column () {
            Spacer(modifier = Modifier.height(8.dp))
            PostMetaData(navController)
            Text (
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(columns),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 0.dp, max = 400.dp)
            ) {
                items(imageList){ imageRes ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 0.dp, max = 400.dp),
                    ){
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = "Post Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .aspectRatio(1f) // Đảm bảo là hình vuông
                                .fillMaxWidth()
                                .border(1.dp, Color.Black)
                                .clickable { navController.navigate("image-detail") }
                        )
                    }
                }
            }

            LikeCommentShareCounter()
            LikeCommentShareButtons(navController = navController)
        }
    }
}