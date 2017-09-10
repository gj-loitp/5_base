package vn.puresolutions.livestarv3.activity.room;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LPref;

/**
 * Created by www.muathu@gmail.com on 8/1/2017.
 */

public class FrmRoomV3Base extends BaseFragmentForLOnlyViewPager {
    private final String TAG = getClass().getSimpleName();
    private Room mRoom;

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
        View view = inflater.inflate(R.layout.frm_room_v3_base, container, false);
        Bundle bundle = getArguments();
        if (bundle == null) {
            AlertMessage.showError(getActivity(), R.string.err);
            return view;
        }
        mRoom = (Room) bundle.getSerializable(Constants.KEY_ROOM);
        if (mRoom == null) {
            AlertMessage.showError(getActivity(), R.string.err);
            return view;
        }

        //LLog.d(TAG, ">>>>>>mode: " + mRoom.getMode().equals(Constants.MODE_PUBLIC));
        if (mRoom.getMode().equals(Constants.MODE_PUBLIC)) {
            switchToRoomPublic();
        } else {
            boolean isBoughtSessionId = LPref.isBoughtSessionId(getActivity(), mRoom.getSession().getId());
            LLog.d(TAG, ">>>> isBoughtSessionId " + isBoughtSessionId);
            if (isBoughtSessionId) {
                //LLog.d(TAG, "isBoughtTicket true switchToRoomPublic");
                switchToRoomPublic();
            } else {
                //LLog.d(TAG, "isBoughtTicket false switchToRoomPrivate");
                switchToRoomPrivate();
            }
        }
        return view;
    }

    private void switchToRoomPublic() {
        FragmentManager fragmentManager = getFragmentManager();
        FrmRoomV3Public frmRoomV3Public = (FrmRoomV3Public) fragmentManager.findFragmentByTag(mRoom.getId());
        if (frmRoomV3Public == null) {
            frmRoomV3Public = new FrmRoomV3Public();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.KEY_ROOM, mRoom);
            frmRoomV3Public.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, frmRoomV3Public, mRoom.getId())
                    //.addToBackStack(null);
                    .commit();
        }
    }

    private void switchToRoomPrivate() {
        FragmentManager fragmentManager = getFragmentManager();
        FrmRoomV3Private frmRoomV3Private = (FrmRoomV3Private) fragmentManager.findFragmentByTag(mRoom.getId());
        if (frmRoomV3Private == null) {
            frmRoomV3Private = new FrmRoomV3Private();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.KEY_ROOM, mRoom);
            frmRoomV3Private.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, frmRoomV3Private, mRoom.getId())
                    //.addToBackStack(null);
                    .commit();
        }
    }
}
