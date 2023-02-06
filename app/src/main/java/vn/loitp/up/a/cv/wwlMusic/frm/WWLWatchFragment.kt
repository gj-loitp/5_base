package vn.loitp.up.a.cv.wwlMusic.frm

import android.app.Activity
import android.content.Context
import android.graphics.SurfaceTexture
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Bundle
import android.view.Surface
import android.view.TextureView.SurfaceTextureListener
import android.view.View
import android.view.ViewGroup
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import kotlinx.android.synthetic.main.f_wwl_music_watch.*
import vn.loitp.R
import vn.loitp.up.a.cv.wwlMusic.itf.FragmentHost
import vn.loitp.up.a.cv.wwlMusic.layout.WWLMusicControlsOverlay
import vn.loitp.up.a.cv.wwlMusic.utils.WWLMusicDataset

@LogTag("WWLWatchFragment")
class WWLWatchFragment : BaseFragment(), SurfaceTextureListener, WWLMusicControlsOverlay.Listener {

    private val mOnPreparedListener = OnPreparedListener { mediaPlayer: MediaPlayer ->
        mediaPlayer.start()
    }
    private var mMediaPlayer: MediaPlayer? = null
    private var mSurfaceView: Surface? = null
    private var mUrl: String? = null
    private var mPlayerWWLMusicControlsOverlay: WWLMusicControlsOverlay? = null
    private var mFragmentHost: FragmentHost? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.f_wwl_music_watch
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        playerView.requestFocus()
        playerView.surfaceTextureListener = this
        mPlayerWWLMusicControlsOverlay = WWLMusicControlsOverlay(requireContext())
        mPlayerWWLMusicControlsOverlay?.setListener(this)

        (frmRootView as? ViewGroup)?.addView(mPlayerWWLMusicControlsOverlay)
    }

    @Deprecated("Deprecated in Java")
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mFragmentHost = activity as FragmentHost
    }

    fun startPlay(item: WWLMusicDataset.DatasetItem) {
        mUrl = item.url
        openVideo()
        liTitle.text = item.title
        liSubtitle.text = item.subtitle
    }

    fun stopPlay() {
        if (mUrl != null) {
            release()
        }
    }

    override fun onSurfaceTextureAvailable(
        surface: SurfaceTexture,
        width: Int,
        height: Int
    ) {
        try {
            mSurfaceView = Surface(surface)
            openVideo()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onSurfaceTextureSizeChanged(
        surface: SurfaceTexture,
        width: Int,
        height: Int
    ) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        mSurfaceView = null
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}

    override fun doCollapse() {
        mFragmentHost?.onVideoCollapse()
    }

    override fun doFullscreen(selected: Boolean) {
        mFragmentHost?.onVideoFullscreen(selected)
    }

    fun switchFullscreen(selected: Boolean) {
        mPlayerWWLMusicControlsOverlay?.switchFullscreen(selected)
    }

    fun hideControls() {
        mPlayerWWLMusicControlsOverlay?.hideControls()
    }

    fun toggleControls() {
        mPlayerWWLMusicControlsOverlay?.toggleControls()
    }

    private fun openVideo() {
        if (mUrl == null || mSurfaceView == null) {
            return
        }
        release()
        logD("openVideo $mUrl")
        val am = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        try {
            mMediaPlayer = MediaPlayer()
            mMediaPlayer?.apply {
                this.setOnPreparedListener(mOnPreparedListener)
                this.setDataSource(requireContext(), Uri.parse(mUrl))
                this.setSurface(mSurfaceView)
                this.setAudioStreamType(AudioManager.STREAM_MUSIC)
                this.setScreenOnWhilePlaying(true)
                this.prepareAsync()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun release() {
        mMediaPlayer?.apply {
            this.reset()
            this.release()
        }
        mMediaPlayer = null
        val am = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
        am?.abandonAudioFocus(null)
    }
}
