package vn.loitp.app.activity.api.retrofit2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import loitp.basemaster.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;

public class TestAPIRetrofit2Activity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private SOService mService;
    private AnswersAdapter mAdapter;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mService = ApiUtils.getSOService();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_answers);
        tv = (TextView) findViewById(R.id.tv);
        mAdapter = new AnswersAdapter(this, new ArrayList<Item>(0), new AnswersAdapter.PostItemListener() {
            @Override
            public void onPostClick(long id) {
                Toast.makeText(activity, "Post id is" + id, Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        LUIUtil.setPullLikeIOSVertical(mRecyclerView);

        loadAnswers();
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_test_api_retrofit2;
    }

    private void loadAnswers() {
        mService.getAnswers().enqueue(new Callback<SOAnswersResponse>() {
            @Override
            public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {
                if (response.isSuccessful()) {
                    mAdapter.updateAnswers(response.body().getItems());
                    LLog.d(TAG, "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
                tv.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SOAnswersResponse> call, Throwable t) {
                if (t == null) {
                    return;
                }
                LLog.d(TAG, "error loading from API");
                tv.setText(t.getMessage());
            }
        });
    }
}
