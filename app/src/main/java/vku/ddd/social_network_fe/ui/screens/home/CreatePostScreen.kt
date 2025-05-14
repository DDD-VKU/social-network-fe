package vku.ddd.social_network_fe.ui.screens.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import vku.ddd.social_network_be.dto.request.PostCreateRequest
import vku.ddd.social_network_fe.data.api.RetrofitClient
import vku.ddd.social_network_fe.data.datastore.AccountDataStore
import vku.ddd.social_network_fe.data.model.Account
import vku.ddd.social_network_fe.data.model.Post
import vku.ddd.social_network_fe.ui.components.DropdownMenuBox
import vku.ddd.social_network_fe.data.utils.FileUploadUtils.urisToMultipartParts
import vku.ddd.social_network_fe.ui.components.Common.gson
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CreatePostScreen(navController: NavHostController) {

    suspend fun downloadImageAndGetUri(context: Context, imageUrl: String): Uri? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(inputStream)

                // Lưu ảnh tạm thời vào cache và lấy Uri
                val file = File.createTempFile("temp_image", ".jpg", context.cacheDir)
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()

                FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    file
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    var postContent by remember { mutableStateOf("") }
    remember { mutableStateListOf<String>() }
    var selectedPrivacy by remember { mutableStateOf("Public") }
    val privacyOptions = listOf("Public", "Followers", "Private")
    var selectedImages by remember { mutableStateOf(mutableListOf<Uri>()) }
    val childPostContents = remember { mutableStateListOf<String>() }
    val coroutineScope = rememberCoroutineScope()
    // Get current account
    val context = LocalContext.current
    var account by remember { mutableStateOf<Account?>(null) }
    LaunchedEffect(Unit) {
        account = AccountDataStore(context).getAccount()
    }

    // Keep child captions in sync with selected images
    LaunchedEffect(selectedImages.size) {
        if (selectedImages.size <= 1) {
            childPostContents.clear()
        }
        while (childPostContents.size < selectedImages.size) {
            childPostContents.add("")
        }
        while (childPostContents.size > selectedImages.size) {
            childPostContents.removeAt(childPostContents.lastIndex)
        }
        Log.d("captions: after load post's images", childPostContents.toList().toString())
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 10),
        onResult = { uris ->
            selectedImages = uris.toMutableList()
        }
    )

    // Prepare the create request. (Make sure you clear captions if needed)
    var createRequest = PostCreateRequest(
        userId = 1,
        caption = mutableListOf() // assuming caption is a mutable list in your DTO
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Create your post",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = { /* You can implement a save draft or similar action here */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text("POST", color = Color.Blue)
                    }
                },
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .drawBehind {
                        val strokeWidth = 2 * density
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            Color.LightGray,
                            Offset(0f, y),
                            Offset(size.width, y),
                            strokeWidth
                        )
                    }
                    .windowInsetsPadding(WindowInsets.statusBars)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .border(1.dp, Color.LightGray, CircleShape)
                ) {
                    AsyncImage(
                        model = "http://10.0.2.2:8080/social-network/api/uploads/images/${account?.avatar}",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(72.dp)
                            .padding(1.dp)
                            .clip(CircleShape),
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text( text = account?.run { "$lname $fname" } ?: "Write your status", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    DropdownMenuBox(selectedPrivacy, privacyOptions) { selectedPrivacy = it }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                TextField(
                    value = postContent,
                    onValueChange = { postContent = it },
                    placeholder = {
                        Text(
                            "What's in your mind?",
                            modifier = Modifier.offset(y = -4.dp, x = 2.dp)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                        .border(1.dp, Color.Blue, RoundedCornerShape(10.dp)),
                    maxLines = 12,
                    minLines = 4,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    )
                )
                if (selectedImages.isNotEmpty()) {
                    selectedImages.forEachIndexed { index, image ->
                        Column {
                            Spacer(modifier = Modifier.height(10.dp))
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(image)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Selected image",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 400.dp)
                            )
                            if (selectedImages.size > 1) {
                                TextField(
                                    value = childPostContents.toList().getOrNull(index) ?: "",
                                    onValueChange = { childPostContents[index] = it },
                                    placeholder = {
                                        Text("Caption", modifier = Modifier.offset(x = 2.dp))
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                                        .border(
                                            1.dp,
                                            Color.Blue,
                                            RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                                        ),
                                    maxLines = 12,
                                    minLines = 1,
                                    colors = TextFieldDefaults.colors(
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        disabledIndicatorColor = Color.Transparent,
                                        errorIndicatorColor = Color.Transparent
                                    )
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    imagePicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008000)),
                shape = RoundedCornerShape(6.dp)
            ) {
                Icon(imageVector = Icons.Default.Image, contentDescription = "Add Image")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Image")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // Clear and set captions as needed
                    createRequest.caption.clear()
                    createRequest.caption.add(postContent)
                    createRequest.caption.addAll(childPostContents)
                    createRequest.privacy = selectedPrivacy.uppercase()
                    Log.d("captions: total images: ", selectedImages.size.toString())
                    Log.d("captions: ", createRequest.caption.toList().toString())


                    // Convert createRequest to JSON string
                    val gson = Gson()
                    val jsonString = gson.toJson(createRequest)
                    val request = jsonString.toRequestBody("application/json".toMediaType())

                    // Convert selected images to MultipartBody.Part list with part name "images"
                    val imageParts = context.urisToMultipartParts(selectedImages, "images")

                    // Make the API call via Retrofit
                    coroutineScope.launch {
                        try {
                            val response = RetrofitClient.instance.createPost(request, imageParts)
                            if (response.isSuccessful) {
                                if (response.body()?.data != null) {
                                    Toast.makeText(
                                        context,
                                        response.body()?.message ?: "Post created successfully!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Log.d("new post", response.body()?.data.toString())
                                    navController.navigate("home") {
                                        popUpTo("post-create") { inclusive = true }
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        response.body()?.message ?: "Something wrong",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Post failed: ${response.code()}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: Exception) {
                            Log.e("Update post error", e.message.toString())
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text("POST", color = Color.White)
            }
        }
    }
}