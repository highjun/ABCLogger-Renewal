package kaist.iclab.abclogger.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncWorker(context: Context, workerParameters: WorkerParameters):CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        // Upload via gRPC
        return Result.success()
    }
}