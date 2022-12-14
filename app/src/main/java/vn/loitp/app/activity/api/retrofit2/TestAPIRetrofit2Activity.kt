package vn.loitp.app.activity.api.retrofit2

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_test_api_retrofit2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.loitp.app.R
import vn.loitp.app.activity.api.retrofit2.AnswersAdapter.PostItemListener
import vn.loitp.app.activity.api.retrofit2.ApiUtils.Companion.sOService
import vn.loitp.app.activity.api.retrofit2.model.SOAnswersResponse

@LogTag("TestAPIRetrofit2Activity")
@IsFullScreen(false)
class TestAPIRetrofit2Activity : BaseFontActivity() {
    private var mService: SOService? = null
    private var mAdapter: AnswersAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_test_api_retrofit2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        mService = sOService

        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
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

        rvAnswers.apply {
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
                textView.visibility = View.GONE
            }

            override fun onFailure(call: Call<SOAnswersResponse>, t: Throwable) {
                textView.text = t.message
            }
        })
    }
}
