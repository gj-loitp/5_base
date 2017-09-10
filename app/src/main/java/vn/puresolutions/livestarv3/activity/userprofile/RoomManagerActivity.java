package vn.puresolutions.livestarv3.activity.userprofile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.facebook.drawee.view.SimpleDraweeView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.categoryget.Category;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestar.corev3.api.model.v3.schedule.ScheduleItem;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolcategory.ModelIdolCategory;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolcategory.ModelIdolCategoryDetail;
import vn.puresolutions.livestarv3.activity.livestream.LivestreamActivity;
import vn.puresolutions.livestarv3.activity.userprofile.model.RoomManagerListener;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.view.LActionBar;
import vn.puresolutions.livestarv3.view.LSpinner;

public class RoomManagerActivity extends BaseActivity implements RoomManagerListener {
    ArrayList<ModelIdolCategoryDetail> lstIdolCategory;
    private Dialog dialog;
    private Bitmap currentBitmap;
    @BindView(R.id.labRoomManager_Header)
    LActionBar labHeader;
    @BindView(R.id.sdvRoomManager_Avatar)
    SimpleDraweeView sdvAvatar;
    @BindView(R.id.btnRoomManager_AddSchedule)
    TextView btnRomManager_AddSchedule;
    @BindView(R.id.spnRoomManager_Category)
    LSpinner spnCategory;
    @BindView(R.id.imvRoomManager_ChangeBanner)
    ImageView imvChangeBanner;
    @BindView(R.id.edtRoomManager_Name)
    EditText edtName;
    @BindView(R.id.edtRoomManager_Status)
    EditText edtStatus;
    @BindView(R.id.tvRoomManager_CountView)
    TextView tvCountView;
    @BindView(R.id.tvRoomManager_CountHeart)
    TextView tvCountHeart;
    @BindView(R.id.tvRoomManager_CategoryType)
    TextView tvType;
    @BindView(R.id.tvRoomManager_Schedule)
    TextView tvCountSchedule;

