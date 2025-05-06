package vku.ddd.social_network_fe.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import vku.ddd.social_network_fe.data.api.RetrofitClient
import vku.ddd.social_network_fe.data.api.StompManager
import vku.ddd.social_network_fe.data.enums.NotificationType
import vku.ddd.social_network_fe.data.model.Notification
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NotificationViewModel : ViewModel() {
    val notifications = mutableStateListOf<Notification>()
    private var stompManager: StompManager? = null

    fun add(notification: Notification) {
        notifications.add(0, notification)
    }

    fun loadData(data: List<Notification>) {
        notifications.clear()
        notifications.addAll(data)
    }

    suspend fun connectStomp(userId: Long) {
        stompManager = StompManager(RetrofitClient.stompHttpClient).apply {
            connect("localhost", 8080, "/social-network/ws")
            subscribe("/api/notification") { message ->
                try {
                    val json = JSONObject(message)
                    val newNotification = Notification(
                        id = json.optLong("id", 0),
                        title = json.optString("title", ""),
                        body = json.optString("body", ""),
                        type = try {
                            NotificationType.valueOf(json.optString("type", NotificationType.LIKE_POST.name))
                        } catch (e: IllegalArgumentException) {
                            NotificationType.LIKE_POST
                        },
                        hrefId = json.optLong("href_id", 0),
                        createdAt = try {
                            LocalDateTime.parse(json.optString("createdAt", ""), DateTimeFormatter.ISO_DATE_TIME)
                        } catch (e: Exception) {
                            LocalDateTime.now()
                        }
                    )
                    add(newNotification)
                } catch (e: Exception) {
                    Log.e("STOMP", "Lỗi xử lý message: ${e.message}")
                }
            }
        }
    }
}
