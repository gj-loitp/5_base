package vn.loitp.app.activity.demo.firebase.auth

import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Base64
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_auth_firebase_facebook.*
import vn.loitp.app.R
import java.security.MessageDigest

//https://github.com/firebase/quickstart-android
@LogTag("AuthFirebaseFacebookActivity")
@IsFullScreen(false)
class AuthFirebaseFacebookActivity : BaseFontActivity(), View.OnClickListener {
    private var firebaseAuth: FirebaseAuth? = null
    private var callbackManager: CallbackManager? = null
    private var progressDialog: ProgressDialog? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_auth_firebase_facebook
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        printHashKey()
        btFacebookSignOut.setOnClickListener(this)

        firebaseAuth = FirebaseAuth.getInstance()

        callbackManager = CallbackManager.Factory.create()

        btFacebookLogin.setReadPermissions("email", "public_profile")
        btFacebookLogin.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                logD("facebook:onSuccess: $loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                logD("facebook:onCancel")
                updateUI(user = null)
            }

            override fun onError(error: FacebookException) {
                logD("facebook:onError $error")
                updateUI(user = null)
            }
        })
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth?.currentUser
        updateUI(user = currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        logD("handleFacebookAccessToken:$token")
        showProgressDialog()
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth
                ?.signInWithCredential(credential)
                ?.addOnCompleteListener(this) { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {
                        logD("signInWithCredential:success")
                        val user = firebaseAuth?.currentUser
                        updateUI(user = user)
                    } else {
                        logD("signInWithCredential:failure " + task.exception)
                        showShortInformation("Authentication failed " + task.exception, true)
                        updateUI(user = null)
                    }
                    hideProgressDialog()
                }
    }

    private fun signOut() {
        firebaseAuth?.signOut()
        LoginManager.getInstance().logOut()
        updateUI(user = null)
    }

    private fun updateUI(user: FirebaseUser?) {
        hideProgressDialog()
        if (user == null) {
            status.text = getString(R.string.signed_out)
            detail.text = null
            btFacebookLogin.visibility = View.VISIBLE
            btFacebookSignOut.visibility = View.GONE
        } else {
            status.text = getString(R.string.facebook_status_fmt, user.displayName)
            LUIUtil.printBeautyJson(o = user, textView = detail)
            try {
                LImageUtil.load(
                        context = this,
                        any = user.photoUrl.toString() + "?height=500",
                        imageView = findViewById(R.id.icon),
                        resPlaceHolder = R.color.colorPrimary,
                        resError = R.color.colorPrimary,
                        transformation = null,
                        drawableRequestListener = object : RequestListener<Drawable> {
                            override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                                return false
                            }

                            override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                                return false
                            }
                        })
            } catch (e: Exception) {
                e.printStackTrace()
            }
            btFacebookLogin.visibility = View.GONE
            btFacebookSignOut.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View) {
        if (v == btFacebookSignOut) {
            signOut()
        }
    }

    private fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
            progressDialog?.setMessage(getString(R.string.loading))
            progressDialog?.isIndeterminate = true
        }
        progressDialog?.show()
    }

    private fun hideProgressDialog() {
        if (progressDialog != null && progressDialog?.isShowing == true) {
            progressDialog?.dismiss()
        }
    }

    public override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

    private fun printHashKey() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                logD("printHashKey() Hash Key: $hashKey")
            }
        } catch (e: Exception) {
            logE("printHashKey() $e")
        }
    }
}