package vku.ddd.social_network_fe.ui.screens.notification

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.json.JSONObject
import vku.ddd.social_network_fe.data.api.RetrofitClient
import vku.ddd.social_network_fe.data.api.StompManager
import vku.ddd.social_network_fe.data.datastore.AccountDataStore
import vku.ddd.social_network_fe.data.enums.NotificationType
import vku.ddd.social_network_fe.data.model.Notification
import vku.ddd.social_network_fe.ui.viewmodel.NotificationViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun NotificationScreen(navController: NavHostController) {
    val notificationViewModel: NotificationViewModel = viewModel()
    val context = LocalContext.current
    val accountDataStore = remember { AccountDataStore(context) }

    LaunchedEffect(key1 = true) {
        val account = accountDataStore.getAccount()
        if (account != null) {
            // Gọi API lấy danh sách thông báo
            val fetchedNotifications = getNotificationsById(account.id)
            if (fetchedNotifications != null) {
                notificationViewModel.loadData(fetchedNotifications)
            } else {
                Log.w("NotificationScreen", "Không thể tải danh sách thông báo.")
            }

            // Kết nối STOMP để nhận thông báo realtime
//            notificationViewModel.connectStomp(account.id)
        } else {
            Log.w("NotificationScreen", "Không tìm thấy tài khoản.")
        }
    }


    LazyColumn (
        modifier = Modifier.fillMaxSize()
    ) {
        items (notificationViewModel.notifications.size) { i ->Column {
            Row (
                modifier = Modifier
                    .background(Color(0xFFE7F8FF))
                    .padding(top = 10.dp, bottom = 3.dp)
                    .wrapContentHeight()
                    .background(Color(0xFFE7F8FF))
                    .fillParentMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Box (
                    modifier = Modifier
                        .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                        .size(40.dp)
                ) {  }
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(text = notificationViewModel.notifications[i].title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = notificationViewModel.notifications[i].createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                }
            }
            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
        }
        }
    }
}

suspend fun getNotificationsById(id: Long): List<Notification>? {
    return try {
        val response = RetrofitClient.instance.getAllNotifications()

        Log.e("abc def", "API: ${response.body()}")

        if (response.isSuccessful) {
            response.body()?.data
        } else {
            emptyList()
        }
    }catch (e: Exception) {
        Log.e("abc def", "Exception: ${e.message}")
        null
    }
}