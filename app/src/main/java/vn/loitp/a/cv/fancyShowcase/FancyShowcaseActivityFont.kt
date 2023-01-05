package vn.loitp.a.cv.fancyShowcase

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFancyShowcaseFont
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_fancy_showcase.*
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.FocusShape
import me.toptas.fancyshowcase.listener.DismissListener
import me.toptas.fancyshowcase.listener.OnViewInflateListener
import vn.loitp.R

@LogTag("FancyShowcaseActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class FancyShowcaseActivityFont : BaseActivityFancyShowcaseFont() {

    private var mFancyShowCaseView: FancyShowCaseView? = null

    private var mClickListener: View.OnClickListener = View.OnClickListener {
        mFancyShowCaseView?.hide()
        mFancyShowCaseView = null
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.a_fancy_showcase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.apply {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = this,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/faruktoptas/FancyShowCaseView"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = FancyShowcaseActivityFont::class.java.simpleName
        }

        btnSimple.setOnClickListener {
            FancyShowCaseView.Builder(this)
                .title("No Focus")
                .build()
                .show()
        }

        //Shows a FancyShowCaseView that focus on a vie
        btnFocus.setOnClickListener {
            FancyShowCaseView.Builder(this)
                .focusOn(it)
                .title("Circle Focus on View")
                .build()
                .show()
        }

        // Set title with spanned
        val spanned: Spanned = Html.fromHtml("<font color='#ff0000'>Spanned</font>")
        btnSpanned.setOnClickListener {
            FancyShowCaseView.Builder(this)
                .focusOn(it)
                .title(spanned)
                .enterAnimation(null)
                .exitAnimation(null)
                .enableAutoTextPosition()
                .build()
                .show()
        }

        // Set title size
        btnTitleSize.setOnClickListener {
            FancyShowCaseView.Builder(this)
                .focusOn(it)
                .title("Title size")
                .titleSize(48, TypedValue.COMPLEX_UNIT_SP)
                .build()
                .show()
        }

        // Set title typeface
        btnTitleTypeface.setOnClickListener {
            val typeface =
                ResourcesCompat.getFont(this, R.font.pacifico_regular)
            FancyShowCaseView.Builder(this)
                .focusOn(it)
                .title("Title typeface")
                .typeface(typeface)
                .build()
                .show()
        }

        //Shows a FancyShowCaseView with rounded rect focus shape
        btnRoundedRect.setOnClickListener {
            FancyShowCaseView.Builder(this)
                .focusOn(it)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .title("Focus on View")
                .build()
                .show()
        }

        //Shows a FancyShowCaseView that focus on a view
        btnFocusDismissOnFocusArea.setOnClickListener {
            if (FancyShowCaseView.isVisible(this)) {
                showShortInformation("Clickable button")
                FancyShowCaseView.hideCurrent(this)
            } else {
                FancyShowCaseView.Builder(this)
                    .focusOn(findViewById(R.id.btnFocusDismissOnFocusArea))
                    .enableTouchOnFocusedView(true)
                    .title("Focus on View \n(dismiss on focus area)")
                    .build()
                    .show()
            }
        }

        //Shows a FancyShowCaseView with rounded rect focus shape
        btnRoundedRectDismissOnFocusArea.setOnClickListener {
            if (FancyShowCaseView.isVisible(this)) {
                showShortInformation("Clickable button")
                FancyShowCaseView.hideCurrent(this)
            } else {
                FancyShowCaseView.Builder(this)
                    .focusOn(it)
                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                    .focusRectSizeFactor(1.5)
                    .roundRectRadius(90)
                    .enableTouchOnFocusedView(true)
                    .title("Focus on View \n(dismiss on focus area)")
                    .build()
                    .show()
            }
        }

        //Shows FancyShowCaseView with focusCircleRadiusFactor 1.5 and title gravity
        btnFocus2.setOnClickListener {
            FancyShowCaseView.Builder(this)
                .focusOn(it)
                .focusCircleRadiusFactor(1.5)
                .title("Focus on View with larger circle")
                .focusBorderColor(Color.GREEN)
                .titleStyle(0, Gravity.BOTTOM or Gravity.CENTER)
                .build()
                .show()
        }

        //Shows FancyShowCaseView at specific position (round rectangle shape)
        btnRectPosition.setOnClickListener {
            FancyShowCaseView.Builder(this)
                .title("Focus on larger view")
                .focusRectAtPosition(260, 85, 480, 80)
                .focusRectSizeFactor(1.5)
                .roundRectRadius(60)
                .dismissListener(object : DismissListener {
                    override fun onDismiss(id: String?) {

                    }

                    override fun onSkipped(id: String?) {

                    }
                })
                .build()
                .show()
        }

        //Shows a FancyShowCaseView that focuses on a larger view
        btnFocusRectColor.setOnClickListener {
            FancyShowCaseView.Builder(this)
                .focusOn(it)
                .title("Focus on larger view")
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(50)
                .focusBorderSize(5)
                .focusBorderColor(Color.RED)
                .titleStyle(0, Gravity.TOP)
                .build()
                .show()
        }

        //Shows a FancyShowCaseView that has dashed rectangle border
        btnFocusDashedRect.setOnClickListener {
            FancyShowCaseView.Builder(this)
                .focusOn(it)
                .title("Focus with dashed line")
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(50)
                .focusBorderSize(10)
                .focusDashedBorder(10.0f, 10.0f)
                .focusBorderColor(Color.RED)
                .titleStyle(0, Gravity.TOP)
                .build()
                .show()
        }

        //Shows a FancyShowCaseView that has dashed circle border
        btnFocusDashedCircle.setOnClickListener {
            FancyShowCaseView.Builder(this)
                .focusOn(it)
                .title("Focus with dashed line")
                .focusShape(FocusShape.CIRCLE)
                .roundRectRadius(50)
                .focusBorderSize(10)
                .focusDashedBorder(10.0f, 10.0f)
                .focusBorderColor(Color.RED)
                .titleStyle(0, Gravity.TOP)
                .build()
                .show()
        }

        //Shows a FancyShowCaseView with background color and title style
        btnBackgroundColor.setOnClickListener {
            FancyShowCaseView.Builder(this)
                .focusOn(it)
                .backgroundColor(Color.parseColor("#AAff0000"))
                .title("Background color and title style can be changed")
                .titleStyle(R.style.MyTitleStyle, Gravity.TOP or Gravity.CENTER)
                .build()
                .show()
        }

        //Shows a FancyShowCaseView with border color
        btnBorderColor.setOnClickListener {
            FancyShowCaseView.Builder(this)
                .focusOn(it)
                .title("Focus border color can be changed")
                .titleStyle(R.style.MyTitleStyle, Gravity.TOP or Gravity.CENTER)
                .focusBorderColor(Color.GREEN)
                .focusBorderSize(10)
                .build()
                .show()
        }

        //Shows a FancyShowCaseView with custom enter, exit animations
        btn_anim.setOnClickListener {
            val enterAnimation =
                AnimationUtils.loadAnimation(this, R.anim.slide_in_top_fancy_showcase)
            val exitAnimation =
                AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom_fancy_showcase)

            val fancyShowCaseView = FancyShowCaseView.Builder(this)
                .focusOn(it)
                .title("Custom enter and exit animations.")
                .enterAnimation(enterAnimation)
                .exitAnimation(exitAnimation)
                .build()
            fancyShowCaseView.show()
            exitAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }

                override fun onAnimationEnd(animation: Animation) {
                    fancyShowCaseView.removeView()
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
        }

        //Shows a FancyShowCaseView view custom view inflation
        btn_custom_view.setOnClickListener {
            mFancyShowCaseView = FancyShowCaseView.Builder(this)
                .focusOn(it)
                .enableTouchOnFocusedView(true)
                .customView(
                    R.layout.l_fancy_showcase_my_custom_view_arrow,
                    object : OnViewInflateListener {
                        override fun onViewInflated(view: View) {
                            val image =
                                (view as RelativeLayout).findViewById<ImageView>(R.id.ivCustomView)
                            val params = image.layoutParams as RelativeLayout.LayoutParams

                            image.post {
                                params.leftMargin =
                                    mFancyShowCaseView!!.focusCenterX - image.width / 2
                                params.topMargin =
                                    mFancyShowCaseView!!.focusCenterY - mFancyShowCaseView!!.focusHeight - image.height
                                image.layoutParams = params
                            }

                            view.findViewById<View>(R.id.btnAction1)
                                .setOnClickListener(mClickListener)
                        }
                    })
                .closeOnTouch(false)
                .build()
            mFancyShowCaseView?.show()

        }

        btn_custom_view2.setOnClickListener {
            launchActivity(AnimatedActivityFancyShowcaseFont::class.java)
        }

        btn_no_anim.setOnClickListener {
            mFancyShowCaseView = FancyShowCaseView.Builder(this)
                .focusOn(it)
                .disableFocusAnimation()
                .build()
            mFancyShowCaseView?.show()

        }

        btn_queue.setOnClickListener {
            launchActivity(QueueActivityFancyShowcaseFont::class.java)
        }

        btn_custom_queue.setOnClickListener {
            launchActivity(CustomQueueActivityFontFancyShowcase::class.java)
        }

        btn_another_activity.setOnClickListener {
            launchActivity(SecondActivityFancyShowcaseFont::class.java)
        }

        btn_recycler_view.setOnClickListener {
            launchActivity(RecyclerViewActivityFontFancyShowcase::class.java)
        }

        btn_scaled_view.setOnClickListener {
            FancyShowCaseView.Builder(this)
                .focusOn(it)
                .title("Focus on Scaled View")
                .build()
                .show()
        }

        btnFocusDelay.setOnClickListener {
            FancyShowCaseView.Builder(this)
                .title("Focus with delay")
                .focusOn(it)
                .delay(1000)
                .build()
                .show()
        }

        btnShowOnce.setOnClickListener {
            FancyShowCaseView.Builder(this)
                .focusOn(it)
                .title("Clean storage to see this again")
                .showOnce("id0")
                .build()
                .show()
        }
    }
}
