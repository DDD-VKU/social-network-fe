package vku.ddd.social_network_fe.ui.screens.post

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import vku.ddd.social_network_be.dto.request.CommentCreateRequest
import vku.ddd.social_network_be.dto.request.CommentUpdateRequest
import vku.ddd.social_network_fe.data.api.RetrofitClient
import vku.ddd.social_network_fe.data.datastore.AccountDataStore
import vku.ddd.social_network_fe.data.model.Comment
import vku.ddd.social_network_fe.data.model.Post
import vku.ddd.social_network_fe.ui.components.Common
import vku.ddd.social_network_fe.ui.viewmodel.CommentViewModel

@Composable
fun PostScreen(navController: NavHostController, post: Post?) {
    val commentText = remember { mutableStateOf("") }
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val isKeyboardVisible = remember { mutableStateOf(false) }
    val isEditting = remember { mutableStateOf(false) }
    val selectedComment = remember { mutableStateOf<Comment?>(null) }
    val navigationBarHeight = with(LocalDensity.current) {
        WindowInsets.navigationBars.getBottom(this).toDp()
    }
    val context = LocalContext.current
    val parentCommentId = remember { mutableStateOf<Long?>(null) }
    val commentLevel = remember { mutableStateOf(0L) }
    val coroutineScope = rememberCoroutineScope()
    val commentViewModel: CommentViewModel = viewModel()

    LaunchedEffect(Unit) {
        try {
            val fetchedComments = getComments(post!!) ?: emptyList()
            commentViewModel.loadData(fetchedComments)
        } catch (e: Exception) {
            Log.e("PostScreen", "Error loading comments", e)
        }
    }

    LaunchedEffect(imeHeight) {
        val keyboardOpen = imeHeight > 0
        if (keyboardOpen != isKeyboardVisible.value) {
            isKeyboardVisible.value = keyboardOpen
        }
    }

    fun handleAddComment() {
        if (commentText.value.isBlank()) return

        coroutineScope.launch {
            val newComment = Common.createComment(
                request = CommentCreateRequest(
                    postId = post!!.id,
                    userId = AccountDataStore(context).getAccount()!!.id,
                    content = commentText.value,
                    parentCommentId = parentCommentId.value,
                    commentLevel = commentLevel.value
                )
            ) ?: return@launch

            // Reset input fields
            commentText.value = ""
            parentCommentId.value = null
            commentLevel.value = 0

            // Update comments list
            if (newComment.parentCommentId == 0L) {
                commentViewModel.comments.add(0, newComment) // Add to top of reversed list
            } else {
                val success = updateCommentsWithNewChild(commentViewModel.comments, newComment)
                if (!success) {
                    // Fallback: Add as top-level if parent not found
                    commentViewModel.comments.add(0, newComment.copy(parentCommentId = 0L))
                }
            }
        }
    }

    fun handleEditComment(old: Comment) {
        if (commentText.value.isBlank()) return

        coroutineScope.launch {
            val updated = Common.editComment(
                request = CommentUpdateRequest(
                    commentId = old.id,
                    content   = commentText.value,
                    userId    = AccountDataStore(context).getAccount()!!.id
                ),
                context = context
            ) ?: return@launch

            // Tìm index của comment trong list gốc
            val idx = commentViewModel.comments.indexOfFirst { it.id == updated.id }
            if (idx != -1) {
                // Thay nguyên phần tử đó bằng bản mới
                commentViewModel.comments[idx] = updated
            } else {
                // Nếu không thấy ở top‑level, thay trong cây con
                replaceCommentInTree(commentViewModel.comments, updated)
            }

            // Reset UI
            isEditting.value = false
            selectedComment.value = null
            commentText.value = ""
        }
    }

    fun handleRemoveComment(id: Long) {
        coroutineScope.launch {
            val response = RetrofitClient.instance.deleteComment(id)
            if (response.isSuccessful) {
                removeCommentInTree(commentViewModel.comments, id)
                Toast.makeText(context, "Deleted comment successfully", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(context, "Can't delete comment", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = (post?.userLname + " " + post?.userFname) ?: "Nguyễn Văn A",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBackIosNew, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("search") }) {
                        Icon(Icons.Outlined.Search, "Search")
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
            )
        },
        bottomBar = {
            Column {
                parentCommentId.value?.let { parentId ->
                    if (selectedComment.value == null) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Replying to #$parentId comment",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            TextButton(
                                onClick = {
                                    parentCommentId.value = null
                                    commentLevel.value = 0
                                }
                            ) {
                                Text("Cancel", style = MaterialTheme.typography.labelLarge)
                            }
                        }
                    }
                }
                selectedComment.value?.let { comment ->
                    if (parentCommentId.value == null && isEditting.value) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Editing #${comment.id} comment",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            TextButton(
                                onClick = {
                                    isEditting.value = false
                                    selectedComment.value = null
                                }
                            ) {
                                Text("Cancel", style = MaterialTheme.typography.labelLarge)
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .imePadding()
                        .fillMaxWidth()
                        .background(Color.White)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                ) {
                    TextField(
                        value = commentText.value,
                        onValueChange = {
                            commentText.value = it
                            if (selectedComment.value != null) {
                                selectedComment.value!!.content = commentText.value
                            }
                        },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Add a comment...") }
                    )
                    IconButton(
                        onClick = {
                            if (isEditting.value) {
                                handleEditComment(selectedComment.value!!)
                            } else {
                                handleAddComment()
                            }
                        },
                        enabled = commentText.value.isNotBlank()
                    ) {
                        Icon(Icons.Filled.Send, "Send comment")
                    }
                }
            }
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Common.MergedPostContent(navController = navController, post = post!!)
            }
            itemsIndexed(commentViewModel.comments.reversed()) { _, comment ->
                Common.Comment(
                    comment = comment,
                    onChangeCommentParentId = { newParentCommentId, newCommentLevel ->
                        parentCommentId.value = newParentCommentId
                        commentLevel.value = newCommentLevel + 1
                    },
                    onEditComment = { comment ->
                        isEditting.value = true
                        selectedComment.value = comment
                        commentText.value = comment.content
                    },
                    onDeleteComment = { comment ->
                        selectedComment.value = comment
                        isEditting.value = false
                        handleRemoveComment(selectedComment.value!!.id)
                    }
                )
            }
        }
    }
}

