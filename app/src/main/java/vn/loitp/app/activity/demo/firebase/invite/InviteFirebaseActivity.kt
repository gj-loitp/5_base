package vn.loitp.app.activity.demo.firebase.invite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.google.android.gms.appinvite.AppInviteInvitation
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.appinvite.FirebaseAppInvite
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import kotlinx.android.synthetic.main.activity_invite_firebase.*
import vn.loitp.app.R

//https://github.com/firebase/quickstart-android
//TODO loitpp api deprecated
@LogTag("InviteFirebaseActivity")
@IsFullScreen(false)
class InviteFirebaseActivity :
        BaseFontActivity(),
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    companion object {
        private const val REQUEST_INVITE = 0
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_invite_firebase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inviteButton.setOnClickListener(this)

        // Check for App Invite invitations and launch deep-link activity if possible.
        // Requires that an Activity is registered in AndroidManifest.xml to handle
        // deep-link URLs.
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(intent)
                .addOnSuccessListener(this) { data: PendingDynamicLinkData? ->
                    if (data == null) {
                        logD("getInvitation: no data")
                        return@addOnSuccessListener
                    }

                    // Get the deep link
                    val deepLink = data.link

                    // Extract invite
                    val invite = FirebaseAppInvite.getInvitation(data)
                    if (invite != null) {
                        val invitationId = invite.invitationId
                        logD("invitationId $invitationId")
                    }

                    // Handle the deep link
                    logD("deepLink:$deepLink")
                    deepLink?.let {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.setPackage(packageName)
                        intent.data = it
                        startActivity(intent)
                    }
                }
                .addOnFailureListener(this) { e: Exception? ->
                    logE("getDynamicLink:onFailure $e")
                }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        logD("onConnectionFailed:$connectionResult")
        showMessage(getString(R.string.google_play_services_error))
    }

    /**
     * User has clicked the 'Invite' button, launch the invitation UI with the proper
     * title, message, and deep link
     */
    private fun onInviteClicked() {
        val intent = AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build()
        startActivityForResult(intent, REQUEST_INVITE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        logD("onActivityResult: requestCode=$requestCode, resultCode=$resultCode")
        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                data?.let {
                    val ids = AppInviteInvitation.getInvitationIds(resultCode, it)
                    for (id in ids) {
                        logD("onActivityResult: sent invitation $id")
                    }
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                showMessage(getString(R.string.send_failed))
            }
        }
    }

    private fun showMessage(msg: String) {
        Snackbar.make(layoutSnackBar, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onClick(view: View) {
        if (view == inviteButton) {
            onInviteClicked()
        }
    }

}
