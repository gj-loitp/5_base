package vn.loitp.up.a.tut.rxjava2

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import vn.loitp.R
import vn.loitp.databinding.ARxAsyncTaskBinding
import vn.loitp.up.a.tut.rxjava2.md.Bike

@LogTag("AsyncTaskRxActivity")
@IsFullScreen(false)
class AsyncTaskRxActivity : BaseActivityFont(), View.OnClickListener {
    private lateinit var binding: ARxAsyncTaskBinding

    private var taskTest1: TaskTest1? = null
    private var disposable: Disposable? = null

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARxAsyncTaskBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = AsyncTaskRxActivity::class.java.simpleName
        }
        binding.btAsyncTask.setOnClickListener(this)
        binding.btRx1.setOnClickListener(this)
        binding.btRx2.setOnClickListener(this)
        binding.btRx3.setOnClickListener(this)
        binding.btRx4.setOnClickListener(this)
    }

    @Suppress("DEPRECATION")
    override fun onClick(view: View) {
        when (view) {
            binding.btAsyncTask -> {
                taskTest1?.cancel(true)
                taskTest1 = TaskTest1()
                taskTest1?.execute()
            }
            binding.btRx1 -> {
                disposable?.dispose()
                val myRxTask1 = MyRxTask1(binding.textView)
                disposable = myRxTask1.execute()
                disposable?.let {
                    compositeDisposable.add(it)
                }
            }
            binding.btRx2 -> {
                disposable?.dispose()
                val myRxTask2 = MyRxTask2(binding.textView)
                disposable = myRxTask2.execute()
                disposable?.let {
                    compositeDisposable.add(it)
                }
            }
            binding.btRx3 -> {
                val test2 = Test2(6)
                test2.apply()
            }
            binding.btRx4 -> testWithProgress()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        taskTest1?.cancel(true)
        compositeDisposable.clear()
    }

    @Suppress("DEPRECATION")
    @SuppressLint("StaticFieldLeak")
    private inner class TaskTest1 : AsyncTask<Void, Bike, List<Bike>>() {
        @Deprecated("Deprecated in Java")
        @SuppressLint("SetTextI18n")
        override fun onPreExecute() {
            super.onPreExecute()
            binding.textView.text = "onPreExecute"
        }

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg voids: Void): List<Bike> {
            val bikeList: MutableList<Bike> = ArrayList()
            for (i in 0..9) {
                try {
                    Thread.sleep(1000)
                    val bike = Bike()
                    bike.model = "Model $i"
                    bike.name = "Name $i"
                    bikeList.add(bike)
                    publishProgress(bike)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                    return emptyList()
                }
            }
            return bikeList
        }

        @Deprecated("Deprecated in Java")
        override fun onProgressUpdate(vararg values: Bike) {
            super.onProgressUpdate(*values)
            val bike = values[0]
            binding.textView.append("\n${bike.name} - ${bike.model}")
        }

        @Deprecated("Deprecated in Java")
        override fun onCancelled() {
            super.onCancelled()
            binding.textView.append("\nonCancelled")
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(bikeList: List<Bike>) {
            super.onPostExecute(bikeList)
            binding.textView.append("\nPostExecute")
        }
    }

    private inner class Test2(private val count: Int) {
        @SuppressLint("SetTextI18n")
        fun apply() {
            binding.textView.text = "Prev"

            Single.fromCallable<List<Bike>> {
                val bikeList: MutableList<Bike> = ArrayList()
                for (i in 0 until count) {
                    Thread.sleep(1000)
                    val bike = Bike()
                    bike.name = "Name $i"
                    bike.model = "Model $i"
                    bikeList.add(bike)
                }
                bikeList
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<List<Bike?>?> {
                    override fun onSubscribe(d: Disposable) {
                        binding.textView.append("\nonSubscribe")
                    }

                    override fun onSuccess(bikes: List<Bike?>) {
                        binding.textView.append("\nonSuccess")
                    }

                    override fun onError(e: Throwable) {
                        binding.textView.append("\nonError: $e")
                    }
                })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun testWithProgress() {
        binding.textView.text = "prev testWithProgress\n"
        compositeDisposable.clear()
        val count = 10
        val testAsyncKotlin = TestAsyncKotlin(count)
        val disProgress = testAsyncKotlin.subscribeProgression { integer: Int ->
            logD("Home -> subscribeProgression $integer")
            binding.textView.append("Home -> subscribeProgression $integer\n")
        }
        val dis = testAsyncKotlin.apply(
            { aBoolean: Boolean ->
                logD("Home -> 1 aBoolean: $aBoolean")
                binding.textView.append("Home -> 1 aBoolean: $aBoolean\n")
            }, { throwable: Throwable ->
                logD("Home -> 2 throwable: $throwable")
                binding.textView.append("Home -> 2 throwable: $throwable\n")
            }, {
                logD("Home -> on disposed")
                binding.textView.append("Home -> on disposed\n")
            }
        ) {
            logD("Home -> 3 finished")
            binding.textView.append("Home -> 3 finished\n")
        }
        compositeDisposable.add(disProgress)
        compositeDisposable.add(dis)
    }
}
