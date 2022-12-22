package vn.loitp.app.activity.demo.piano

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LScreenUtil
import com.loitp.views.piano.entity.AutoPlayEntity
import com.loitp.views.piano.entity.Piano
import com.loitp.views.piano.listener.OnLoadAudioListener
import com.loitp.views.piano.listener.OnPianoAutoPlayListener
import com.loitp.views.piano.listener.OnPianoListener
import com.loitp.views.piano.utils.AutoPlayUtils
import kotlinx.android.synthetic.main.activity_piano.*
import vn.loitp.R
import java.io.IOException

@LogTag("PianoActivity")
@IsFullScreen(true)
@IsAutoAnimation(false)
class PianoActivity : BaseFontActivity(), OnPianoListener, OnLoadAudioListener,
    OnSeekBarChangeListener,
    View.OnClickListener, OnPianoAutoPlayListener {

    private val configFileName = "simple_little_star_config"
    private val useConfigFile = true
    private var scrollProgress = 0
    private val seekbarOffsetSize = -12f
    private var isPlay = false
    private var litterStarList: ArrayList<AutoPlayEntity>? = null
    private val litterStartBreakShortTime: Long = 500
    private val litterStartBreakLongTime: Long = 1000

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_piano
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.hideDefaultControls(this)
//        LScreenUtil.hideNavigationBar(this)
        setupViews()
    }

    private fun setupViews() {
        pv.setSoundPollMaxStream(10)
        sb.thumbOffset = convertDpToPixel(seekbarOffsetSize).toInt()

        pv.setPianoListener(this)
        pv.setAutoPlayListener(this)
        pv.setLoadAudioListener(this)
        sb.setOnSeekBarChangeListener(this)
        ivRightArrow.setOnClickListener(this)
        ivLeftArrow.setOnClickListener(this)
        ivMusic.setOnClickListener(this)

        if (useConfigFile) {
            val assetManager = assets
            try {
                litterStarList = AutoPlayUtils.getAutoPlayEntityListByCustomConfigInputStream(
                    assetManager.open(configFileName)
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            initLitterStarList()
        }
    }

    private fun initLitterStarList() {
        litterStarList = java.util.ArrayList()
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                0,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                0,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                4,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                4,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                5,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                5,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                4,
                litterStartBreakLongTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                3,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                3,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                2,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                2,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                1,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                1,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                0,
                litterStartBreakLongTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                4,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                4,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                3,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                3,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                2,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                2,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                1,
                litterStartBreakLongTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                4,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                4,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                3,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                3,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                2,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                2,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                1,
                litterStartBreakLongTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                0,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                0,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                4,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                4,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                5,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                5,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                4,
                litterStartBreakLongTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                3,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                3,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                2,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                2,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                1,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                1,
                litterStartBreakShortTime
            )
        )
        litterStarList?.add(
            AutoPlayEntity(
                Piano.PianoKeyType.WHITE,
                4,
                0,
                litterStartBreakLongTime
            )
        )
    }

    override fun onPianoInitFinish() {}

    override fun onPianoClick(
        type: Piano.PianoKeyType?, voice: Piano.PianoVoice?, group: Int,
        positionOfGroup: Int
    ) {
    }

    override fun loadPianoAudioStart() {
        showShortInformation("loadPianoMusicStart")
    }

    override fun loadPianoAudioFinish() {
        showShortInformation("loadPianoMusicFinish")
    }

    override fun loadPianoAudioError(e: Exception?) {
        showShortInformation("loadPianoMusicError")
    }

    override fun loadPianoAudioProgress(progress: Int) {
        logD("progress:$progress")
    }

    override fun onProgressChanged(seekBar: SeekBar?, i: Int, b: Boolean) {
        pv?.scroll(i)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    override fun onResume() {
        if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        super.onResume()
    }

    override fun onClick(view: View) {
        if (scrollProgress == 0) {
            try {
                scrollProgress = pv.layoutWidth * 100 / pv.pianoWidth
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        var progress: Int
        when (view.id) {
            R.id.ivLeftArrow -> {
                if (scrollProgress == 0) {
                    progress = 0
                } else {
                    progress = sb.progress - scrollProgress
                    if (progress < 0) {
                        progress = 0
                    }
                }
                sb.progress = progress
            }
            R.id.ivRightArrow -> {
                if (scrollProgress == 0) {
                    progress = 100
                } else {
                    progress = sb.progress + scrollProgress
                    if (progress > 100) {
                        progress = 100
                    }
                }
                sb.progress = progress
            }
            R.id.ivMusic -> if (!isPlay) {
                pv.autoPlay(litterStarList)
            }
        }
    }

    @Suppress("unused")
    private fun convertDpToPixel(dp: Float): Float {
        val resources = this.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    override fun onPianoAutoPlayStart() {
        showShortInformation("onPianoAutoPlayStart")
    }

    override fun onPianoAutoPlayEnd() {
        showShortInformation("onPianoAutoPlayEnd")
        isPlay = false
    }

    override fun onDestroy() {
        super.onDestroy()
        pv.releaseAutoPlay()
    }
}
