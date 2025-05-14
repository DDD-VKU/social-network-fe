package vku.ddd.social_network_fe.ui.screens.profile

import android.app.DatePickerDialog
import android.net.Uri
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import vku.ddd.social_network_be.dto.request.AccountUpdateRequest
import vku.ddd.social_network_fe.data.api.RetrofitClient
import vku.ddd.social_network_fe.data.datastore.AccountDataStore
import vku.ddd.social_network_fe.data.datastore.TokenDataStore
import vku.ddd.social_network_fe.data.model.Account
import vku.ddd.social_network_fe.data.model.User
import vku.ddd.social_network_fe.data.model.response.AccountResponse
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navHostController: NavHostController) {
    var account by remember { mutableStateOf<Account?>(null) }
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var isHaveAvatar by remember { mutableStateOf(false) }
    val defaultAvatar = "https://cdn-icons-png.flaticon.com/512/149/149071.png"
    var isLoading by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // State for editable fields
    var fname by remember { mutableStateOf("") }
    var lname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    // Khởi tạo shouldSave null
    var shouldSave by remember { mutableStateOf<Account?>(null) }

    // DatePickerDialog setup
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    var selectedDate by remember { mutableStateOf<Date?>(null) }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _: DatePicker,
              selectedYear: Int,
              selectedMonth: Int,
              selectedDayOfMonth: Int ->
                calendar.set(selectedYear, selectedMonth, selectedDayOfMonth)
                selectedDate = calendar.time
                dob = dateFormat.format(selectedDate)
            },
            year,
            month,
            day
        )
    }

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
        }
    }

    // Load account data
    LaunchedEffect(Unit) {
        account = AccountDataStore(context).getAccount()
        account?.let {
            fname = it.fname
            lname = it.lname
            username = it.username
            email = it.email
            //gender = it.gender
            dob = it.dob?.let { dateFormat.format(it) } ?: ""
            selectedDate = it.dob
            isHaveAvatar = it.avatar != 0L
        }
    }

    // Save account data
    LaunchedEffect(shouldSave) {
        shouldSave?.let { updatedAccount ->
            isLoading = true
            try {
                val token = TokenDataStore(context).getToken()
                if (token.isNullOrEmpty()) {
                    Toast.makeText(context, "Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show()
                    navHostController.navigate("login")
                    return@LaunchedEffect
                }

                Log.d("EditProfileScreen", "Token before sending: $token")

                val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = inputFormat.parse(dob)
                val formattedDate = outputFormat.format(date)

                val request = AccountUpdateRequest(
                    username = updatedAccount.username,
                    fname = updatedAccount.fname,
                    lname = updatedAccount.lname,
                    email = updatedAccount.email,
                    dob = formattedDate
                )

                val gson = Gson()
                val requestJson = gson.toJson(request)
                Log.d("EditProfileScreen", "Request JSON: $requestJson")
                val requestBody = requestJson.toRequestBody("application/json".toMediaTypeOrNull())

                // Create MultipartBody.Part for avatar if an image is selected
                val avatarPart = selectedImageUri?.let { uri ->
                    val inputStream = context.contentResolver.openInputStream(uri)
                    val tempFile = File.createTempFile("avatar", ".jpg", context.cacheDir)
                    FileOutputStream(tempFile).use { outputStream ->
                        inputStream?.copyTo(outputStream)
                    }
                    val requestFile = tempFile.asRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("avatar", tempFile.name, requestFile)
                }

                val response = RetrofitClient.instance.updateAccount(
                    token = "Bearer $token",
                    request = requestBody,
                    avatar = avatarPart
                )

                if (response.isSuccessful) {
                    val accountResponse = response.body()?.data
                    if (accountResponse != null) {
                        try {
                            // Lấy danh sách followers và following từ API
                            val followersResponse = RetrofitClient.instance.getFollowers(accountResponse.id)
                            val followingResponse = RetrofitClient.instance.getFollowing(accountResponse.id)

                            val followers = if (followersResponse.isSuccessful) {
                                followersResponse.body()?.data ?: emptyList()
                            } else {
                                emptyList()
                            }

                            val following = if (followingResponse.isSuccessful) {
                                followingResponse.body()?.data ?: emptyList()
                            } else {
                                emptyList()
                            }

                            // Chuyển đổi AccountResponse thành Account
                            val updatedAccount = Account(
                                id = accountResponse.id,
                                username = accountResponse.username ?: "",
                                fname = accountResponse.fname ?: "",
                                lname = accountResponse.lname ?: "",
                                email = accountResponse.email ?: "",
                                bio = accountResponse.bio ?: "",
                                avatar = accountResponse.avatar,
                                dob = accountResponse.dob ?: Date(),
                                followers = followers,
                                following = following
                            )
                            AccountDataStore(context).saveAccount(updatedAccount)
                            account = updatedAccount
                            Toast.makeText(context, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Log.e("EditProfileScreen", "Error getting followers/following", e)
                            Toast.makeText(context, "Lỗi khi lấy danh sách followers/following", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.e("EditProfileScreen", response.body().toString())
                        Toast.makeText(context, "Cập nhật thông tin thất bại!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("EditProfileScreen", "Error response: $errorBody")
                    Log.e("EditProfileScreen", "Error code: ${response.code()}")
                    Toast.makeText(context, "Cập nhật thông tin thất bại: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("EditProfileScreen", "Error updating account", e)
                Toast.makeText(context, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
            }
            isLoading = false
            shouldSave = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chỉnh sửa hồ sơ") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigate("setting") }) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Personal Info Row
            ListItem(
                headlineContent = {
                    Text("Thông tin cá nhân",
                        fontSize = 14.sp,
                        color = Color.Gray)
                },
                supportingContent = {
                    Text(
                        text = account?.let { "${it.lname} ${it.fname}" } ?: "Chưa cập nhật",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                },
                leadingContent = {
                    AsyncImage(
                        model = if (isHaveAvatar) {
                            "http://10.0.2.2:8080/social-network/api/uploads/images/${account!!.avatar}"
                        } else {
                            defaultAvatar
                        },
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .padding(4.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                },
                trailingContent = {
                    Icon(Icons.Default.ArrowForwardIos,
                        contentDescription = null,
                        tint = Color.Gray)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDialog = true }
            )

            Divider()

            // Bio Row
            ListItem(
                leadingContent = {
                    Icon(Icons.Default.Phone, contentDescription = null)
                },
                headlineContent = { Text("SDT") },
                supportingContent = {
                    Text(
                        text = account?.bio?.takeIf { it.isNotBlank() } ?: "Chưa cập nhật",
                    )
                },
            )

            Divider()

            // Email Row
            ListItem(
                leadingContent = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                headlineContent = { Text("Email") },
                supportingContent = {
                    Text(
                        text = account?.email?.takeIf { it.isNotBlank() } ?: "Chưa cập nhật",
                    )
                },
            )

            Divider()

            // Gender Row
            ListItem(
                leadingContent = {
                    Icon(Icons.Default.Face, contentDescription = null)
                },
                headlineContent = { Text("Giới tính") },
                supportingContent = {
                    Text("Nam"
                        //text = account?.gender?.takeIf { it.isNotBlank() } ?: "Chưa cập nhật",
                    )
                },
            )

            Divider()

            // Date of Birth Row
            ListItem(
                leadingContent = {
                    Icon(Icons.Default.DateRange, contentDescription = null)
                },
                headlineContent = { Text("Ngày sinh") },
                supportingContent = {
                    Text(
                        text = account?.dob?.let { dateFormat.format(it) } ?: "Chưa cập nhật",
                    )
                },
            )

            Divider()
        }
        // Loading Dialog
        if (isLoading) {
            Dialog(onDismissRequest = {}) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }
        }

        // Edit Dialog
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(onClick = {
                        account?.let { currentAccount ->
                            val updatedAccount = currentAccount.copy(
                                fname = fname,
                                lname = lname,
                                username = username,
                                email = email,
                                dob = selectedDate ?: currentAccount.dob
                            )
                            shouldSave = updatedAccount
                        }
                        showDialog = false
                    }) {
                        Text("Lưu")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Hủy")
                    }
                },
                title = { Text("Chỉnh sửa thông tin") },
                text = {
                    Column {
                        // Avatar selection
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = selectedImageUri ?: if (isHaveAvatar) {
                                        "http://10.0.2.2:8080/social-network/api/uploads/images/${account!!.avatar}"
                                    } else {
                                        defaultAvatar
                                    },
                                    contentDescription = "Profile Avatar",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, Color.Gray, CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = { imagePickerLauncher.launch("image/*") }
                                ) {
                                    Text("Chọn ảnh đại diện")
                                }
                            }
                        }

                        OutlinedTextField(
                            value = fname,
                            onValueChange = { fname = it },
                            label = { Text("Họ") }
                        )
                        OutlinedTextField(
                            value = lname,
                            onValueChange = { lname = it },
                            label = { Text("Tên") }
                        )
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            label = { Text("Username") }
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") }
                        )
                        OutlinedTextField(
                            value = gender,
                            onValueChange = { gender = it },
                            label = { Text("Giới tính") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    BorderStroke( 1.dp, Color.Gray),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clip(RoundedCornerShape(8.dp)),
                        ) {
                            ListItem(
                                leadingContent = {
                                    Icon(Icons.Default.DateRange, contentDescription = null)
                                },
                                headlineContent = { Text("Ngày sinh") },
                                supportingContent = {
                                    Text(
                                        text = account?.dob?.let { dateFormat.format(it) } ?: "Chưa cập nhật",
                                    )
                                },
                                modifier = Modifier.clickable { datePickerDialog.show() }
                            )
                        }
                    }
                }
            )
        }
    }
}