package vn.puresolutions.livestarv3.activity.userprofile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.view.LActionBar;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.DateUtils;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

import static vn.puresolutions.livestar.common.Constants.GENDER_FEMALE;
import static vn.puresolutions.livestar.common.Constants.GENDER_MALE;
import static vn.puresolutions.livestar.common.Constants.GENDER_NO;

public class EditInfoActivity extends BaseActivity {
    private Dialog dialog;
    private Bitmap currentBitmap;
    private String updateName;
    private String updateBirthday;
    private String updateGender;
    private String picturePath;
    @BindView(R.id.btnEditProfileScreen_ChangePass)
    TextView btnGoChangePass;
    @BindView(R.id.btnEditProfileScreen_UpdateProfile)
    TextView btnUpdate;
    @BindView(R.id.edtEditInfo_Name)
    EditText edtName;
    @BindView(R.id.imvEditInfo_editName)
    ImageView imvEdtName;
    @BindView(R.id.sdvEditProfileScreen_Avatar)
    SimpleDraweeView sdvAvatar;
    @BindView(R.id.labEditInfo_Header)
    LActionBar labHeader;
    @BindView(R.id.ivEditProfileScreen_ChangeAvatar)
    ImageView imvChangeAvatar;
    @BindView(R.id.ivEditProfileScreen_EditBirthday)
    ImageView imvEdtBirthday;
    @BindView(R.id.tvEditProfileScreen_Birthday)
    TextView tvBirthdate;
    @BindView(R.id.rgProfileScreen_Gender)
    RadioGroup rgGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        dialog = LDialogUtils.loadingMultiColor(activity, true);
        updateUI();
        labHeader.setTvTitle(getString(R.string.edit_profilescreen_title));
        labHeader.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {

            }
        });
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                UserProfile userProfile = LPref.getUser(activity);
                switch (checkedId) {
                    case R.id.rbProfileScreen_No:
                        updateGender = Constants.GENDER_NO;
                        break;
                    case R.id.rbProfileScreen_Male:
                        updateGender = Constants.GENDER_MALE;
                        break;
                    case R.id.rbProfileScreen_Female:
                        updateGender = Constants.GENDER_FEMALE;
                        break;
                }
                if (!updateGender.equalsIgnoreCase((String) userProfile.getGender())) {
                    btnUpdate.setEnabled(true);
                    btnUpdate.setBackgroundResource(R.drawable.background_register_btnregister);
                } else {
                    btnUpdate.setBackgroundResource(R.drawable.background_button_non_enable);
                    btnUpdate.setEnabled(false);
                }
            }
        });
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                UserProfile userProfile = LPref.getUser(activity);
                if (!s.toString().trim().equals((userProfile.getName()))) {
                    if (s.toString().trim().length() >= 6) {
                        btnUpdate.setEnabled(true);
                        btnUpdate.setBackgroundResource(R.drawable.background_register_btnregister);
                        updateName = s.toString();
                    }
                } else {
                    btnUpdate.setBackgroundResource(R.drawable.background_button_non_enable);
                    btnUpdate.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.btnEditProfileScreen_ChangePass)
    void goChangePassScreen() {
        Intent intent = new Intent(this, EditInfoChangePassActivity.class);
        startActivity(intent);
        LUIUtil.transActivityFadeIn(this);
    }

    @OnClick(R.id.imvEditInfo_editName)
    void editName() {
        if (edtName.isEnabled()) {
            edtName.setEnabled(false);
        } else {
            edtName.setEnabled(true);
            edtName.setSelection(edtName.getText().toString().trim().length());
        }
    }

    @OnClick(R.id.ivEditProfileScreen_ChangeAvatar)
    void changeAvatar() {
        LImageUtils.openAndCropImage(activity, 1, 1);
    }

    @OnClick(R.id.btnEditProfileScreen_UpdateProfile)
    void updateProfile() {
        LLog.d(TAG, "Update");
        UserProfile userProfile = LPref.getUser(this);
        if (TextUtils.isEmpty(updateBirthday) || updateBirthday == null) {
            if (userProfile.getBirthday() != null) {
                updateBirthday = (String) userProfile.getBirthday();
            } else {
                updateBirthday = null;
            }
        }
        if (TextUtils.isEmpty(updateName) || updateName == null) {
            updateName = userProfile.getName();
        }
        if (TextUtils.isEmpty(updateGender) || updateGender == null) {
            if (userProfile.getGender() != null) {
                updateGender = (String) userProfile.getGender();
            } else {
                updateGender = null;
            }
        }
        if (!TextUtils.isEmpty(picturePath) && currentBitmap != null) {
            Uri uri = LImageUtils.saveCroppedImage(this, currentBitmap);
            File imageFile = new File(uri.getPath());
            requestChangeAvatar(imageFile);
            return;
        }
        if (checkUpdateProfile()) {
            requestChangeProfile(updateName, updateBirthday, updateGender);
            return;
        }
    }

    @OnClick(R.id.ivEditProfileScreen_EditBirthday)
    void changeBirthday() {
        LDialogUtils.showDatePicker(this, new LDialogUtils.OnDateSet() {
            @Override
            public void onDateSet(int year, int month, int day) {
                String date = DateUtils.formatDatePicker(year, month, day, "dd-MM-yyyy");
                //String date = String.format(getString(R.string.date_format_value), day, month, year);
                tvBirthdate.setText(date);
                UserProfile user = LPref.getUser(activity);
                if (!date.equals(user.getBirthday().toString())) {
                    btnUpdate.setEnabled(true);
                    btnUpdate.setBackgroundResource(R.drawable.background_register_btnregister);
                    updateBirthday = DateUtils.formatDatePicker(year, month, day, "yyyy-MM-dd");
                }
            }
        });
    }

    private void requestChangeAvatar(File imageFile) {
        LDialogUtils.showDialog(dialog);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageFile);
        MultipartBody.Part formBody = MultipartBody.Part.createFormData("avatar", imageFile.getName(), requestFile);
        //
        LSService service = RestClient.createService(LSService.class);
        Observable<Void> changeAvatar = service.changeAvatar(formBody);
        Observable<UserProfile> changeInfo = changeAvatar.flatMap(requestId -> service.updateProfile(updateName, updateBirthday, updateGender));
        subscribe(changeInfo, new ApiSubscriber<UserProfile>() {
            @Override
            public void onSuccess(UserProfile result) {
                //TODO check result is null or not?
                LPref.setUser(activity, result);
                updateUI();
                btnUpdate.setEnabled(false);
                btnUpdate.setBackgroundResource(R.drawable.background_button_non_enable);
                picturePath = null;
                currentBitmap = null;
                AlertMessage.showSuccess(activity, "Đổi ảnh đại diện thành công");
                LDialogUtils.hideDialog(dialog);
            }

            @Override
            public void onFail(Throwable e) {
                LDialogUtils.hideDialog(dialog);
                handleException(e);
            }
        });
    }

    private void requestChangeProfile(String name, String birthday, String gender) {
        LDialogUtils.showDialog(dialog);
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.updateProfile(name, birthday, gender), new ApiSubscriber<UserProfile>() {
            @Override
            public void onSuccess(UserProfile result) {
                LPref.setUser(activity, result);
                updateUI();
                clearVariable();
                btnUpdate.setEnabled(false);
                btnUpdate.setBackgroundResource(R.drawable.background_button_non_enable);
                AlertMessage.showSuccess(activity, "Cập nhật thông tin thành công");
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
        return R.layout.activity_edit_info;
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
                            if (currentBitmap.getWidth() > 512) {
                                int newW = 512;
                                int newH = newW * currentBitmap.getHeight() / currentBitmap.getWidth();
                                currentBitmap = LImageUtils.getResizedBitmap(currentBitmap, newW, newH);
                            }
                            picturePath = MediaStore.Images.Media.insertImage(this.getContentResolver(), currentBitmap, null, null);
                            LImageUtils.loadImage(sdvAvatar, resultUri);
                            LLog.d(TAG, "picturePath: " + picturePath);
                            btnUpdate.setEnabled(true);
                            btnUpdate.setBackgroundResource(R.drawable.background_register_btnregister);
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

    private void updateUI() {
        UserProfile userProfile = LPref.getUser(this);
        LImageUtils.loadImage(sdvAvatar, (String) userProfile.getAvatarsPath().getAvatar());
        edtName.setText(userProfile.getName());
        if (userProfile.getFbId() != null) {
            btnGoChangePass.setEnabled(true);
        }
        switch (((String) userProfile.getGender()).toLowerCase()) {
            case GENDER_FEMALE:
                ((RadioButton) rgGender.getChildAt(2)).setChecked(true);
                break;
            case GENDER_MALE:
                ((RadioButton) rgGender.getChildAt(1)).setChecked(true);
                break;
            case GENDER_NO:
                ((RadioButton) rgGender.getChildAt(0)).setChecked(true);
                break;
        }
        if (userProfile.getBirthday() != null) {
            tvBirthdate.setText(formatdate((String) userProfile.getBirthday()));
        }
    }

    public String formatdate(String date) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date outdate = input.parse(date);
            return output.format(outdate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }


    private boolean checkUpdateProfile() {
        Log.d(TAG, "Gender: " + updateGender);
        if (updateName.length() <= 0) {
            return false;
        }
        if (TextUtils.isEmpty(updateBirthday)) {
            return false;
        }
        if (TextUtils.isEmpty(updateGender)) {
            return false;
        }
        return true;
    }

    private void clearVariable() {
        updateGender = null;
        updateName = null;
        updateBirthday = null;
    }
}
