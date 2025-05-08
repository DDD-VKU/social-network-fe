package vku.ddd.social_network_fe.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import vku.ddd.social_network_fe.data.model.Notification
import vku.ddd.social_network_fe.data.model.Post

class NotificationViewModel: ViewModel() {
    val notifications = mutableStateListOf<Notification>()

    fun loadData(data: List<Notification>) {
        notifications.addAll(data)
    }
}