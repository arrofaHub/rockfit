package com.rockfit.ksa.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rockfit.ksa.model.AthleteProfileCard
import com.rockfit.ksa.model.LifestyleProfile
import com.rockfit.ksa.model.NutritionStyle
import com.rockfit.ksa.model.SampleData
import com.rockfit.ksa.model.StressLevel
import com.rockfit.ksa.model.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

private const val STORE_NAME = "rockfit_prefs"
private val Context.dataStore by preferencesDataStore(STORE_NAME)

object AthleteStore {
    private val ACTIVE_ATHLETE_ID = stringPreferencesKey("active_athlete_id")
    private val ATHLETES_JSON = stringPreferencesKey("athletes_json")

    val athletes: List<AthleteProfileCard> = SampleData.athleteProfiles

    fun activeAthleteIdFlow(context: Context): Flow<String> {
        return context.dataStore.data.map { prefs: Preferences ->
            prefs[ACTIVE_ATHLETE_ID] ?: athletes.first().id
        }
    }

    fun athletesFlow(context: Context): Flow<List<AthleteProfileCard>> {
        return context.dataStore.data.map { prefs ->
            val json = prefs[ATHLETES_JSON]
            if (json.isNullOrBlank()) {
                SampleData.athleteProfiles
            } else {
                decodeAthletes(json)
            }
        }
    }

    suspend fun setActiveAthlete(context: Context, athleteId: String) {
        context.dataStore.edit { prefs ->
            prefs[ACTIVE_ATHLETE_ID] = athleteId
        }
    }

    suspend fun setAthletes(context: Context, list: List<AthleteProfileCard>) {
        context.dataStore.edit { prefs ->
            prefs[ATHLETES_JSON] = encodeAthletes(list)
        }
    }

    suspend fun ensureSeed(context: Context) {
        context.dataStore.edit { prefs ->
            val existing = prefs[ATHLETES_JSON]
            if (existing.isNullOrBlank()) {
                prefs[ATHLETES_JSON] = encodeAthletes(SampleData.athleteProfiles)
            }
        }
    }

    private fun encodeAthletes(list: List<AthleteProfileCard>): String {
        val array = JSONArray()
        list.forEach { athlete ->
            val json = JSONObject()
            json.put("id", athlete.id)
            json.put("name", athlete.name)
            json.put("sport", athlete.sport)
            json.put("role", athlete.role.name)
            val life = JSONObject()
            life.put("avgSleepHours", athlete.lifestyle.avgSleepHours)
            life.put("hydrationLiters", athlete.lifestyle.hydrationLiters)
            life.put("caffeineMg", athlete.lifestyle.caffeineMg)
            life.put("nutritionStyle", athlete.lifestyle.nutritionStyle.name)
            life.put("trainingSessionsPerWeek", athlete.lifestyle.trainingSessionsPerWeek)
            life.put("stressLevel", athlete.lifestyle.stressLevel.name)
            life.put("travelDaysPerMonth", athlete.lifestyle.travelDaysPerMonth)
            life.put("recentInjuryNotes", athlete.lifestyle.recentInjuryNotes)
            json.put("lifestyle", life)
            array.put(json)
        }
        return array.toString()
    }

    private fun decodeAthletes(raw: String): List<AthleteProfileCard> {
        val array = JSONArray(raw)
        val list = mutableListOf<AthleteProfileCard>()
        for (i in 0 until array.length()) {
            val json = array.getJSONObject(i)
            val life = json.getJSONObject("lifestyle")
            val lifestyle = LifestyleProfile(
                avgSleepHours = life.getDouble("avgSleepHours"),
                hydrationLiters = life.getDouble("hydrationLiters"),
                caffeineMg = life.getInt("caffeineMg"),
                nutritionStyle = NutritionStyle.valueOf(life.getString("nutritionStyle")),
                trainingSessionsPerWeek = life.getInt("trainingSessionsPerWeek"),
                stressLevel = StressLevel.valueOf(life.getString("stressLevel")),
                travelDaysPerMonth = life.getInt("travelDaysPerMonth"),
                recentInjuryNotes = life.getString("recentInjuryNotes")
            )
            list.add(
                AthleteProfileCard(
                    id = json.getString("id"),
                    name = json.getString("name"),
                    sport = json.getString("sport"),
                    role = UserRole.valueOf(json.getString("role")),
                    lifestyle = lifestyle
                )
            )
        }
        return list
    }
}
