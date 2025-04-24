package vku.ddd.social_network_fe.ui.screens.auth

import android.app.DatePickerDialog
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import okhttp3.internal.format
import vku.ddd.social_network_be.dto.request.AccountCreateRequest
import vku.ddd.social_network_fe.data.api.RetrofitClient
import vku.ddd.social_network_fe.data.datastore.AccountDataStore
import vku.ddd.social_network_fe.data.datastore.TokenDataStore
import vku.ddd.social_network_fe.data.model.Account
import vku.ddd.social_network_fe.ui.components.Common.ValidateTextField
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun RegisterScreen(navController: NavHostController) {
    val fname = remember { mutableStateOf("") }
    val lname = remember { mutableStateOf("") }
    var username = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var passwordConfirm = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }

    val fnameError = remember { mutableStateOf<String?>(null) }
    val lnameError = remember { mutableStateOf<String?>(null) }
    var usernameError = remember { mutableStateOf<String?>(null) }
    var passwordError = remember { mutableStateOf<String?>(null) }
    var passwordConfirmError = remember { mutableStateOf<String?>(null) }
    var emailError = remember { mutableStateOf<String?>(null) }

    val genders = listOf("Male", "Female", "Other")
    var selectedGender by remember { mutableStateOf(genders[0]) }
    var expanded by remember { mutableStateOf(false) }
    // Date of Birth selection
    var selectedDate by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Column ( modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF0F2F5))
        .padding(end = 16.dp, start = 16.dp, top = 16.dp)
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Row (modifier = Modifier
            .padding(10.dp)
        ) {
            Text (
                text = "Footnote",
                color = Color.Blue,
                fontSize = 32.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            )
        }
        Row (modifier = Modifier
            .padding(6.dp)
        ) {
            Text("Conect with friends and read the best news! ")
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(top = 60.dp)
        ) {
            val textFieldModifier = Modifier
                .height(60.dp)
                .fillMaxWidth(0.9f)
            ValidateTextField(
                lname,
                "Last name",
                lnameError,
                {if (it.isBlank()) "Last name is required" else null},
                textFieldModifier
            )
            ValidateTextField(
                fname,
                "First name",
                fnameError,
                {if (it.isBlank()) "First name is required" else null},
                textFieldModifier
            )
            ValidateTextField(
                username,
                "Username",
                usernameError,
                {if (it.isBlank()) "Username is required"
                else if (it.length <= 7) "Username must be at least 8 characters"
                else null},
                textFieldModifier
            )
            ValidateTextField(
                password,
                "Password",
                passwordError,
                {if (it.isBlank()) "Password cannot be blank"
                else if (it.length < 8) "Password must be at least 8 characters"
                else null},
                textFieldModifier
            )
            ValidateTextField(
                passwordConfirm,
                "Password confirm",
                passwordConfirmError,
                {if (it != password.value) "Password doesn't match"
                else null},
                textFieldModifier
            )
            ValidateTextField(
                email,
                "Email",
                emailError,
                {if (!isValidEmail(it)) "Invalid email format" else null},
                textFieldModifier
            )

            // Gender selection
            Box {
                Button(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(width = 1.dp, color = Color.DarkGray),
                    contentPadding = PaddingValues(0.dp),
                    modifier = textFieldModifier,
                    shape = RoundedCornerShape(2.dp),
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(selectedGender, fontSize = 16.sp, color = Color.DarkGray)
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown"
                        )
                    }
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    genders.forEach { gender ->
                        DropdownMenuItem(onClick = {
                            selectedGender = gender
                            expanded = false
                        },
                            text = { Text(gender) }
                        )
                    }
                }
            }

            if (showDatePicker) {
                val context = LocalContext.current
                val calendar = Calendar.getInstance()

                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        selectedDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
                        showDatePicker = false
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            OutlinedTextField(
                modifier = textFieldModifier,
                value = selectedDate,
                onValueChange = {},
                readOnly = true,
                label = { Text("Date of Birth") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select Date",
                        modifier = Modifier.clickable { showDatePicker = true }
                    )
                }
            )
        }

        val interactionSource = remember { MutableInteractionSource() }
        val isHovered by interactionSource.collectIsPressedAsState()
        Spacer (modifier = Modifier .padding(14.dp))
        Button(
            onClick = {
                lnameError.value = if (lname.value.isBlank()) "Last name is required" else null
                fnameError.value = if (fname.value.isBlank()) "First name is required" else null
                usernameError.value = if (username.value.isBlank()) "Username is required"
                    else if (username.value.length <= 7) "Username must be at least 8 characters"
                    else null;
                passwordError.value = if (password.value.isBlank()) "Password cannot be blank"
                    else if (password.value.length < 8) "Password must be at least 8 characters"
                    else null;
                passwordConfirmError.value = if (passwordConfirm.value != password.value) "Password doesn't match"
                    else null;
                if (
                    lnameError.value == null &&
                    fnameError.value == null &&
                    usernameError.value == null &&
                    passwordError.value == null &&
                    passwordConfirmError.value == null &&
                    selectedDate != ""
                ) {
                    val tokenDataStore = TokenDataStore(context)
                    val accountDataStore = AccountDataStore(context)
                    val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val date = inputFormat.parse(selectedDate)
                    val formattedDate = outputFormat.format(date)
                    coroutineScope.launch {
                        val request = AccountCreateRequest(
                            username = username.value,
                            password = password.value,
                            passwordConfirm = passwordConfirm.value,
                            fname = fname.value,
                            lname = lname.value,
                            email = email.value,
                            gender = genders.indexOf(selectedGender),
                            dob = formattedDate
                        )
                        val response = RetrofitClient.authInstance.register(request)
                        if (response.isSuccessful) {
                            val token = response.body()?.data?.token.orEmpty()
                            if (token.length > 0) {
                                val expireTime = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000)
                                tokenDataStore.saveToken(token, expireTime)
                                RetrofitClient.setToken(token)
                                val account = response.body()?.data!!.account
                                accountDataStore.saveAccount(
                                    Account(
                                        id = account.id,
                                        username = account.username,
                                        fname = account.fname,
                                        lname = account.lname,
                                        email = account.email,
                                        avatar = account.avatar
                                    )
                                )
                                navController.navigate("home")
                            } else {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(70.dp)
                .padding(6.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if (isHovered) Color(0xFF135DB5) else Color(0xFF1877F2)
            ),
            interactionSource = interactionSource
        ) {
            Text("Create", fontSize = 19.sp, fontWeight = FontWeight.ExtraBold)
        }
        Spacer(modifier = Modifier.height(18.dp))


        Spacer(modifier = Modifier.height(18.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Divider(
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "  or  ",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Divider(
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(70.dp))
        Column ( modifier = Modifier
            .fillMaxSize(0.9f)
            .padding(bottom = 1.dp), // Cách đáy 10dp
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                Text("Have accout ?")
                Button(
                    modifier = Modifier,
                    shape = RoundedCornerShape(5.dp),
                    onClick = {navController.navigate("login")},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE573E5))
                ) { Text("Login") }
            }
        }
    }
}