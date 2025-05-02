package vku.ddd.social_network_fe.data.api

import android.util.Log
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.json.JSONObject
import java.util.*
import kotlin.coroutines.CoroutineContext

class StompManager(
    private val client: HttpClient,
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) {
    private var session: DefaultClientWebSocketSession? = null
    private val messageHandlers = mutableMapOf<String, (String) -> Unit>()
    private val scope = CoroutineScope(coroutineContext + SupervisorJob())
    private var _stompConnected = false

    // Flow báo trạng thái STOMP CONNECTED
    private val _connectionState = MutableStateFlow(false)
    val connectionState: StateFlow<Boolean> = _connectionState.asStateFlow()

    fun isConnected(): Boolean = _stompConnected && session?.isActive == true

    /**
     * Connect to raw WebSocket endpoint and perform STOMP handshake
     * @param path full servlet path including context, e.g. "/social-network/ws"
     */
    suspend fun connect(host: String, port: Int, path: String) {
        try {
            client.webSocket(
                method = HttpMethod.Get,
                host = host,
                port = port,
                path = path,
                request = {
                    // Request STOMP subprotocol
                    header(HttpHeaders.SecWebSocketProtocol, "v10.stomp")
                }
            ) {
                session = this
                // Send STOMP CONNECT frame
                val connectFrame = buildString {
                    append("CONNECT\n")
                    append("accept-version:1.2\n")
                    append("heart-beat:10000,10000\n")
                    append("host:\$host\n")
                    append("\n")
                    append("\u0000")
                }
                send(Frame.Text(connectFrame))
                Log.d("StompManager", "STOMP CONNECT sent on $path")

                // Collect incoming frames
                incoming
                    .receiveAsFlow()
                    .filterIsInstance<Frame.Text>()
                    .collect { frame ->
                        val text = frame.readText()
                        when {
                            text.startsWith("CONNECTED") -> {
                                _stompConnected = true
                                _connectionState.value = true
                                Log.d("StompManager", "STOMP CONNECTED")
                            }
                            text.startsWith("MESSAGE") || text.startsWith("RECEIPT") || text.startsWith("ERROR") -> {
                                messageHandlers.values.forEach { it(text) }
                            }
                        }
                    }
            }
        } catch (e: Exception) {
            Log.e("StompManager", "Connection error: \${e.message}")
            throw e
        } finally {
            session = null
            _stompConnected = false
            _connectionState.value = false
            Log.d("StompManager", "WebSocket/STOMP disconnected")
        }
    }

    fun subscribe(topic: String, handler: (String) -> Unit) {
        messageHandlers[topic] = handler
        scope.launch {
            if (isConnected()) {
                val subscribeFrame = buildString {
                    append("SUBSCRIBE\n")
                    append("id:\${UUID.randomUUID()}\n")
                    append("destination:\$topic\n")
                    append("\n")
                    append("\u0000")
                }
                session?.send(Frame.Text(subscribeFrame))
                Log.d("StompManager", "Subscribed to \$topic")
            } else {
                Log.e("StompManager", "Cannot subscribe - not connected")
            }
        }
    }

    fun sendMessage(frameText: String) {
        scope.launch {
            if (isConnected()) {
                session?.send(Frame.Text(frameText))
                Log.d("StompManager", "Sent message frame")
            } else {
                Log.e("StompManager", "Send failed - not connected")
            }
        }
    }

    suspend fun disconnect() {
        try {
            session?.close()
        } catch (e: Exception) {
            Log.e("StompManager", "Disconnect error: \${e.message}")
        } finally {
            session = null
            _stompConnected = false
            _connectionState.value = false
            client.close()
            scope.coroutineContext.cancelChildren()
        }
    }
}