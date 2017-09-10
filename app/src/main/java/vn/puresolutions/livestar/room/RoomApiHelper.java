package vn.puresolutions.livestar.room;

import android.content.Context;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 12/5/2015
 */
public class RoomApiHelper {

    private Context context;
    private RoomCenter roomCenter;

//    private LoadRoomDetailTask loadRoomDetailTask;
//    private LoadIdolTask loadIdolTask;
//    private FollowIdolTask followIdolTask;
//    private AddHeartTask addHeartTask;

    public RoomApiHelper(Context context, RoomCenter roomCenter) {
        this.roomCenter = roomCenter;
        this.context = context;
    }

    public void loadRoomDetail(int roomId) {
//        if (loadRoomDetailTask != null) {
//            loadRoomDetailTask.cancel(true);
//        }
//
//        loadRoomDetailTask = new LoadRoomDetailTask(context);
//        loadRoomDetailTask.execute(roomId);
    }

    public void loadIdolProfile(long idolId) {
//        if (loadIdolTask != null) {
//            loadIdolTask.cancel(true);
//        }
//        loadIdolTask = new LoadIdolTask(context);
//        loadIdolTask.startWithCheckNetwork(idolId);
    }

    public void followIdol(long idolId, boolean isFollow) {
//        if (followIdolTask != null) {
//            followIdolTask.cancel(true);
//        }
//        followIdolTask = new FollowIdolTask(context);
//        followIdolTask.startWithCheckNetwork(idolId, isFollow ? 1l : 0l);
    }

    public void addHeart(int roomId) {
//        if (addHeartTask != null) {
//            addHeartTask.cancel(true);
//        }
//        addHeartTask = new AddHeartTask(context);
//        addHeartTask.execute(new HeartParam(roomId));
    }

    public void cancelAllTask() {
//        if (loadIdolTask != null) {
//            loadIdolTask.cancel(true);
//        }
//
//        if (loadRoomDetailTask != null) {
//            loadRoomDetailTask.cancel(true);
//        }
//
//        if (followIdolTask != null) {
//            followIdolTask.cancel(true);
//        }
    }

//    private class LoadRoomDetailTask extends LSAsyncTask<Integer, Void, RoomDetail> {
//
//        public LoadRoomDetailTask(Context context) {
//            super(context);
//        }
//
//        @Override
//        protected RoomDetail run(Integer... params) throws Exception {
//            RoomInvoker invoker = new RoomInvoker(context);
//            return invoker.getRoomDetail(params[0]);
//        }
//
//        @Override
//        protected void onSuccess(RoomDetail roomDetail) {
//            roomCenter.onLoadRoomDetailCompleted(roomDetail);
//        }
//
//        @Override
//        protected void onFail(Exception exception) {
//            roomCenter.onLoadRoomDetailFailed(exception);
//        }
//    }
//
//    private class LoadIdolTask extends LSAsyncTask<Long, Void, Broadcaster> {
//
//        public LoadIdolTask(Context context) {
//            super(context);
//        }
//
//        @Override
//        protected Broadcaster run(Long... params) throws Exception {
//            BroadcasterInvoker invoker = new BroadcasterInvoker(context);
//            return invoker.getBroadcaster(params[0]);
//        }
//
//        @Override
//        protected void onSuccess(Broadcaster broadcaster) {
//            roomCenter.onLoadIdolProfileCompleted(broadcaster);
//        }
//
//        @Override
//        protected void onFail(Exception exception) {
//            roomCenter.onLoadIdolProfileFailed(exception);
//        }
//    }
//
//    private class FollowIdolTask extends LSAsyncTask<Long, Void, Void> {
//
//        public FollowIdolTask(Context context) {
//            super(context);
//        }
//
//        @Override
//        protected Void run(Long... params) throws Exception {
//            BroadcasterInvoker invoker = new BroadcasterInvoker(context);
//            invoker.follow(params[0], params[1] != 0);
//            return null;
//        }
//
//        @Override
//        protected void onSuccess(Void result) {
//            roomCenter.onChangeFollowStatusCompleted();
//        }
//
//        @Override
//        protected void onFail(Exception exception) {
//            super.onFail(exception);
//            roomCenter.onChangeFollowStatusFailed();
//        }
//    }
//
//    private class AddHeartTask extends LSAsyncTask<HeartParam, Void, Response> {
//
//        public AddHeartTask(Context context) {
//            super(context);
//        }
//
//        @Override
//        protected Response run(HeartParam... params) throws Exception {
//            LiveInvoker invoker = new LiveInvoker(context);
//            return invoker.addHeart(params[0]);
//        }
//
//        @Override
//        protected void onSuccess(Response response) {
//            if (response.getStatus() != 204) {
//                roomCenter.onAddHeartCompleted();
//            }
//        }
//
//        @Override
//        protected void onFail(Exception exception) {
//            exception.printStackTrace();
//        }
//    }
}
