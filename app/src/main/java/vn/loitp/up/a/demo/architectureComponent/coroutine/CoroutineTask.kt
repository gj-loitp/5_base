package vn.loitp.up.a.demo.architectureComponent.coroutine

import com.loitp.core.ext.d
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CoroutineTask : CoroutineScope {
    private val logTag = javaClass.simpleName
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job // to run code in Main(UI) Thread

    // call this method to cancel a coroutine when you don't need it anymore,
    fun cancel() {
        job.cancel()
    }

    fun startTask() = launch {
        val result =
            doSomeBackgroundWork() // runs in background thread without blocking the Main Thread
        doUiStuff(result)
    }

    private suspend fun doSomeBackgroundWork(): String = withContext(Dispatchers.IO) {
        // to run code in Background Thread
        // do async work
        delay(5000) // simulate async work
        return@withContext "SomeResult ${System.currentTimeMillis()}"
    }

    // Runs in Main(UI) Thread
    private fun doUiStuff(result: String) {
        d(logTag, "doUiStuff result: $result")
    }
}
