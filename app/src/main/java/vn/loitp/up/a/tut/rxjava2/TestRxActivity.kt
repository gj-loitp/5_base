package vn.loitp.up.a.tut.rxjava2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.google.gson.reflect.TypeToken
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseApplication
import com.loitp.core.ext.isUIThread
import com.loitp.core.ext.setSafeOnClickListenerElastic
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import vn.loitp.R
import vn.loitp.databinding.ARxTestBinding
import vn.loitp.up.a.tut.rxjava2.md.Bike
import java.util.concurrent.TimeUnit

// https://viblo.asia/p/cung-hoc-rxjava-phan-1-gioi-thieu-aRBeXWqgGWE
@LogTag("TestRxActivity")
@IsFullScreen(false)
class TestRxActivity : BaseActivityFont(), View.OnClickListener {

    private lateinit var binding: ARxTestBinding

    override fun setLayoutResourceId(): Int {
        return R.layout.a_rx_test
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARxTestBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = TestRxActivity::class.java.simpleName
        }
        binding.bt0.setOnClickListener(this)
        binding.bt1.setOnClickListener(this)
        binding.bt2.setOnClickListener(this)
        binding.bt3.setOnClickListener(this)
        binding.bt4.setOnClickListener(this)
        binding.bt5.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view) {
            binding.bt0 -> test0()
            binding.bt1 -> test1()
            binding.bt2 -> test2()
            binding.bt3 -> test3()
            binding.bt4 -> test4()
            binding.bt5 -> test5()
        }
    }

    private fun print(s: String) {
        binding.textView.append("$s -> isUIThread: $isUIThread\n")
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
        binding.textView.text = "test0\n"
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
        binding.textView.text = "test1\n"
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
        binding.textView.text = "test2\n"
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
        binding.textView.text = "test3\n"
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
        binding.textView.text = "test4\n"
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
        binding.textView.text = "test5\n"

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
