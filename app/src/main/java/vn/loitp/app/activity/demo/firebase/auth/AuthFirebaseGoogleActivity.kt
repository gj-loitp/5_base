package vn.loitp.app.activity.demo.firebase.auth

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_auth_firebase_google.*
import vn.loitp.app.R

//https://github.com/firebase/quickstart-android
@LogTag("AuthFirebaseGoogleActivity")
@IsFullScreen(false)
class AuthFirebaseGoogleActivity : BaseFontActivity(), View.OnClickListener {

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    private var firebaseAuth: FirebaseAuth? = null
    private var googleSignInClient: GoogleSignInClient? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_auth_firebase_google
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btSignIn.setOnClickListener(this)
        btSignOut.setOnClickListener(this)
        btDisconnect.setOnClickListener(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth?.currentUser
        updateUI(firebaseUser = currentUser)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(googleSignInAccount = account)
            } catch (e: ApiException) {
                logE("Google sign in failed $e")
                updateUI(firebaseUser = null)
            }
        }
    }

    private fun firebaseAuthWithGoogle(googleSignInAccount: GoogleSignInAccount) {
        logD("firebaseAuthWithGoogle:" + googleSignInAccount.id)
        showProgressDialog()
        val credential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        firebaseAuth
                ?.signInWithCredential(credential)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        logD("signInWithCredential:success")
                        val user = firebaseAuth?.currentUser
                        updateUI(firebaseUser = user)
                    } else {
                        logD("signInWithCredential:failure " + task.exception)
                        Snackbar.make(rootView, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                        updateUI(firebaseUser = null)
                    }
                    hideProgressDialog()
                }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        firebaseAuth?.signOut()
        googleSignInClient?.signOut()?.addOnCompleteListener(this) {
            updateUI(firebaseUser = null)
        }
    }

    private fun revokeAccess() {
        firebaseAuth?.signOut()
        googleSignInClient?.revokeAccess()?.addOnCompleteListener(this) {
            updateUI(firebaseUser = null)
        }
    }

    private fun updateUI(firebaseUser: FirebaseUser?) {
        hideProgressDialog()
        if (firebaseUser == null) {
            status.text = getString(R.string.signed_out)
            detail.text = null
            btSignIn.visibility = View.VISIBLE
            signOutAndDisconnect.visibility = View.GONE
        } else {
            status.text = getString(R.string.google_status_fmt, firebaseUser.email)
            LUIUtil.printBeautyJson(o = firebaseUser, textView = detail)
            logD("user.getPhotoUrl() " + firebaseUser.photoUrl)
            try {
                var url = firebaseUser.photoUrl.toString()
                url = url.replace("/s96-c/", "/s300-c/")
                LImageUtil.load(context = this,
                        any = url,
                        imageView = googleIcon,
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
            btSignIn.visibility = View.GONE
            signOutAndDisconnect.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View) {
        when (v) {
            btSignIn -> {
                signIn()
            }
            btSignOut -> {
                signOut()
            }
            btDisconnect -> {
                revokeAccess()
            }
        }
    }

    @VisibleForTesting
    var mProgressDialog: ProgressDialog? = null
    private fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)
            mProgressDialog?.setMessage(getString(R.string.loading))
            mProgressDialog?.isIndeterminate = true
        }
        mProgressDialog?.show()
    }

    private fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog?.isShowing == true) {
            mProgressDialog?.dismiss()
        }
    }

    public override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

}
