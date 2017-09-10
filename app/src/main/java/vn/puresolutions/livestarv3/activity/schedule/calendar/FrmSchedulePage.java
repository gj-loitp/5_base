package vn.puresolutions.livestarv3.activity.schedule.calendar;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.activity.homescreen.fragment.ranking.DummyPerson;
import vn.puresolutions.livestarv3.base.BaseFragment;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmSchedulePage extends BaseFragment {
    private ArrayList<DummyPerson> dummyPersonArrayList = new ArrayList<DummyPerson>();
    private ScheduleAdapter scheduleAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDummyData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_schedule_page, container, false);
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
        return view;
    }

    private void createDummyData() {
        for (int i = 0; i < 50; i++) {
            DummyPerson dummyPerson = new DummyPerson();
            dummyPerson.setName("Idol loitp " + i);
            dummyPerson.setHeart(i);
            dummyPerson.setImage("https://scontent.fsgn2-3.fna.fbcdn.net/v/t1.0-9/10437784_322413067955246_5048646645777216242_n.jpg?oh=8fb07025cda1b37e19a6dc3c4457740b&oe=59F9FA7E");
            dummyPersonArrayList.add(dummyPerson);
        }
    }
}