private fun updateCommentsWithNewChild(
    comments: MutableList<Comment>,
    newComment: Comment
): Boolean {
    comments.forEachIndexed { index, comment ->
        if (comment.id == newComment.parentCommentId) {
            comments[index] = comment.copy(
                childrenComments = comment.childrenComments.toMutableList().apply {
                    add(0, newComment)
                }
            )
            return true
        }
        if (updateCommentsWithNewChild(comment.childrenComments, newComment)) {
            return true
        }
    }
    return false
}

suspend fun getComments(post: Post): List<Comment>? {
    return try {
        val response = RetrofitClient.instance.getCommentsByPostId(post.id)
        if (response.isSuccessful) {
            Log.d("abc def", response.body()?.data.toString())
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

private fun findComment(
    comments: List<Comment>,
    parentId: Long
): Comment? {
    for (comment in comments) {
        if (comment.id == parentId) {
            return comment
        }
        val foundInChildren = findComment(comment.childrenComments, parentId)
        if (foundInChildren != null) {
            return foundInChildren
        }
    }
    return null
}

fun replaceCommentInTree(
    list: MutableList<Comment>,
    updated: Comment
): Boolean {
    list.forEachIndexed { index, c ->
        if (c.id == updated.id) {
            list[index] = updated
            return true
        }
        if (replaceCommentInTree(c.childrenComments, updated)) {
            return true
        }
    }
    return false
}

fun removeCommentInTree(
    comments: MutableList<Comment>,
    commentId: Long
): Boolean {
    val iter = comments.iterator()
    while (iter.hasNext()) {
        val c = iter.next()
        if (c.id == commentId) {
            // Xóa comment này (và subtree)
            iter.remove()
            return true
        }
        // Nếu không phải, tiếp tục tìm trong children
        if (removeCommentInTree(c.childrenComments, commentId)) {
            return true
        }
    }
    return false
}