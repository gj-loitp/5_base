package vn.loitp.app.activity.tutorial.retrofit2

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_retrofit_2.*
import loitp.basemaster.R
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.restapi.restclient.RestClient2
import vn.loitp.views.LToast
import java.util.*

//https://code.tutsplus.com/tutorials/connect-to-an-api-with-retrofit-rxjava-2-and-kotlin--cms-32133
class Retrofit2Activity : BaseFontActivity(), Retrofit2Adapter.Listener {
    private var retrofit2Adapter: Retrofit2Adapter? = null
    private var retroCryptoArrayList: ArrayList<RetroCrypto>? = null
    private val BASE_URL = "https://api.nomics.com/v1/"
    private var sampleService: SampleService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RestClient2.init(BASE_URL)
        sampleService = RestClient2.createService(SampleService::class.java)
        initRecyclerView()
        loadData()
    }

    private fun initRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        cryptocurrency_list.layoutManager = layoutManager
    }

    private fun loadData() {
        compositeDisposable?.add(sampleService!!.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(Consumer {
                    retroCryptoArrayList = ArrayList(it)
                    retrofit2Adapter = Retrofit2Adapter(retroCryptoArrayList!!, this)
                    cryptocurrency_list.adapter = retrofit2Adapter
                }))

        /*subscribe(sampleService!!.getData(), Consumer {
            retroCryptoArrayList = ArrayList(it)
            retrofit2Adapter = Retrofit2Adapter(retroCryptoArrayList!!, this)
            cryptocurrency_list.adapter = retrofit2Adapter
        })*/
    }

    override fun onItemClick(retroCrypto: RetroCrypto) {
        LToast.show(activity, "You clicked: ${retroCrypto.currency}")
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_retrofit_2
    }

}
