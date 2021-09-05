package vn.loitp.app.activity.customviews.dragview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import vn.loitp.app.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNormal.setOnClickListener { startActivity(Intent(this, NormalActivity::class.java)) }

        btnCustom.setOnClickListener { startActivity(Intent(this, CustomActivity::class.java)) }

        btnExoplayer.setOnClickListener { startActivity(Intent(this, ExoPlayerActivity::class.java)) }

    }
}
