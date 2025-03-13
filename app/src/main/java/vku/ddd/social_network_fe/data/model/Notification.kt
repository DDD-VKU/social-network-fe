package vku.ddd.social_network_fe.data.model

import vku.ddd.social_network_fe.data.enums.NotificationType
import java.time.LocalDateTime

data class Notification(
    val id: Long,
    val title: String,
    val body: String,
    val hrefId: Long,
    val type: NotificationType,
    val createdAt: LocalDateTime
)
