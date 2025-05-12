package vku.ddd.social_network_fe.ui.screens.profile

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import vku.ddd.social_network_fe.data.datastore.AccountDataStore
import vku.ddd.social_network_fe.data.model.Account
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
            AccountDataStore(context).saveAccount(updatedAccount)
            account = updatedAccount
            shouldSave = null // Reset to prevent re-saving
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
                                //gender = gender,
                                dob = selectedDate ?: currentAccount.dob
                            )
                            shouldSave = updatedAccount // Trigger save via state
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