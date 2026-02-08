package com.rockfit.ksa.data.remote

import com.rockfit.ksa.BuildConfig

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

data class AuthSession(
    val userId: String,
    val email: String
)

interface AuthRepository {
    suspend fun signIn(email: String, password: String): AuthSession
    suspend fun signUp(email: String, password: String)
    suspend fun resetPassword(email: String)
    suspend fun signOut()
}

class AuthRepositorySupabase : AuthRepository {
    override suspend fun signIn(email: String, password: String): AuthSession {
        val url = URL("${SupabaseConfig.URL}/auth/v1/token?grant_type=password")
        val body = JSONObject()
            .put("email", email.trim())
            .put("password", password)
            .toString()
        val response = request(url, body)
        val user = response.getJSONObject("user")
        return AuthSession(
            userId = user.getString("id"),
            email = user.getString("email")
        )
    }

    override suspend fun signUp(email: String, password: String) {
        val url = URL("${SupabaseConfig.URL}/auth/v1/signup")
        val body = JSONObject()
            .put("email", email.trim())
            .put("password", password)
            .toString()
        request(url, body)
    }

    override suspend fun resetPassword(email: String) {
        val url = URL("${SupabaseConfig.URL}/auth/v1/recover")
        val body = JSONObject()
            .put("email", email.trim())
            .toString()
        request(url, body)
    }

    override suspend fun signOut() {
        // TODO: Add token-based sign out if needed.
    }

    private fun request(url: URL, body: String): JSONObject {
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("apikey", SupabaseConfig.ANON_KEY)
            setRequestProperty("Authorization", "Bearer ${SupabaseConfig.ANON_KEY}")
            doOutput = true
        }
        connection.outputStream.use { os ->
            os.write(body.toByteArray(Charsets.UTF_8))
        }
        val status = connection.responseCode
        val stream = if (status in 200..299) connection.inputStream else connection.errorStream
        val text = BufferedReader(InputStreamReader(stream)).readText()
        if (status !in 200..299) {
            val message = runCatching {
                val errorJson = JSONObject(text)
                when {
                    errorJson.has("error_description") -> errorJson.getString("error_description")
                    errorJson.has("msg") -> errorJson.getString("msg")
                    errorJson.has("message") -> errorJson.getString("message")
                    errorJson.has("error") -> errorJson.getString("error")
                    else -> text
                }
            }.getOrElse { text }
            throw IllegalStateException(message.ifBlank { text })
        }
        return if (text.isBlank()) JSONObject() else JSONObject(text)
    }
}
