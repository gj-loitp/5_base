package vn.loitp.app.activity.customviews.dragview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tuanhav95.drag.DragView
import kotlinx.android.synthetic.main.activity_normal.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.dragview.fragment.BottomFragment
import vn.loitp.app.activity.customviews.dragview.fragment.NormalTopFragment

class NormalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal)

        dragView.setDragListener(object : DragView.DragListener {
            override fun onChangeState(state: DragView.State) {
            }

            override fun onChangePercent(percent: Float) {
                alpha.alpha = 1 - percent
            }

        })

        supportFragmentManager.beginTransaction().add(R.id.frameFirst, NormalTopFragment()).commit()
        supportFragmentManager.beginTransaction().add(R.id.frameSecond, BottomFragment()).commit()

        btnMax.setOnClickListener { dragView.maximize() }
        btnMin.setOnClickListener { dragView.minimize() }
        btnClose.setOnClickListener { dragView.close() }

    }
}
