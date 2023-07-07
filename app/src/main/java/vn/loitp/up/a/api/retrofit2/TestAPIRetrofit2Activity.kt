package vn.loitp.up.a.api.retrofit2

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.loitp.databinding.ATestApiRetrofit2Binding
import vn.loitp.up.a.api.retrofit2.AnswersAdapter.PostItemListener
import vn.loitp.up.a.api.retrofit2.ApiUtils.Companion.sOService
import vn.loitp.up.a.api.retrofit2.md.SOAnswersResponse

@LogTag("TestAPIRetrofit2Activity")
@IsFullScreen(false)
class TestAPIRetrofit2Activity : BaseActivityFont() {
    private var mService: SOService? = null
    private var mAdapter: AnswersAdapter? = null
    private lateinit var binding: ATestApiRetrofit2Binding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATestApiRetrofit2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        mService = sOService

        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = TestAPIRetrofit2Activity::class.java.simpleName
        }
        mAdapter = AnswersAdapter(
            mItems = ArrayList(0),
            mItemListener = object : PostItemListener {
                override fun onPostClick(id: Long) {
                    showShortInformation("Post id is$id")
                }
            }
        )

        binding.rvAnswers.apply {
            this.layoutManager = LinearLayoutManager(this@TestAPIRetrofit2Activity)
            this.adapter = mAdapter
            this.setHasFixedSize(true)
            val itemDecoration: ItemDecoration =
                DividerItemDecoration(this@TestAPIRetrofit2Activity, DividerItemDecoration.VERTICAL)
            this.addItemDecoration(itemDecoration)
            // setPullLikeIOSVertical(this)
        }

        loadAnswers()
    }

    private fun loadAnswers() {
        mService?.answers?.enqueue(object : Callback<SOAnswersResponse> {
            override fun onResponse(
                call: Call<SOAnswersResponse>,
                response: Response<SOAnswersResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.items?.let {
                        mAdapter?.updateAnswers(it)
                    }
                } else {
                    val statusCode = response.code()
                    print(statusCode)
                    // handle request errors depending on status code
                }
                binding.textView.visibility = View.GONE
            }

            override fun onFailure(call: Call<SOAnswersResponse>, t: Throwable) {
                binding.textView.text = t.message
            }
        })
    }
}
