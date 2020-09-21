package vn.loitp.app.activity.tutorial.retrofit2

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.restapi.restclient.RestClient2
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_retrofit_2.*
import vn.loitp.app.R
import vn.loitp.app.app.LApplication

//https://code.tutsplus.com/tutorials/connect-to-an-api-with-retrofit-rxjava-2-and-kotlin--cms-32133

@LayoutId(R.layout.activity_retrofit_2)
@LogTag("Retrofit2Activity")
@IsFullScreen(false)
class Retrofit2Activity : BaseFontActivity(), Retrofit2Adapter.Listener {
    private var retrofit2Adapter: Retrofit2Adapter? = null
    private var retroCryptoArrayList = ArrayList<RetroCrypto>()
    private val BASE_URL = "https://api.nomics.com/v1/"
    private lateinit var sampleService: SampleService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RestClient2.init(BASE_URL)
        sampleService = RestClient2.createService(SampleService::class.java)
        initRecyclerView()
        loadData()
    }

    private fun initRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
    }

    private fun loadData() {
        logD("loadData")
        pb.visibility = View.VISIBLE
        compositeDisposable.add(sampleService.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    logD("loadData success " + LApplication.gson.toJson(it))
                    retroCryptoArrayList.clear()
                    retroCryptoArrayList.addAll(it)
                    retrofit2Adapter = Retrofit2Adapter(cryptoList = retroCryptoArrayList, listener = this)
                    rv.adapter = retrofit2Adapter
                    pb.visibility = View.GONE
                }, {
                    logE("loadData error $it")
                    showShort(it.toString())
                    pb.visibility = View.GONE
                }))
    }

    override fun onItemClick(retroCrypto: RetroCrypto) {
        showShort("You clicked: ${retroCrypto.currency}")
    }

}
