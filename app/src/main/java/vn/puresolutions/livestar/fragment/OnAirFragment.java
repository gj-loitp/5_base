package vn.puresolutions.livestar.fragment;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.OnAirAdapter;
import vn.puresolutions.livestar.core.api.model.Room;
import vn.puresolutions.livestar.core.api.model.Rooms;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.RoomService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestar.decoration.OnAirItemDecoration;
import vn.puresolutions.livestarv3.base.BaseFragment;

/**
 * @author hoangphu
 * @since 7/31/16
 */
public class OnAirFragment extends BaseFragment {

    public interface OnAirLoadMoreListener {
        void onLoadingMore();
    }

    @BindView(R.id.onAirFragment_rclIdols)
    RecyclerView rclIdols;
    @BindView(R.id.onAirFragment_prgLoading)
    ProgressBar prgLoading;
    @BindView(R.id.onAirFragment_imgBackground)
    SimpleDraweeView imgBackground;
    @BindView(R.id.onAirFragment_tvNoData)
    TextView tvNoData;
    @BindView(R.id.onAirFragment_rltContainer)
    View rltContainer;
    @BindView(R.id.onAirFragment_srlRefreshLayout)
    SwipeRefreshLayout refreshLayout;

    private int page = 0;
    private OnAirAdapter roomsAdapter;
    private PipelineDraweeControllerBuilder controller;
    private int currentPosition = -1;
    List<Room> lstOnAirRoom = new ArrayList<>();
    private ControllerListener controllerListener = new BaseControllerListener() {
        @Override
        public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
            Animation fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
            imgBackground.startAnimation(fadeInAnimation);
        }
    };
    private OnAirLoadMoreListener loadMoreListener = this::loadData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_on_air, container, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        // init view
        controller = Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener);
        imgBackground.setController(controller.build());

        LinearLayoutManager layout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rclIdols.addItemDecoration(new OnAirItemDecoration(context, R.dimen.on_air_recycler_item_padding));
        rclIdols.setLayoutManager(layout);
        rclIdols.setHasFixedSize(true);
        rclIdols.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    loadBackgroundImage();
                }
            }
        });

        refreshLayout.setOnRefreshListener(() -> {
            // clear old data
            roomsAdapter.clearNtf();
            lstOnAirRoom.clear();
            // reset page index
            page = 0;
            loadData();
        });

        // load data
        rltContainer.post(() -> {
            roomsAdapter = new OnAirAdapter();
            roomsAdapter.setListener(loadMoreListener);
            roomsAdapter.setBaseWidth(rltContainer.getWidth());
            rclIdols.setAdapter(roomsAdapter);

            loadData();
        });
    }

    private void loadData() {
        // disable pull to refresh
        refreshLayout.setEnabled(false);
        RoomService service = RestClient.createService(RoomService.class);
        subscribe(service.getOnAir(page), new ApiSubscriber<Rooms>() {
            @Override
            public void onSuccess(Rooms result) {
                if (result.getTotalPage() == 0) {
                    tvNoData.setVisibility(View.VISIBLE);
                    roomsAdapter.reachToEnd();
                } else {
                    //roomsAdapter.addAll(result.getRooms());
                    //List<Room> room = new ArrayList<>();
                    //room=getOnAirRoom(result.getRooms());
                    getOnAirRoom(result.getRooms());
                    if (lstOnAirRoom.size()==0) {
                        tvNoData.setVisibility(View.VISIBLE);
                        return;
                    }
                        roomsAdapter.clear();
                        roomsAdapter.addAll(lstOnAirRoom);
                    if (page == result.getTotalPage() - 1) {
                        roomsAdapter.reachToEnd();
                    } else {
                        page++;
                        roomsAdapter.finishLoadingMore();
                    }
                }

                if (page == 1) {
                    // first page. Selected item zero background as default
                    loadBackgroundImage(0);
                } else {
                    loadBackgroundImage();
                }
                tvNoData.setVisibility(View.GONE);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }

            @Override
            public void onFinally(boolean success) {
                refreshLayout.setEnabled(true);
                refreshLayout.setRefreshing(false);

                prgLoading.setVisibility(View.GONE);
            }
        });
    }

    private void loadBackgroundImage() {
        LinearLayoutManager layoutManager = (LinearLayoutManager)rclIdols.getLayoutManager();
        loadBackgroundImage(layoutManager.findLastCompletelyVisibleItemPosition());
    }

    private void loadBackgroundImage(int position) {
        if (!roomsAdapter.isLastPosition(position) && position != currentPosition &&
                position != RecyclerView.NO_POSITION) {
            this.currentPosition = position;
            controller.setUri(Uri.parse(roomsAdapter.getItem(currentPosition).getPoster()));
            imgBackground.setController(controller.build());
        }
    }
    private void getOnAirRoom(List<Room> lstRoom ){
        for (int i=0;i<lstRoom.size();i++){
            if (lstRoom.get(i).isOnAir()==true){
                try{
                        lstOnAirRoom.add(lstRoom.get(i));
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                    lstOnAirRoom.add(lstRoom.get(i));
                }

            }
        }
        for (int i = 0;i<lstOnAirRoom.size();i++){
            if (lstOnAirRoom.size()>1){
                for (int j=i+1;j<lstOnAirRoom.size();j++){
                    if (lstOnAirRoom.get(i).getId()==lstOnAirRoom.get(j).getId()){
                        lstOnAirRoom.remove(j);
                    }
                }
            }
        }
      /*  for (Room room:lstRoom){
            if (room.isOnAir()){
                Log.i("OnAirFragment","Room");
                lstOnAirRoom.add(room);
            }
        }*/
        //return lstOnAirRoom;
    }
}
