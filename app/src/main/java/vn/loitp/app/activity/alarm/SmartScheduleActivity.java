package vn.loitp.app.activity.alarm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import io.hypertrack.smart_scheduler.Job;
import io.hypertrack.smart_scheduler.SmartScheduler;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.utilities.LLog;
import vn.loitp.livestar.R;

public class SmartScheduleActivity extends BaseActivity implements SmartScheduler.JobScheduledCallback {
    private static final int JOB_ID = 1;
    private static final String JOB_PERIODIC_TASK_TAG = "JOB_PERIODIC_TASK_TAG";

    private Spinner jobTypeSpinner, networkTypeSpinner;
    private Switch requiresChargingSwitch, isPeriodicSwitch;
    private EditText intervalInMillisEditText;
    private Button smartJobButton;
    private Button btOnResetSchedulerClick;

    private void findView() {
        jobTypeSpinner = (Spinner) findViewById(R.id.spinnerJobType);
        networkTypeSpinner = (Spinner) findViewById(R.id.spinnerNetworkType);
        requiresChargingSwitch = (Switch) findViewById(R.id.switchRequiresCharging);
        isPeriodicSwitch = (Switch) findViewById(R.id.switchPeriodicJob);
        intervalInMillisEditText = (EditText) findViewById(R.id.jobInterval);
        smartJobButton = (Button) findViewById(R.id.smartJobButton);
        btOnResetSchedulerClick = (Button) findViewById(R.id.bt_onResetSchedulerClick);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findView();
        smartJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSmartJobBtnClick(v);
            }
        });
        btOnResetSchedulerClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetSchedulerClick();
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
        return R.layout.activity_smart_schedule;
    }

    public void onSmartJobBtnClick(View view) {
        SmartScheduler jobScheduler = SmartScheduler.getInstance(this);

        // Check if any periodic job is currently scheduled
        if (jobScheduler.contains(JOB_ID)) {
            LLog.d(TAG, "jobScheduler.contains(JOB_ID) -> return");
            removePeriodicJob();
            return;
        } else {
            LLog.d(TAG, "!jobScheduler.contains(JOB_ID) -> continue");
        }

        // Create a new job with specified params
        Job job = createJob();
        if (job == null) {
            Toast.makeText(activity, "Invalid paramteres specified. " + "Please try again with correct job params.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Schedule current created job
        if (jobScheduler.addJob(job)) {
            Toast.makeText(activity, "Job successfully added!", Toast.LENGTH_SHORT).show();

            if (job.isPeriodic()) {
                smartJobButton.setText(getString(R.string.remove_job_btn));
            } else {
                smartJobButton.setAlpha(0.5f);
                smartJobButton.setEnabled(false);
            }
        }
    }

    private Job createJob() {
        int jobType = getJobType();
        int networkType = getNetworkTypeForJob();
        boolean requiresCharging = requiresChargingSwitch.isChecked();
        boolean isPeriodic = isPeriodicSwitch.isChecked();

        String intervalInMillisString = intervalInMillisEditText.getText().toString();
        if (TextUtils.isEmpty(intervalInMillisString)) {
            return null;
        }

        Long intervalInMillis = Long.parseLong(intervalInMillisString);
        Job.Builder builder = new Job.Builder(JOB_ID, this, jobType, JOB_PERIODIC_TASK_TAG)
                .setRequiredNetworkType(networkType)
                .setRequiresCharging(requiresCharging)
                .setIntervalMillis(intervalInMillis);

        if (isPeriodic) {
            builder.setPeriodic(intervalInMillis);
        }

        return builder.build();
    }

    private int getJobType() {
        int jobTypeSelectedPos = jobTypeSpinner.getSelectedItemPosition();
        switch (jobTypeSelectedPos) {
            default:
            case 1:
                return Job.Type.JOB_TYPE_HANDLER;
            case 2:
                return Job.Type.JOB_TYPE_ALARM;
            case 3:
                return Job.Type.JOB_TYPE_PERIODIC_TASK;
        }
    }

    private int getNetworkTypeForJob() {
        int networkTypeSelectedPos = networkTypeSpinner.getSelectedItemPosition();
        switch (networkTypeSelectedPos) {
            default:
            case 0:
                return Job.NetworkType.NETWORK_TYPE_ANY;
            case 1:
                return Job.NetworkType.NETWORK_TYPE_CONNECTED;
            case 2:
                return Job.NetworkType.NETWORK_TYPE_UNMETERED;
        }
    }

    private void removePeriodicJob() {
        smartJobButton.setText(getString(R.string.schedule_job_btn));
        SmartScheduler jobScheduler = SmartScheduler.getInstance(this);
        if (!jobScheduler.contains(JOB_ID)) {
            Toast.makeText(activity, "No job exists with JobID: " + JOB_ID, Toast.LENGTH_SHORT).show();
            return;
        }
        if (jobScheduler.removeJob(JOB_ID)) {
            Toast.makeText(activity, "Job successfully removed!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onJobScheduled(Context context, final Job job) {
        if (job != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Job: " + job.getJobId() + " scheduled!", Toast.LENGTH_SHORT).show();
                }
            });
            Log.d(TAG, "Job: " + job.getJobId() + " scheduled!");
            if (!job.isPeriodic()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        smartJobButton.setAlpha(1.0f);
                        smartJobButton.setEnabled(true);
                    }
                });
            }
        }
    }

    public void onResetSchedulerClick() {
        SmartScheduler smartScheduler = SmartScheduler.getInstance(getApplicationContext());
        smartScheduler.removeJob(JOB_ID);

        smartJobButton.setText(getString(R.string.schedule_job_btn));
        smartJobButton.setEnabled(true);
        smartJobButton.setAlpha(1.0f);
    }
}
