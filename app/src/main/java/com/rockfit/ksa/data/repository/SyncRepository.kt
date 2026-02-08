package com.rockfit.ksa.data.repository

import android.content.Context
import com.rockfit.ksa.data.AthleteStore
import com.rockfit.ksa.model.AthleteProfileCard
import com.rockfit.ksa.model.SampleData

interface SyncRepository {
    suspend fun fetchAthletes(): List<AthleteProfileCard>
    suspend fun sync()
}

class SyncRepositoryImpl(
    private val context: Context
) : SyncRepository {
    override suspend fun fetchAthletes(): List<AthleteProfileCard> {
        // TODO: Replace with Supabase API calls.
        return SampleData.athleteProfiles
    }

    override suspend fun sync() {
        val remote = fetchAthletes()
        AthleteStore.setAthletes(context, remote)
    }
}
