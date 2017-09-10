package vn.puresolutions.livestarv3.activity.schedule.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.activity.homescreen.fragment.ranking.DummyPerson;
import vn.puresolutions.livestarv3.activity.schedule.calendar.ScheduleAdapter;
import vn.puresolutions.livestarv3.base.BaseFragment;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmSearchInSchedule extends BaseFragment {
    private TextView tvNumberResult;
    private TextView tvChooseMenuTime;
    private ArrayList<DummyPerson> dummyPersonArrayList = new ArrayList<DummyPerson>();
    private ScheduleAdapter scheduleAdapter;
    private ImageView btChooseMenuTime;
    private LinearLayout viewGroupChooseTime;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createDummyData();
        View view = inflater.inflate(R.layout.frm_search_in_schedule, container, false);
        findViews(view);
        return view;

    }

    private void findViews(View view) {
        viewGroupChooseTime = (LinearLayout) view.findViewById(R.id.view_group_choose_time);
        tvNumberResult = (TextView) view.findViewById(R.id.tv_number_result);
        tvChooseMenuTime = (TextView) view.findViewById(R.id.tv_choose_menu_time);
        btChooseMenuTime = (ImageView) view.findViewById(R.id.bt_choose_menu_time);
        tvNumberResult.setText("69 kết quả");
        tvChooseMenuTime.setText("Trong ngày");
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        scheduleAdapter = new ScheduleAdapter(getActivity(), dummyPersonArrayList, new ScheduleAdapter.Callback() {
            @Override
            public void onClickStateFollow(DummyPerson dummyPerson) {
                AlertMessage.showSuccess(getActivity(), "onClickStateFollow " + dummyPerson.getName());
            }

            @Override
            public void onClickEvent(DummyPerson dummyPerson) {
                AlertMessage.showSuccess(getActivity(), "onClickEvent " + dummyPerson.getName());
            }
        });
        recyclerView.setAdapter(scheduleAdapter);
        viewGroupChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupChooseTime();
            }
        });
    }

    private void createDummyData() {
        for (int i = 0; i < 999; i++) {
            DummyPerson dummyPerson = new DummyPerson();
            dummyPerson.setName("Idol loitp " + i);
            dummyPerson.setHeart(i);
            dummyPerson.setImage("https://scontent.fsgn2-3.fna.fbcdn.net/v/t1.0-9/10437784_322413067955246_5048646645777216242_n.jpg?oh=8fb07025cda1b37e19a6dc3c4457740b&oe=59F9FA7E");
            dummyPersonArrayList.add(dummyPerson);
        }
    }

    private void showPopupChooseTime() {
        PopupMenu popup = new PopupMenu(getActivity(), btChooseMenuTime);
        popup.getMenuInflater().inflate(R.menu.search_in_schedule_choose_time, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                AlertMessage.showSuccess(getActivity(), String.valueOf(item.getTitle()));
                tvChooseMenuTime.setText(String.valueOf(item.getTitle()));
                switch (item.getItemId()) {
                    default:
                        break;
                }
                return true;
            }
        });

        popup.show();
    }
}

