package vn.loitp.up.a.demo.architectureComponent.coroutine

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.getRandomNumber
import com.loitp.core.ext.setDelay
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.coroutines.*
import vn.loitp.R
import vn.loitp.databinding.ADemoCoroutineBinding
import kotlin.system.measureTimeMillis

// https://viblo.asia/p/cung-hoc-kotlin-coroutine-phan-1-gioi-thieu-kotlin-coroutine-va-ky-thuat-lap-trinh-bat-dong-bo-gGJ59xajlX2
// https://viblo.asia/p/cung-hoc-kotlin-coroutine-phan-2-build-first-coroutine-with-kotlin-Do7544Ee5M6
// https://viblo.asia/p/cung-hoc-kotlin-coroutine-phan-3-coroutine-context-va-dispatcher-Qbq5QkDzZD8
// https://viblo.asia/p/cung-hoc-kotlin-coroutine-phan-4-job-join-cancellation-and-timeouts-Do75463QZM6
// https://viblo.asia/p/cung-hoc-kotlin-coroutine-phan-5-async-await-naQZRxGm5vx
// https://viblo.asia/p/cung-hoc-kotlin-coroutine-phan-6-coroutine-scope-aWj536n8l6m
// https://medium.com/androiddevelopers/coroutines-on-android-part-ii-getting-started-3bff117176dd
@LogTag("CoroutineActivity")
@IsFullScreen(false)
class CoroutineActivity : BaseActivityFont() {

    private lateinit var binding: ADemoCoroutineBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADemoCoroutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = CoroutineActivity::class.java.simpleName
        }
        binding.btTestBlocking.setSafeOnClickListener {
            testBlocking()
        }
        binding.btTestWithContext.setSafeOnClickListener {
            testWithContext()
        }
        binding.btTestJoin.setSafeOnClickListener {
            testJoin()
        }
        binding.btTestCancel.setSafeOnClickListener {
            testCancel()
        }
        binding.btTestCompose.setSafeOnClickListener {
            testCompose()
        }
        binding.btTestTimeOut.setSafeOnClickListener {
            testTimeOut()
        }
        binding.btTestConvertAsyncTaskToCoroutine.setSafeOnClickListener {
            convertAsyncTaskToCoroutine()
        }
        binding.btTestAwaitAll.setSafeOnClickListener {
            testAwaitAll()
        }
    }

    // test blocking
    private fun testBlocking() = runBlocking {
        repeat(100_000) {
            // launch 100_000 coroutines
            launch {
                logD("hello " + System.currentTimeMillis())
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun testWithContext() {
        newSingleThreadContext("thread1").use { ctx1 ->
            // tạo một context là ctx1 chứ chưa launch coroutine.
            // ctx1 sẽ có 1 element là dispatcher quyết định coroutine sẽ chạy trên 1 thread tên là thread1
            logD("ctx1 - ${Thread.currentThread().name}")

            newSingleThreadContext("thread2").use { ctx2 ->
                // tạo một context là ctx2 chứ vẫn chưa launch coroutine
                // ctx2 sẽ có 1 element là dispatcher quyết định coroutine sẽ chạy trên 1 thread tên là thread2
                logD("ctx2 - ${Thread.currentThread().name}")

                // bắt đầu chạy coroutine với context là ctx1
                runBlocking(ctx1) {
                    // coroutine đang chạy trên context ctx1 và trên thread thread1
                    logD("Started in ctx1 - ${Thread.currentThread().name}")

                    // sử dụng hàm withContext để chuyển đổi context từ ctx1 qua ctx2
                    withContext(ctx2) {
                        // coroutine đang chạy với context ctx2 và trên thread thread2
                        logD("Working in ctx2 - ${Thread.currentThread().name}")
                    }

                    // coroutine đã thoát ra block withContext nên sẽ chạy lại với context ctx1 và trên thread thread1
                    logD("Back to ctx1 - ${Thread.currentThread().name}")
                }
            }
            logD("out of ctx2 block - ${Thread.currentThread().name}")
        }
        logD("out of ctx1 block - ${Thread.currentThread().name}")
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun testJoin() {
        runBlocking {
            val job = GlobalScope.launch {
                // launch a new coroutine and keep a reference to its Job
                delay(5000L)
                logD("World!")
            }
            logD("Hello,")
            job.join() // wait until child coroutine completes
            logD("Kotlin")
        }
    }

    private fun testCancel() {
        runBlocking {
            val job = launch {
                try {
                    repeat(1000) { i ->
                        logD("I'm sleeping $i")
                        delay(500L)
                    }
                } finally {
                    // Tranh thủ close resource trong này đi nha :D
                    logD("I'm running finally")
                }
            }
            delay(1300L) // delay a bit
            logD("I'm tired of waiting!")
            job.cancel() // cancels the job
            logD("Now I can quit.")
        }
    }

    private fun testCompose() {
        suspend fun getOne(): Int {
            delay(1000)
            return 10
        }

        suspend fun getTwo(): Int {
            delay(1000)
            return 20
        }
        runBlocking {
            val time = measureTimeMillis {
                val one = async { getOne() }
                val two = async { getTwo() }
                logD("total: ${one.await() + two.await()}")
            }
            logD("testCompose time: $time ms")
        }
    }

    private fun testTimeOut() {
        runBlocking {
            val result = withTimeoutOrNull(timeMillis = 10000) {
                repeat(1000) { i ->
                    logD("testTimeOut $i: " + System.currentTimeMillis())
                    delay(500)
                }
            }
            logD("testTimeOut result: $result")
        }
    }

    private fun convertAsyncTaskToCoroutine() {
        val coroutineTask = CoroutineTask()
        coroutineTask.startTask()
        setDelay(mls = 2000, runnable = Runnable {
            // coroutineTask.cancel()
        })
    }

    private fun testAwaitAll() {
        val timeStart = System.currentTimeMillis()
        logD("timeStart $timeStart")

        val list = ArrayList<String>()
        for (i in 0..5) {
            list.add(System.currentTimeMillis().toString())
        }

        val scope = CoroutineScope(Dispatchers.Main.immediate)
        var countSuccess = 0
        val sizeExpect = list.size
        scope.launch {

            fun doLongTask(index: Int) {
                launch {
                    logD("======doLongTask index $index")
                    val timeStartItem = System.currentTimeMillis()

                    //min 1_000, max 10_000
//                    val delayInMls = getRandomNumber(9_000) + 1_000
//                    delay(delayInMls.toLong())

                    fakeDoLongTask(
                        scope = scope,
                        index = index,
                        callBack = {
                            val timeEndItem = System.currentTimeMillis()
                            logD("index $index take ${timeEndItem - timeStartItem}")

                            countSuccess++
                            if (countSuccess == sizeExpect) {
                                logD(">>>FINISH")
                                val timeEnd = System.currentTimeMillis()
                                logD("timeEnd $timeEnd -> bench ${timeEnd - timeStart}")
//                                scope.cancel()
                            }
                        })
                }
            }

            list.forEachIndexed { index, _ ->
                doLongTask(index)
            }
        }
    }

    private suspend fun fakeDoLongTask(
        scope: CoroutineScope,
        index: Int,
        callBack: ((String) -> Unit)
    ) {
        logD("$index fakeDoLongTask======")
        scope.launch {
            logD("$index fakeDoLongTask")

            val delayInMls = getRandomNumber(9_000) + 1_000
            delay(delayInMls.toLong())
            callBack.invoke(System.currentTimeMillis().toString())

//            logD("$index fakeDoLongTask cancel")
//            scope.cancel()
        }
    }
}
