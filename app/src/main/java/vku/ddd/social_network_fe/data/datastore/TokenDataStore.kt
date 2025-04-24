package vku.ddd.social_network_fe.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class TokenDataStore(private val context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore(name = "auth_pref")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val EXPIRE_TIME_KEY = longPreferencesKey("expire_time")
    }

    suspend fun saveToken(token: String, expireTime: Long) {
        context.dataStore.edit { pref ->
            pref[TOKEN_KEY] = token
            pref[EXPIRE_TIME_KEY] = expireTime
        }
    }

    suspend fun getToken(): String? {
        val pref = context.dataStore.data.first()
        val token = pref[TOKEN_KEY]
        val expireTime = pref[EXPIRE_TIME_KEY] ?: 0L
        return if (System.currentTimeMillis() > expireTime) {
            clearToken()
            null
        } else token
    }

    suspend fun clearToken() {
        context.dataStore.edit { it.clear() }
    }
}