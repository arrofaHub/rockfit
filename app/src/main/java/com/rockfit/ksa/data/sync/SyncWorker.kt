package com.rockfit.ksa.data.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rockfit.ksa.data.repository.SyncRepositoryImpl

class SyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        return try {
            SyncRepositoryImpl(applicationContext).sync()
            Result.success()
        } catch (ex: Exception) {
            Result.retry()
        }
    }
}
