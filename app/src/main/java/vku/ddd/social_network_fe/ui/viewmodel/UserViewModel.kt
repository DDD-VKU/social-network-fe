package vku.ddd.social_network_fe.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import vku.ddd.social_network_fe.data.model.User

class UserViewModel: ViewModel() {
    val users = mutableStateListOf<User>()

    fun loadData(data: List<User>) {
        users.clear()
        users.addAll(data)
    }

    fun removeUsers() {
        users.clear()
    }
}