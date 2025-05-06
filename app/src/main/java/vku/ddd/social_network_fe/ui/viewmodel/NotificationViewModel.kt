package vku.ddd.social_network_fe.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import vku.ddd.social_network_fe.data.model.Notification

class NotificationViewModel: ViewModel() {
    val notifications = mutableStateListOf<Notification>()

    fun add(notification: Notification) {
        notifications.add(notification)
    }

    fun loadData(data: List<Notification>) {
        notifications.clear()
        notifications.addAll(data)
    }
}