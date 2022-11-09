package com.example.nawanolza.hideandseek

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutionException

object MessageSenderService {
    private val tag = "MessageSenderService"
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    fun sendMessageToWearable(path: String, message: String, context: Context) {
        coroutineScope.launch {
            sendMessageToWearableInBackground(path, message, context)
        }
    }

    private fun sendMessageToWearableInBackground(path: String, message: String, context: Context) {
        val nodeListTask = Wearable.getNodeClient(context).connectedNodes
        try {
            val nodes = Tasks.await(nodeListTask)

            if(nodes.isEmpty()) {
                Log.i(tag, "No Node found to send message")
            }

            for(node in nodes) {
                val sendMessageTask = Wearable.getMessageClient(context)
                    .sendMessage(node.id, path, message.toByteArray())
                try {
                    val result = Tasks.await(sendMessageTask)
                    Log.v(tag, "SendThread: message send to " + node.displayName)
                } catch (exception: ExecutionException) {
                    Log.e(tag, "Task failed: $exception")
                } catch (exception: InterruptedException) {
                    Log.e(tag, "Interrupt occurred: $exception")
                }
            }
        } catch (exception: ExecutionException) {
            Log.e(tag, "Task failed: $exception")
        } catch (exception: InterruptedException) {
            Log.e(tag, "Interrupt occurred: $exception")
        }
    }
}