package com.rockfit.ksa.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val SESSION_STORE = "rockfit_session"
private val Context.sessionDataStore by preferencesDataStore(SESSION_STORE)

data class StoredSession(
    val email: String,
    val role: String
)

object SessionStore {
    private val EMAIL = stringPreferencesKey("email")
    private val ROLE = stringPreferencesKey("role")

    fun sessionFlow(context: Context): Flow<StoredSession?> {
        return context.sessionDataStore.data.map { prefs: Preferences ->
            val email = prefs[EMAIL]
            val role = prefs[ROLE]
            if (email.isNullOrBlank() || role.isNullOrBlank()) null else StoredSession(email, role)
        }
    }

    suspend fun setSession(context: Context, email: String, role: String) {
        context.sessionDataStore.edit { prefs ->
            prefs[EMAIL] = email
            prefs[ROLE] = role
        }
    }

    suspend fun clear(context: Context) {
        context.sessionDataStore.edit { prefs ->
            prefs.remove(EMAIL)
            prefs.remove(ROLE)
        }
    }
}
