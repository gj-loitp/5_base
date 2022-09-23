package vn.loitp.app.activity.tutorial.rxjava2

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_asynctask_rx.*
import vn.loitp.app.R
import vn.loitp.app.activity.tutorial.rxjava2.model.Bike

@LogTag("AsyncTaskRxActivity")
@IsFullScreen(false)
class AsyncTaskRxActivity : BaseFontActivity(), View.OnClickListener {
    private var taskTest1: TaskTest1? = null
    private var disposable: Disposable? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_asynctask_rx
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = AsyncTaskRxActivity::class.java.simpleName
        }
        btAsyncTask.setOnClickListener(this)
        btRx1.setOnClickListener(this)
        btRx2.setOnClickListener(this)
        btRx3.setOnClickListener(this)
        btRx4.setOnClickListener(this)
    }

    @Suppress("DEPRECATION")
    override fun onClick(view: View) {
        when (view) {
            btAsyncTask -> {
                taskTest1?.cancel(true)
                taskTest1 = TaskTest1()
                taskTest1?.execute()
            }
            btRx1 -> {
                disposable?.dispose()
                val myRxTask1 = MyRxTask1(textView)
                disposable = myRxTask1.execute()
                disposable?.let {
                    compositeDisposable.add(it)
                }
            }
            btRx2 -> {
                disposable?.dispose()
                val myRxTask2 = MyRxTask2(textView)
                disposable = myRxTask2.execute()
                disposable?.let {
                    compositeDisposable.add(it)
                }
            }
            btRx3 -> {
                val test2 = Test2(6)
                test2.apply()
            }
            btRx4 -> testWithProgress()
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
            textView.text = "onPreExecute"
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
            textView.append("\n${bike.name} - ${bike.model}")
        }

        @Deprecated("Deprecated in Java")
        override fun onCancelled() {
            super.onCancelled()
            textView.append("\nonCancelled")
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(bikeList: List<Bike>) {
            super.onPostExecute(bikeList)
            textView.append("\nPostExecute")
        }
    }

    private inner class Test2(private val count: Int) {
        @SuppressLint("SetTextI18n")
        fun apply() {
            textView.text = "Prev"

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
                        textView.append("\nonSubscribe")
                    }

                    override fun onSuccess(bikes: List<Bike?>) {
                        textView.append("\nonSuccess")
                    }

                    override fun onError(e: Throwable) {
                        textView.append("\nonError: $e")
                    }
                })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun testWithProgress() {
        textView.text = "prev testWithProgress\n"
        compositeDisposable.clear()
        val count = 10
        val testAsyncKotlin = TestAsyncKotlin(count)
        val disProgress = testAsyncKotlin.subscribeProgression { integer: Int ->
            logD("Home -> subscribeProgression $integer")
            textView.append("Home -> subscribeProgression $integer\n")
        }
        val dis = testAsyncKotlin.apply(
            { aBoolean: Boolean ->
                logD("Home -> 1 aBoolean: $aBoolean")
                textView.append("Home -> 1 aBoolean: $aBoolean\n")
            }, { throwable: Throwable ->
                logD("Home -> 2 throwable: $throwable")
                textView.append("Home -> 2 throwable: $throwable\n")
            }, {
                logD("Home -> on disposed")
                textView.append("Home -> on disposed\n")
            }
        ) {
            logD("Home -> 3 finished")
            textView.append("Home -> 3 finished\n")
        }
        compositeDisposable.add(disProgress)
        compositeDisposable.add(dis)
    }
}