    private boolean isCalledFromLoginActivity;
    private String oldName;
    private String oldDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        isCalledFromLoginActivity = getIntent().getBooleanExtra(Constants.ROOM_MANAGER_IS_CALL_FROM_LOGIN_ACTIVITY, false);
        if (isCalledFromLoginActivity) {
            AlertMessage.showSuccess(activity, getString(R.string.pls_update_room_info));
        }
        init();
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setTypeface(null, Typeface.BOLD);
        TextView tvType = (TextView) findViewById(R.id.tv_type);
        tvType.setTypeface(null, Typeface.BOLD);
    }

    private void init() {
        dialog = LDialogUtils.loadingMultiColor(activity, true);
        lstIdolCategory = new ArrayList<>();
        ModelIdolCategory modelIdolCategory = LPref.getModelIdolCatgory(activity);
        lstIdolCategory.addAll(modelIdolCategory.getModelIdolCategoryDetailArrayList());

        //LLog.d(TAG, ">>>" + LSApplication.getInstance().getGson().toJson(lstIdolCategory));

        labHeader.setTvTitle(getString(R.string.roommanagerscreen_title));
        labHeader.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {
                //do nothing
            }
        });

        btnRomManager_AddSchedule.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int w = btnRomManager_AddSchedule.getMeasuredWidth();
        //LLog.d(TAG, "w: " + w);
        spnCategory.getLayoutParams().width = w;
        spnCategory.requestLayout();

        String[] types = new String[lstIdolCategory.size()];
        for (int i = 0; i < lstIdolCategory.size(); i++) {
            types[i] = lstIdolCategory.get(i).getName();
        }
        spnCategory.initializeStringValues(types, getString(R.string.choose_type));
        updateUI();
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //LLog.d(TAG, "position " + position);

                if (isSpinnerFirstClick) {
                    //LLog.d(TAG, "isSpinnerFirstClick true");
                    if (position != 0) {
                        position--;
                    }
                } else {
                    //LLog.d(TAG, "isSpinnerFirstClick false");
                    position--;
                    //LLog.d(TAG, "position: " + position);
                    //LLog.d(TAG, "type: " + lstIdolCategory.get(position).getType() + " " + lstIdolCategory.get(position).getName());
                    UserProfile userProfile = LPref.getUser(activity);
                    Room room = userProfile.getRoom();
                    if (room.getCategory() != null) {
                        if (!room.getCategory().getId().equals(lstIdolCategory.get(position).getType())) {
                            //LLog.d(TAG, "1 call request " + lstIdolCategory.get(position).getName() + ", type: " + lstIdolCategory.get(position).getType());
                            requestChangeCategory(lstIdolCategory.get(position).getType());
                        } else {
                            //LLog.d(TAG, "2 no call request");
                        }
                    } else {
                        //LLog.d(TAG, "3 no call request " + lstIdolCategory.get(position).getType());
                        //requestChangeCategory(lstIdolCategory.get(position).getType());
                    }
                    tvType.setText(lstIdolCategory.get(position).getName());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });
        spnCategory.setSpinnerEventsListener(new LSpinner.OnSpinnerEventsListener() {
            @Override
            public void onSpinnerOpened() {
                //LLog.d(TAG, "onSpinnerOpened");
                isSpinnerFirstClick = false;
            }

            @Override
            public void onSpinnerClosed() {
                //LLog.d(TAG, "onSpinnerClosed");
            }
        });
    }

    private boolean isSpinnerFirstClick = true;

    @OnClick(R.id.imvRoomManager_ChangeBanner)
    void changeBanner() {
        LAnimationUtil.play(sdvAvatar, Techniques.FlipInY, new LAnimationUtil.Callback() {
            @Override
            public void onCancel() {
                //do nothing
            }

            @Override
            public void onEnd() {
                LImageUtils.openAndCropImage(activity, 16, 9);
            }

            @Override
            public void onRepeat() {
                //do nothing
            }

            @Override
            public void onStart() {
                //do nothing
            }
        });
    }

    @OnClick(R.id.btnRoomManager_AddSchedule)
    void doAddSchedule() {
        UserProfile userProfile = LPref.getUser(activity);
        boolean isIdol = userProfile.getIsIdol() == Constants.USER_IS_IDOL;
        if (isIdol) {
            Intent intent = new Intent(activity, ScheduleManagerActivity.class);
            startActivity(intent);
            LUIUtil.transActivityFadeIn(activity);
        } else {
            showDialogRequestIdol();
        }
    }

    private void showDialogRequestIdol() {
        LDialogUtils.showDlg1Option(activity, R.mipmap.ic_launcher, getString(R.string.warning), getString(R.string.to_be_idol), getString(R.string.confirm), new LDialogUtils.Callback1() {
            @Override
            public void onClickButton0() {
                //do nothing
            }
        });
    }

    private void requestChangeBanner(File imageFile) {
        LDialogUtils.showDialog(dialog);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageFile);
        MultipartBody.Part formBody = MultipartBody.Part.createFormData("banner", imageFile.getName(), requestFile);
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.updateBanner(formBody), new ApiSubscriber<Room>() {
            @Override
            public void onSuccess(Room result) {
                UserProfile userProfile = LPref.getUser(activity);
                userProfile.setRoom(result);
                LPref.setUser(activity, userProfile);
                LDialogUtils.hideDialog(dialog);
                AlertMessage.showSuccess(activity, getString(R.string.update_banner_success));
            }

            @Override
            public void onFail(Throwable e) {
                LDialogUtils.hideDialog(dialog);
                handleException(e);
            }
        });
    }

    private void requestChangeName() {
        LDialogUtils.showDialog(dialog);
        LSService service = RestClient.createService(LSService.class);
        String name = edtName.getText().toString().trim();
        String description = edtStatus.getText().toString().trim();
        //LLog.d(TAG, "name: " + name + ", description " + description);
        subscribe(service.updateRoomInfo(name, description), new ApiSubscriber<UserProfile>() {
            @Override
            public void onSuccess(UserProfile userProfile) {
                //LLog.d(TAG, "requestChangeName onSuccess " + userProfile.getRoom().getTitle() + ", " + userProfile.getRoom().getDescription());
                LPref.setUser(activity, userProfile);
                if (isCalledFromLoginActivity) {
                    Intent intent = new Intent(activity, LivestreamActivity.class);
                    startActivity(intent);
                    LUIUtil.transActivityFadeIn(activity);
                    finish();
                } else {
                    finish();
                    LUIUtil.transActivityFadeIn(activity);
                }
                LDialogUtils.hideDialog(dialog);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                LDialogUtils.hideDialog(dialog);
                finish();
                LUIUtil.transActivityFadeIn(activity);
            }
        });
    }

    private void requestChangeCategory(String categoryId) {
        //LLog.d(TAG, ">>>>>>>>>>>>>>>>>requestChangeCategory " + categoryId);
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.updateCategory(categoryId), new ApiSubscriber<Room>() {
            @Override
            public void onSuccess(Room room) {
                //LLog.d(TAG, "onSuccess: " + LSApplication.getInstance().getGson().toJson(room));

                String newTypeCategory = room.getCategoryId();
                String newTitle = "";
                for (int i = 0; i < lstIdolCategory.size(); i++) {
                    if (newTypeCategory.equals(lstIdolCategory.get(i).getType())) {
                        newTitle = lstIdolCategory.get(i).getName();
                        break;
                    }
                }
                //LLog.d(TAG, ">>>newTitle: " + newTitle + ", newTypeCategory " + newTypeCategory);

                Category category = room.getCategory();
                category.setDescription(newTitle);
                category.setTitle(newTitle);
                category.setSlug(newTitle.toLowerCase());
                category.setId(newTypeCategory);

                room.setCategory(category);

                UserProfile userProfile = LPref.getUser(activity);
                userProfile.setRoom(room);
                LPref.setUser(activity, userProfile);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void updateUI() {
        UserProfile userProfile = LPref.getUser(activity);
        Room room = userProfile.getRoom();
        //LLog.d(TAG, "updateUI " + userProfile.getRoom().getCategory().getTitle());
        int cateType = 0;
        if (room.getCategory() != null) {
            for (int i = 0; i < lstIdolCategory.size(); i++) {
                if (lstIdolCategory.get(i).getType().equals(room.getCategory().getId())) {
                    cateType = i + 1;
                    break;
                }
            }
            spnCategory.setSelection(cateType);
        } else {
            spnCategory.setSelection(cateType);
        }
        //LLog.d(TAG, "setSelection " + cateType);
        if (room.getBanner() != null) {
            LImageUtils.loadImage(sdvAvatar, room.getBanners().getW512h512());
        }

        oldName = room.getTitle();
        oldDescription = (String) userProfile.getRoom().getDescription();

        edtName.setText(oldName);
        edtName.setSelection(edtName.getText().length());
        tvCountHeart.setText(String.valueOf(userProfile.getRoom().getReceivedHeart()));
        //LLog.d(TAG, "userProfile.getRoom().getDescription(): " + userProfile.getRoom().getDescription());
        if (oldDescription != null) {
            edtStatus.setText(oldDescription);
        } else {
            edtStatus.setText("");
        }
        tvCountSchedule.setText(String.format(getString(R.string.roommanagerscreen_count_schedule), room.getSchedules() != null ? room.getSchedules().size() : 0));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                if (resultUri != null) {
                    try {
                        currentBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                        if (currentBitmap != null) {
                            if (currentBitmap.getWidth() > 1080) {
                                int newW = 1080;
                                int newH = newW * currentBitmap.getHeight() / currentBitmap.getWidth();
                                currentBitmap = LImageUtils.getResizedBitmap(currentBitmap, newW, newH);
                            }
                            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), currentBitmap, null, null);
                            LImageUtils.loadImage(sdvAvatar, path);
                            this.onChangeBanner(currentBitmap);
                        } else {
                            AlertMessage.showError(activity, getString(R.string.err_select_crop_image));
                        }
                    } catch (IOException e) {
                        AlertMessage.showError(activity, getString(R.string.err_select_crop_image));
                    }
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                AlertMessage.showError(activity, getString(R.string.err_select_crop_image));
            }
        }
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
        return R.layout.activity_room_manager;
    }

    @Override
    public void onChangeBanner(Bitmap bitmap) {
        Uri uri = LImageUtils.saveCroppedImage(this, currentBitmap);
        File imageFile = new File(uri.getPath());
        requestChangeBanner(imageFile);
    }

    private void loadData() {
        LDialogUtils.showDialog(dialog);
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.getSchedule(), new ApiSubscriber<ArrayList<ScheduleItem>>() {
            @Override
            public void onSuccess(ArrayList<ScheduleItem> result) {
                if (result != null) {
                    tvCountSchedule.setText(String.format(getString(R.string.roommanagerscreen_count_schedule), result != null ? result.size() : 0));
                }
                LDialogUtils.hideDialog(dialog);
            }

            @Override
            public void onFail(Throwable e) {
                LDialogUtils.hideDialog(dialog);
                handleException(e);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onBackPressed() {
        String newName = edtName.getText().toString();
        String newDescription = edtStatus.getText().toString();
        //LLog.d(TAG, oldName + "->" + newName);
        //LLog.d(TAG, oldDescription + "->" + newDescription);
        if (newName.equals(oldName) && newDescription.equals(oldDescription)) {
            //LLog.d(TAG, "no requestChangeName");
            if (isCalledFromLoginActivity) {
                Intent intent = new Intent(activity, LivestreamActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
                finish();
            } else {
                finish();
                LUIUtil.transActivityFadeIn(activity);
            }
        } else {
            //LLog.d(TAG, "requestChangeName");
            requestChangeName();
        }
    }
}
