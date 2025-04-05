package vku.ddd.social_network_fe.ui.components

// Android components
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes

// Navigation
import androidx.navigation.NavHostController

// Coil 3 imports
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest

// Material3 components
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

// Compose foundation
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

// Material icons
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme

// Compose UI
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson

// Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Network
import okhttp3.ResponseBody
import retrofit2.Retrofit
import vku.ddd.social_network_be.dto.request.CommentCreateRequest
import vku.ddd.social_network_be.dto.request.ReactToPostRequest

// Application specific
import vku.ddd.social_network_fe.R
import vku.ddd.social_network_fe.data.api.RetrofitClient
import vku.ddd.social_network_fe.data.model.Comment
import vku.ddd.social_network_fe.data.model.Post
import vku.ddd.social_network_fe.ui.context.LocalPosts
import vku.ddd.social_network_fe.ui.viewmodel.PostViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

// Date/time
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Common {
    val gson = Gson()
    @Composable
    fun LikeCommentShareButtons(
        color : Color = Color.White,
        textColor: Color = Color.Gray,
        navController: NavHostController,
        post: Post,
        onPostUpdate: (Post) -> Unit
    ) {
        val postJson = gson.toJson(post)
        val encodedPost = URLEncoder.encode(postJson, StandardCharsets.UTF_8.toString())
        val coroutineScope = rememberCoroutineScope()
        Row (
            modifier = Modifier
        ) {
            var isLiked = remember { mutableStateOf(false) }
            Button(
                onClick = {
                    isLiked.value = !isLiked.value
                    val request = ReactToPostRequest(
                        postId = post.id,
                        userId = 1,
                        reaction = "LIKE"
                    )
                    coroutineScope.launch {
                        val response = reactToPost(post, request)
                        response?.let {
                            Log.d("abc def", "Post reacted successfully: ${it.id}")
                            onPostUpdate(it)
                        } ?: Log.e("abc def", "Failed to react to post")
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = color, //
                    contentColor = color
                ),
                shape = RectangleShape,
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ThumbUp, // Biểu tượng Like
                    contentDescription = "Like",
                    tint = if (isLiked.value) Color.Blue else textColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Like", color = if (isLiked.value) Color.Blue else textColor, fontSize = 14.sp)
            }
            Button(
                onClick = {
                    navController.navigate("post/$encodedPost")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = color, // Màu xanh Facebook
                    contentColor = color
                ),
                shape = RectangleShape, // Bo tròn viền
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChatBubbleOutline,
                    contentDescription = "Comment",
                    tint = textColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Comment", color = textColor, fontSize = 14.sp)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = color,
                    contentColor = color
                ),
                shape = RectangleShape,
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Repeat,
                    contentDescription = "Share",
                    tint = textColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Share", color = textColor, fontSize = 14.sp)
            }
        }
    }

    @Composable
    fun LikeCommentShareCounter(
        color: Color = Color.White,
        textColor: Color = Color.Black,
        post: Post? = null
    ) {
        Row(
            modifier = Modifier
                .background(color)
                .padding(vertical = 10.dp, horizontal = 4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween // Adjust as needed
        ) {
            // Like/Dislike Section
            Row {
                Icon(Icons.Filled.ThumbUp, "Like", tint = Color.Blue)
                Spacer(Modifier.width(2.dp))
                Text(post?.likesCount?.toString() ?: "0", color = textColor)
                Spacer(Modifier.width(10.dp))
                Icon(Icons.Filled.ThumbDown, "Dislike", tint = Color.Red)
                Spacer(Modifier.width(2.dp))
                Text(post?.dislikeCount?.toString() ?: "0", color = textColor)
            }

            // Comments & Shares (horizontal)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (post?.commentsCount ?: 0 > 0) {
                    Text(
                        text = when (post?.commentsCount) {
                            1L -> "1 comment"
                            else -> "${post?.commentsCount ?: 0} comments"
                        }
                    )
                }
                if (post?.shareCount ?: 0 > 0) {
                    Text(
                        text = when (post?.shareCount) {
                            1L -> "1 share"
                            else -> "${post?.shareCount ?: 0} shares"
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun PostMetaData(navController: NavHostController, post: Post? = null) {
        val defaultDateTime = "2020-04-04T19:00:00" // Điều chỉnh lại default thành dạng ISO
        val dateTimeString = post?.createdAt ?: defaultDateTime
        val expanded = remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current
        val viewModel: PostViewModel = viewModel()

        val parsedDateTime = remember(dateTimeString) {
            runCatching {
                LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            }.getOrElse {
                LocalDateTime.parse(defaultDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME) // fallback nếu lỗi
            }
        }

        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Box (
                    modifier = Modifier
                        .size(40.dp)
                        .border(1.dp, Color.Black, shape = CircleShape)
                ) { }
                Spacer(Modifier.width(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = (post?.userLname + " " + post?.userFname) ?: "Nguyễn Văn A",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(text = parsedDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                    }
                }
            }
            Box {
                IconButton(
                    onClick = { expanded.value = true }
                ) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Option",
                        modifier = Modifier.size(20.dp)
                    )
                }
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = {expanded.value = false},
                    modifier = Modifier
                        .background(Color.White)
                        .border(1.dp, Color.LightGray)
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Edit") },
                        onClick = { expanded.value = false }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Delete") },
                        onClick = {
                            expanded.value = false
                            coroutineScope.launch {
                                val response = RetrofitClient.instance.deletePost(post!!.id)
                                if (response.isSuccessful) {
                                    viewModel.removePost(post.id)
                                    Toast.makeText(context, response.body()?.message, Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Can't delete post", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun MergedPostContent(
        navController: NavHostController,
        post: Post? = null
    ) {
        val postState = remember { mutableStateOf(post) }
        val postJson = gson.toJson(postState.value)
        val encodedPost = URLEncoder.encode(postJson, StandardCharsets.UTF_8.toString())
        Column {
            Spacer(modifier = Modifier.height(6.dp))
            // Display the post metadata and caption
            PostMetaData(navController = navController, post = postState.value)
            Text(
                text = post?.caption ?: "Abc def ghi",
                modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
            )

            // Check if there are children posts to determine which layout to show
            if (post?.childrenPosts!!.isEmpty()) {
                // No children posts – display the main post image
                val imageUrl = "http://10.0.2.2:8080/social-network/api/uploads/images/${encodedPost}"
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Post Image",
                    placeholder = rememberVectorPainter(Icons.Default.Image),
                    error = rememberVectorPainter(Icons.Default.BrokenImage),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 0.dp, max = 400.dp)
                )
            } else {
                // There are children posts, so display their images in a grid.
                val childrenCount = postState.value!!.childrenPosts.size
                when {
                    // 2 or 3 children posts – use a grid with that many columns
                    childrenCount in 2..3 -> {
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Fixed(childrenCount),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 0.dp, max = 400.dp)
                        ) {
                            items(childrenCount) { i ->
                                val childPost = postState.value!!.childrenPosts[i]
                                val imageUrl = "http://10.0.2.2:8080/social-network/api/uploads/images/${childPost.imageId}"
                                val childPostJson = gson.toJson(childPost)
                                val encodedChildPost = URLEncoder.encode(childPostJson, StandardCharsets.UTF_8.toString())
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 0.dp, max = 400.dp)
                                        .clickable {
                                            navController.navigate("image-detail/${encodedChildPost}")
                                        }
                                ) {
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = "Child Post Image",
                                        placeholder = rememberVectorPainter(Icons.Default.Image),
                                        error = rememberVectorPainter(Icons.Default.BrokenImage),
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                    // 4 or more children posts – use a 2-column grid for the first 4 images
                    childrenCount >= 4 -> {
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 0.dp, max = 400.dp)
                        ) {
                            items(4) { i ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 0.dp, max = 400.dp)
                                ) {
                                    val childPost = postState.value!!.childrenPosts[i]
                                    val imageUrl = "http://10.0.2.2:8080/social-network/api/uploads/images/${childPost.imageId}"
                                    val childPostJson = gson.toJson(childPost)
                                    val encodedChildPost = URLEncoder.encode(childPostJson, StandardCharsets.UTF_8.toString())
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = "Child Post Image",
                                        placeholder = rememberVectorPainter(Icons.Default.Image),
                                        error = rememberVectorPainter(Icons.Default.BrokenImage),
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .aspectRatio(1f)
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable {
                                                Log.d("abc def", encodedChildPost)
                                                navController.navigate("image-detail/${encodedChildPost}")
                                            }
                                    )
                                    // For the last cell, overlay a box if there are extra children posts
                                    if (i == 3 && childrenCount > 4) {
                                        Box(
                                            modifier = Modifier
                                                .matchParentSize()
                                                .background(Color.Black.copy(alpha = 0.5f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "+${childrenCount - 4}",
                                                color = Color.White,
                                                style = MaterialTheme.typography.headlineMedium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Render like, comment and share counters and buttons
            LikeCommentShareCounter(post = postState.value)
            LikeCommentShareButtons(navController = navController, post = postState.value!!) { updatedPost ->
                postState.value = updatedPost
            }
        }
    }

    @Composable
    fun Comment(
        level: Int = 0,
        comment: Comment? = null,
        onChangeCommentParentId: (Long, Long) -> Unit,
    ) {
        // Local state for like button
        val isLiked = remember { mutableStateOf(false) }
        // Cap the indentation to avoid excessive nesting visually
        val mutableLevel = if (level >= 3) 2 else level

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(
                    start = (mutableLevel * 36).dp,
                    end = 6.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
            ) {
                // Avatar placeholder
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Comment content container
                    Column(
                        modifier = Modifier
                            .background(color = Color(0xFFE0E0E0), shape = RoundedCornerShape(14.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = comment?.username ?: "Nguyễn Văn B",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = comment?.content
                                ?: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    // Date, Like and Reply row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column {
                            comment?.createdAt?.let { createdAt ->
                                // Assumes createdAt is a String in ISO format
                                Text(
                                    text = createdAt.split("T").getOrElse(0) { "" },
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = createdAt.split("T").getOrElse(1) { "" }
                                        .split(".").getOrElse(0) { "" },
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                        TextButton(
                            onClick = { isLiked.value = !isLiked.value },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = if (isLiked.value) Color.Blue else Color.Gray
                            ),
                            modifier = Modifier.wrapContentSize(),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("Like", fontSize = 12.sp)
                        }
                        TextButton(
                            onClick = {
                                comment?.let {
                                    onChangeCommentParentId(it.id, it.commentLevel)
                                }
                            },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray),
                            modifier = Modifier.wrapContentSize(),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("Reply", fontSize = 12.sp)
                        }
                    }
                }
            }

            // Debug logging to check child comments
            Log.d("Comment Debug", "Comment ID: ${comment?.id}, Children count: ${comment?.childrenComments?.size ?: 0}")

            // Render children recursively using parent's level incremented by 1.
            comment?.childrenComments?.forEach { child ->
                Comment(
                    level = level + 1,
                    comment = child,
                    onChangeCommentParentId = onChangeCommentParentId
                )
                Log.d("Comment Debug", "Rendered child comment of parent ID: ${comment?.id}")
            }
        }
    }


    @Composable
    fun SearchItem(navController: NavHostController) {
        Row {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search string"
            )
        }
    }
    @Composable
    fun ImageDetail(navController: NavHostController, post: Post) {
        val hidden = remember { mutableStateOf(false) }
        val postState = remember { mutableStateOf(post) }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ZoomableImage(post.imageId)
            Column (
                modifier = Modifier
                    .alpha(if (hidden.value) 0f else 1f)
                    .background(Color.Black.copy(alpha = 0.35f))
                    .padding(start = 6.dp, end = 6.dp, top = 4.dp)
                    .align(Alignment.BottomStart)
                    .clickable {
                        hidden.value = !hidden.value
                    }
            ) {
                Text(
                    text = (post.userLname + " " + post.userFname) ?: "Nguyễn Văn A",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("profile")
                        }
                )
                Text(
                    text = post.caption ?: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    color = Color.White
                )
                LikeCommentShareCounter(color = Color.Transparent, textColor = Color.White, post = post)
                LikeCommentShareButtons(color = Color.Transparent, textColor = Color.White, navController = navController, post = post) { updatedPost ->
                    postState.value = updatedPost
                }
            }

        }
    }
    @Composable
    fun ZoomableImage(id: Long) {
        var scale = remember { mutableStateOf(1f) }
        var offset = remember { mutableStateOf(Offset(0f, 0f)) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        // Cập nhật scale với giới hạn từ 1x đến 5x
                        val newScale = (scale.value * zoom).coerceIn(1f, 5f)

                        // Tính toán giới hạn di chuyển để ảnh không bị kéo ra khỏi màn hình
                        val maxX = (newScale - 1) * 500f // Giới hạn X dựa trên scale
                        val maxY = (newScale - 1) * 500f // Giới hạn Y dựa trên scale
                        val newOffset = Offset(
                            x = (offset.value.x + pan.x).coerceIn(-maxX, maxX),
                            y = (offset.value.y + pan.y).coerceIn(-maxY, maxY)
                        )

                        scale.value = newScale
                        offset.value = newOffset
                    }
                }
        ) {
            AsyncImage(
                model = "http://10.0.2.2:8080/social-network/api/uploads/images/${id}",
                contentDescription = "Post Image",
                placeholder = rememberVectorPainter(Icons.Default.Image),
                error = rememberVectorPainter(Icons.Default.BrokenImage),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = scale.value,
                        scaleY = scale.value,
                        translationX = offset.value.x,
                        translationY = offset.value.y
                    )
            )
        }
    }
    @Composable
    fun ValidateTextField(
        textFieldState: MutableState<String>,
        placeholder: String,
        textFieldErrorState: MutableState<String?>,
        errorLabel: (String) -> String?,
        modifier: Modifier = Modifier
    ) {
        Column {
            OutlinedTextField(
                modifier = modifier,
                value = textFieldState.value,
                onValueChange = {
                    textFieldState.value = it
                    textFieldErrorState.value = errorLabel(it) // ✅ Không cần `?: ""`
                },
                label = {
                    Text(text = placeholder)
                },
                isError = textFieldErrorState.value != null // ✅ Cách đúng để xác định lỗi
            )
            textFieldErrorState.value?.let { errorMessage ->
                if (errorMessage.isNotBlank()) { // ✅ Chỉ hiển thị nếu có lỗi
                    Text(text = errorMessage, fontSize = 12.sp, color = Color.Red)
                }
            }
        }
    }

    suspend fun fetchImage(id: Long): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.instance.getImage(id)
                if (response.isSuccessful) {
                    val body: ResponseBody? = response.body()
                    if (body != null) {
                        // Decode stream thành Bitmap
                        BitmapFactory.decodeStream(body.byteStream())
                    } else {
                        Log.e("image-fetch", "Response body is null")
                        null
                    }
                } else {
                    Log.e("image-fetch", "Failed to fetch image: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("image-fetch", "Exception fetching image: ${e.localizedMessage}", e)
                null
            }
        }
    }

    suspend fun reactToPost(post: Post, request: ReactToPostRequest): Post? {
        return try {
            val response = RetrofitClient.instance.reactPost(post.id, request)
            if (response.isSuccessful) {
                Log.d("API Response", "Updated Post: ${response.body()?.data?.likesCount}")
                response.body()?.data
            } else {
                Log.e("abc def", "Error: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("abc def", "Exception: ${e.message}")
            null
        }
    }

    suspend fun createComment(post: Post, request: CommentCreateRequest): Comment? {
        return try {
            val response = RetrofitClient.instance.createComment(request)
            if (response.isSuccessful) {
                Log.d("API Response", "Created comment: ${response.body()?.data?.id}")
                response.body()?.data
            } else {
                Log.e("abc def", "Error: ${response.errorBody()?.string()}")
                null
            }
        } catch (ex: Exception) {
            Log.e("abc def", "Exception: ${ex.message}")
            null
        }
    }
}