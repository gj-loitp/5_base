package vn.loitp.app.activity.api.retrofit2

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_retrofit2_test_api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.loitp.app.R
import vn.loitp.app.activity.api.retrofit2.AnswersAdapter.PostItemListener
import vn.loitp.app.activity.api.retrofit2.ApiUtils.Companion.sOService
import java.util.*

@LayoutId(R.layout.activity_retrofit2_test_api)
@LogTag("TestAPIRetrofit2Activity")
@IsFullScreen(false)
class TestAPIRetrofit2Activity : BaseFontActivity() {
    private var mService: SOService? = null
    private var mAdapter: AnswersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mService = sOService

        mAdapter = AnswersAdapter(mItems = ArrayList(0), mItemListener = object : PostItemListener {
            override fun onPostClick(id: Long) {
                showShortInformation("Post id is$id")
            }
        })

        rvAnswers.apply {
            this.layoutManager = LinearLayoutManager(this@TestAPIRetrofit2Activity)
            this.adapter = mAdapter
            this.setHasFixedSize(true)
            val itemDecoration: ItemDecoration = DividerItemDecoration(this@TestAPIRetrofit2Activity, DividerItemDecoration.VERTICAL)
            this.addItemDecoration(itemDecoration)
            //setPullLikeIOSVertical(this)
        }

        loadAnswers()
    }

    private fun loadAnswers() {
        mService?.answers?.enqueue(object : Callback<SOAnswersResponse> {
            override fun onResponse(call: Call<SOAnswersResponse>, response: Response<SOAnswersResponse>) {
                if (response.isSuccessful) {
                    response.body()?.items?.let {
                        mAdapter?.updateAnswers(it)
                    }
                } else {
                    val statusCode = response.code()
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
