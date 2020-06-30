package vn.loitp.app.activity.api.retrofit2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.loitp.app.R;

public class TestAPIRetrofit2Activity extends BaseFontActivity {
    private RecyclerView mRecyclerView;
    private SOService mService;
    private AnswersAdapter mAdapter;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mService = ApiUtils.getSOService();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_answers);
        tv = (TextView) findViewById(R.id.textView);
        mAdapter = new AnswersAdapter(this, new ArrayList<Item>(0), id -> Toast.makeText(getActivity(), "Post id is" + id, Toast.LENGTH_SHORT).show());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        LUIUtil.Companion.setPullLikeIOSVertical(mRecyclerView);

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
                    LLog.d(getTAG(), "posts loaded from API");
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
                LLog.d(getTAG(), "error loading from API");
                tv.setText(t.getMessage());
            }
        });
    }
}
