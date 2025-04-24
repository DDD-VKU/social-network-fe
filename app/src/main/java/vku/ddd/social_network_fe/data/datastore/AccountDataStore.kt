package vku.ddd.social_network_fe.data.datastore

import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import vku.ddd.social_network_fe.data.model.Account

class AccountDataStore(private val context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore("account_pref")
        private val ACCOUNT_KEY = stringPreferencesKey("account")
    }

    suspend fun saveAccount(account: Account) {
        val json = Gson().toJson(account)
        context.dataStore.edit { pref ->
            pref[ACCOUNT_KEY] = json
        }
    }

    suspend fun getAccount(): Account? {
        val pref = context.dataStore.data.first()
        val account = Gson().fromJson(pref[ACCOUNT_KEY], Account::class.java)
        return account
    }

    suspend fun clearAccount() {
        context.dataStore.edit {
            it.remove(ACCOUNT_KEY)
        }
    }
}