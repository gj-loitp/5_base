package vn.loitp.up.a.tut.retrofit2

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseApplication
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.restApi.restClient.RestClient2
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import vn.loitp.R
import vn.loitp.databinding.ARetrofit2Binding

// https://code.tutsplus.com/tutorials/connect-to-an-api-with-retrofit-rxjava-2-and-kotlin--cms-32133

@LogTag("Retrofit2Activity")
@IsFullScreen(false)
class Retrofit2Activity : BaseActivityFont(), Retrofit2Adapter.Listener {
    private lateinit var binding: ARetrofit2Binding
    private var retrofit2Adapter: Retrofit2Adapter? = null
    private var retroCryptoArrayList = ArrayList<RetroCrypto>()
    private val baseURL = "https://api.nomics.com/v1/"
    private lateinit var sampleService: SampleService

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARetrofit2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        RestClient2.init(baseURL)
        sampleService = RestClient2.createService(SampleService::class.java)
        setupViews()
        loadData()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = Retrofit2Activity::class.java.simpleName
        }
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rv.layoutManager = layoutManager
    }

    private fun loadData() {
        logD("loadData")
        binding.pb.visibility = View.VISIBLE
        compositeDisposable.add(
            sampleService.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    logD("loadData success " + BaseApplication.gson.toJson(it))
                    retroCryptoArrayList.clear()
                    retroCryptoArrayList.addAll(it)
                    retrofit2Adapter =
                        Retrofit2Adapter(cryptoList = retroCryptoArrayList, listener = this)
                    binding.rv.adapter = retrofit2Adapter
                    binding.pb.isVisible = false
                }, {
                    logE("loadData error $it")
                    showShortError(it.toString())
                    binding.pb.isVisible = false
                })
        )
    }

    override fun onItemClick(retroCrypto: RetroCrypto) {
        showShortInformation("You clicked: ${retroCrypto.currency}")
    }
}
