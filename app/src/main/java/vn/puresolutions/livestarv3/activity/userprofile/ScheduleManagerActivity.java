package vn.puresolutions.livestarv3.activity.userprofile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.schedule.ScheduleItem;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.activity.userprofile.adapter.ScheduleManagerAdapter;
import vn.puresolutions.livestarv3.activity.userprofile.model.ScheduleModel;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.DateUtils;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.view.LActionBar;

public class ScheduleManagerActivity extends BaseActivity {
    //private String date;
    private Integer yyyy, mm, dd;
    private Integer h, m;
    //private String time;
    private ArrayList<ScheduleModel> lstSchedule;
    private ScheduleManagerAdapter scheduleManagerAdapter;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.labScheduleManager_Header)
    LActionBar labHeader;
    @BindView(R.id.rvScheduleManager_Schedule)
    RecyclerView rvSchedule;
    @BindView(R.id.tvScheduleManager_Count)
    TextView tvScheduleCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        loadData();
        lstSchedule = new ArrayList<>();
        labHeader.setTvTitle(getString(R.string.roommanagerscreen_title));
        labHeader.setImageMenuIcon(R.drawable.add);
        labHeader.getIvIconMenu().setVisibility(View.VISIBLE);
        labHeader.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {
                ScheduleModel scheduModel = new ScheduleModel();
                lstSchedule.add(scheduModel);
                tvScheduleCount.setText(String.format(getString(R.string.schedulerscreen_count_schedule), lstSchedule.size()));
                rvSchedule.scrollToPosition(lstSchedule.size() - 1);
                scheduleManagerAdapter.notifyItemInserted(lstSchedule.size() - 1);
            }
        });

        rvSchedule.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSchedule.setLayoutManager(linearLayoutManager);
        scheduleManagerAdapter = new ScheduleManagerAdapter(this, lstSchedule, new ScheduleManagerAdapter.Callback() {
            @Override
            public void onDelete(ScheduleModel scheduleItem, int pos) {
                //LLog.d(TAG, "onDelete pos " + pos);
                if (pos >= 0 && pos < lstSchedule.size()) {
                    if (lstSchedule.get(pos).getId() != null) {
                        //LLog.d(TAG, "onDelete if");
                        deleteSchedule(scheduleItem.getId(), pos);
                    } else {
                        //LLog.d(TAG, "onDelete else");
                        lstSchedule.remove(pos);
                        scheduleManagerAdapter.notifyItemRemoved(pos);
                        scheduleManagerAdapter.notifyItemRangeChanged(pos, scheduleManagerAdapter.getItemCount() - pos);
                        tvScheduleCount.setText(String.format(getString(R.string.schedulerscreen_count_schedule), lstSchedule.size()));
                    }
                }
            }

            @Override
            public void onUpDateDate(ScheduleManagerAdapter.IdolEditScheduleHolder idolEditScheduleHolder, ScheduleModel scheduleItem, int pos) {
                LDialogUtils.showDatePicker(activity, new LDialogUtils.OnDateSet() {
                    @Override
                    public void onDateSet(int year, int month, int day) {
                        yyyy = year;
                        mm = month;
                        dd = day;
                        String date = DateUtils.formatDatePicker(year, month, day, "dd/MM/yyyy");
                        scheduleItem.setDate(date);
                        scheduleManagerAdapter.notifyDataSetChanged();
                        if (h != null && m != null || scheduleItem.getTime() != null) {
                            String time24 = convert12to24(scheduleItem.getTime());
                            String[] timesplit = time24.split(":");
                            h = Integer.valueOf(timesplit[0]);
                            m = Integer.valueOf(timesplit[1]);
                            long timestamp = convertDateToTimeStamp(scheduleItem.getDate(), h, m);
                            if (scheduleItem.getId() != null) {
                                if (scheduleItem.getId().toString().length() > 0) {
                                    updateSchedule(idolEditScheduleHolder, scheduleItem.getId(), timestamp);
                                }
                                //update
                            } else {
                                //add new;
                                addSchedule(idolEditScheduleHolder, timestamp);
                            }
                        }
                    }
                });
            }

            @Override
            public void onUpDateTime(ScheduleManagerAdapter.IdolEditScheduleHolder idolEditScheduleHolder, ScheduleModel scheduleItem, int pos, String date) {
                LDialogUtils.showTimePicker(activity, new LDialogUtils.OnTimeSet() {
                    @Override
                    public void onTimeSet(int hour, int minute) {
                        h = hour;
                        m = minute;
                        boolean isPM = (hour >= 12);
                        String strhour = String.format("%02d:%02d %s", (hour == 12 || hour == 0) ? 12 : hour % 12, minute, isPM ? "PM" : "AM");
                        scheduleItem.setTime(strhour);
                        scheduleManagerAdapter.notifyDataSetChanged();
                        if (yyyy != null && mm != null && dd != null || scheduleItem.getDate() != null) {
                           /* String lstart = scheduleItem.getDate() + " " + hour + ":" + minute;
                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                            Date date = null;
                            try {
                                date = format.parse(lstart);
                                Log.d(TAG, "lstart: " + lstart);
                                Log.d(TAG, "Time requets: " + date.getTime() / 1000);
                                if (scheduleItem.getId() != null || scheduleItem.getId().toString().length() > 0) {
                                    //update
                                    Log.d(TAG, "else");
                                    updateSchedule(scheduleItem.getId(), date.getTime() / 1000);
                                } else {
                                    //add new;
                                    addSchedule(Long.valueOf(date.getTime() / 1000));
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }*/
                            long timestamp = convertDateToTimeStamp(scheduleItem.getDate(), h, m);
                            if (scheduleItem.getId() != null) {
                                //update
                                updateSchedule(idolEditScheduleHolder, scheduleItem.getId(), timestamp);
                            } else {
                                //add new;
                                addSchedule(idolEditScheduleHolder, timestamp);
                            }
                        }
                    }
                });

            }
        });
        rvSchedule.setAdapter(scheduleManagerAdapter);
    }

    private void loadData() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.getSchedule(), new ApiSubscriber<ArrayList<ScheduleItem>>() {
            @Override
            public void onSuccess(ArrayList<ScheduleItem> result) {
                if (result != null) {
                    for (ScheduleItem sc : result) {
                       /* Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        SimpleDateFormat outformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                        //SimpleDateFormat outformat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZZ");
                        Date date = null;
                        try {
                            date = format.parse(sc.getStart());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        ;
                        calendar.setTime(date);
                        calendar.add(Calendar.SECOND, -18000);
                        Date newdate = calendar.getTime();
                        Log.d(TAG, "new date: " + outformat.format(newdate));
                        String startDate = DateUtils.convertFormatDate(outformat.format(newdate), "yyyy-MM-dd'T'hh:mm:ss", "dd/MM/yyyy");
                        Log.d(TAG, "startDate: " + startDate);
                        String hour = DateUtils.convertFormatDate(outformat.format(newdate), "yyyy-MM-dd'T'hh:mm:ss", "hh:mm a");
                        Log.d(TAG, "starthour: " + hour);
                        lstSchedule.add(new ScheduleModel(sc.getId(), startDate, hour));*/

                        String date = getDate(sc.getStart());
                        //String startDate = DateUtils.convertFormatDate(date, "dd/MM/yyyy hh:mm:", "dd/MM/yyyy");
                        //String hour = DateUtils.convertFormatDate(date, "dd/MM/yyyy hh:mm:", "hh:mm a");
                        String[] splitDate = date.split(" ", 2);
                        String startDate = splitDate[0];
                        String hour = splitDate[1];
                        lstSchedule.add(new ScheduleModel(sc.getId(), startDate, hour));
                    }
                    scheduleManagerAdapter.notifyDataSetChanged();
                    tvScheduleCount.setText(String.format(getString(R.string.schedulerscreen_count_schedule), lstSchedule.size()));
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void updateSchedule(ScheduleManagerAdapter.IdolEditScheduleHolder idolEditScheduleHolder, String id, long start) {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.updateSchedule(id, start), new ApiSubscriber<ScheduleItem>() {
            @Override
            public void onSuccess(ScheduleItem result) {
                if (result != null) {
                    if (result.getError() != null) {
                        AlertMessage.showSuccess(activity, "Sửa không hợp lệ");
                    } else {
                        playAnimFadeSavedView(idolEditScheduleHolder);
                        AlertMessage.showSuccess(activity, "Sửa lịch diễn thành công");
                        clearVariable();
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void playAnimFadeSavedView(ScheduleManagerAdapter.IdolEditScheduleHolder idolEditScheduleHolder) {
        //LLog.d(TAG, "playAnimFadeSavedView");
        if (idolEditScheduleHolder != null) {
            idolEditScheduleHolder.playFadeSaved();
        }
    }

    private void addSchedule(ScheduleManagerAdapter.IdolEditScheduleHolder idolEditScheduleHolder, long start) {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.createSchedule(start), new ApiSubscriber<ScheduleItem>() {
            @Override
            public void onSuccess(ScheduleItem result) {
                if (result.getError() != null) {
                    if (result.getError() == 2005) {
                        AlertMessage.showError(activity, "Thời gian không hợp lệ");
                    }
                } else {
                    AlertMessage.showSuccess(activity, "Thêm lịch diễn thành công");
                    clearVariable();
                    playAnimFadeSavedView(idolEditScheduleHolder);
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void deleteSchedule(String id, int pos) {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.deleteSchedule(id), new ApiSubscriber() {
            @Override
            public void onSuccess(Object result) {
                lstSchedule.remove(pos);
                scheduleManagerAdapter.notifyItemRemoved(pos);
                scheduleManagerAdapter.notifyItemRangeChanged(pos, scheduleManagerAdapter.getItemCount() - pos);
                tvScheduleCount.setText(String.format(getString(R.string.schedulerscreen_count_schedule), lstSchedule.size()));
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_schedule_manager;
    }

    private void clearVariable() {
        yyyy = null;
        dd = null;
        mm = null;
        h = null;
        m = null;
    }

    private long convertDateToTimeStamp(String d, int h, int m) {
        //String lstart = dd + "/" + mm + "/" + yyyy + " " + h + ":" + m;
        String lstart = d + " " + h + ":" + m;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        try {
            Date date = format.parse(lstart);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String convert12to24(String h12) {
        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        try {
            return date24Format.format(date12Format.parse(h12));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        try {
            value = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.ENGLISH);
        dateFormatter.setTimeZone(TimeZone.getDefault());
        //dateFormatter.setTimeZone(Locale.ENGLISH);
        return dateFormatter.format(value);
    }
}
