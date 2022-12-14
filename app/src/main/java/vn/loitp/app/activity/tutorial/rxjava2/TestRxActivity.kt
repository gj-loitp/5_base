package vn.loitp.app.activity.tutorial.rxjava2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.google.gson.reflect.TypeToken
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseApplication
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LThreadUtil.Companion.isUIThread
import com.loitpcore.core.utilities.LUIUtil
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_test_rx.*
import vn.loitp.app.R
import vn.loitp.app.activity.tutorial.rxjava2.model.Bike
import java.util.concurrent.TimeUnit

// https://viblo.asia/p/cung-hoc-rxjava-phan-1-gioi-thieu-aRBeXWqgGWE
@LogTag("TestRxActivity")
@IsFullScreen(false)
class TestRxActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_test_rx
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
            this.tvTitle?.text = TestRxActivity::class.java.simpleName
        }
        bt0.setOnClickListener(this)
        bt1.setOnClickListener(this)
        bt2.setOnClickListener(this)
        bt3.setOnClickListener(this)
        bt4.setOnClickListener(this)
        bt5.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view) {
            bt0 -> test0()
            bt1 -> test1()
            bt2 -> test2()
            bt3 -> test3()
            bt4 -> test4()
            bt5 -> test5()
        }
    }

    private fun print(s: String) {
        textView.append("$s -> isUIThread: $isUIThread\n")
    }

    private val bikeList: List<Bike>
        get() {
            val bikeList: MutableList<Bike> = ArrayList()
            for (i in 0..9) {
                val bike = Bike("Name $i", "Model $i")
                bikeList.add(bike)
            }
            return bikeList
        }

    @SuppressLint("SetTextI18n")
    private fun test0() {
        textView.text = "test0\n"
        Observable.fromArray("Suzuki", "Ducati", "BMW", "Honda")
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
                    print("onSubscribe")
                }

                override fun onNext(string: String) {
                    print("onNext $string")
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    print("onComplete")
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun test1() {
        textView.text = "test1\n"
        Observable.just(bikeList).subscribe(object : Observer<List<Bike?>> {
            override fun onSubscribe(d: Disposable) {
                print("onSubscribe")
            }

            override fun onNext(bikes: List<Bike?>) {
                print("onNext " + bikes.size)
            }

            override fun onError(e: Throwable) {}
            override fun onComplete() {
                print("onComplete")
            }
        })
    }

    private var bike: Bike? = null

    @SuppressLint("SetTextI18n")
    private fun test2() {
        textView.text = "test2\n"
        bike = Bike("Suzuki", "GSX R1000")

        // c1
        // Observable<Bike> bikeObservable = Observable.just(bike);

        // c2
        val bikeObservable = Observable.defer { Observable.just<Bike>(bike) }
        bike = Bike("Honda", "CBR1000RR")
        bikeObservable.subscribe(object : Observer<Bike> {
            override fun onSubscribe(d: Disposable) {
                print("onSubscribe")
            }

            override fun onNext(bike: Bike) {
                print("onNext " + bike.name + " - " + bike.model)
            }

            override fun onError(e: Throwable) {}
            override fun onComplete() {
                print("onComplete")
            }
        })
    }

    private var interValDisposable: Disposable? = null

    @SuppressLint("SetTextI18n")
    private fun test3() {
        textView.text = "test3\n"
        interValDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : DisposableObserver<Long?>() {
                    override fun onNext(aLong: Long) {
                        print("onNext $aLong")
                        if (aLong == 5L) {
                            interValDisposable?.dispose()
                        }
                    }

                    override fun onError(e: Throwable) {}
                    override fun onComplete() {
                        print("onComplete")
                    }
                }
            )
    }

    @SuppressLint("SetTextI18n", "CheckResult")
    private fun test4() {
        textView.text = "test4\n"
        Observable.create { emitter: ObservableEmitter<Bike?> ->
            val bikeList = bikeList
            for (bike in bikeList) {
                emitter.onNext(bike)
            }
            emitter.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Bike?>() {
                /*@Override
                public void onSubscribe(Disposable d) {
                    print("onSubscribe");
                }*/
                override fun onNext(bike: Bike) {
                    print("onNext " + bike.name + " - " + bike.model)
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    print("onComplete")
                }
            })
        // compositeDisposable.add(disposable);
    }

    // for test 5
    private fun searchBike(): Observable<String> {
        return Observable.just(BaseApplication.gson.toJson(bikeList))
    }

    private fun parse(json: String): List<Bike> {
        return BaseApplication.gson.fromJson(json, object : TypeToken<List<Bike?>?>() {}.type)
    }

    @SuppressLint("SetTextI18n")
    private fun test5() {
        textView.text = "test5\n"

        // map
        // ok
        /*searchBike("Yamaha").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, List<Bike>>() {
                    @Override
                    public List<Bike> apply(String s) throws Exception {
                        return parse(s);
                    }
                })
                .subscribe(new Observer<List<Bike>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        print("onSubscribe");
                    }

                    @Override
                    public void onNext(List<Bike> bike) {
                        print("onNext " + bike.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        print("onComplete");
                    }
                });*/

        // flat map
        searchBike()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { s: String ->
                Observable.defer { Observable.just(parse(s)) }
            }
            .flatMap { source: List<Bike>? ->
                Observable.fromIterable(source)
            }
            .subscribe(object : Observer<Bike> {
                override fun onSubscribe(d: Disposable) {
                    print("onSubscribe")
                }

                override fun onNext(b: Bike) {
                    print("onNext " + b.name)
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    print("onComplete")
                }
            })
    } // for test 5
}
