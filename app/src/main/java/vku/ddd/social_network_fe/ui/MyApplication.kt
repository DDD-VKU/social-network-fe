package vku.ddd.social_network_fe.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.HttpMethod
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import vku.ddd.social_network_fe.data.api.StompManager
import kotlin.time.Duration.Companion.seconds

class MyApplication : Application(), LifecycleObserver {
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        Log.e("MyApplication", "WebSocket error: ${e.message}")
    }

    private val httpClient by lazy {
        HttpClient(CIO) {
            install(WebSockets) {
                pingInterval = 20.seconds
            }
        }
    }

    val stompManager: StompManager by lazy { StompManager(httpClient) }

    private var hasConnectedOnce = false

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        connectWebSocketIfNeeded()
    }

    private fun connectWebSocketIfNeeded() {
        if (hasConnectedOnce) {
            Log.d("MyApplication", "WebSocket already connected once, skip.")
            return
        }

        appScope.launch(exceptionHandler) {
            try {
                stompManager.connect(
                    host = "10.0.2.2",
                    port = 8080,
                    path = "/social-network/ws"
                )
                hasConnectedOnce = true
                Log.d("MyApplication", "WebSocket connection established successfully.")
            } catch (e: Exception) {
                Log.e("MyApplication", "Failed to connect WebSocket: ${e.message}")
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        // Không cần disconnect, giữ kết nối!
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        // Cũng không reconnect nữa.
        Log.d("MyApplication", "App foregrounded, but WebSocket already connected once.")
    }

    override fun onTerminate() {
        appScope.launch {
            stompManager.disconnect()
        }
        appScope.cancel()
        super.onTerminate()
    }
}
