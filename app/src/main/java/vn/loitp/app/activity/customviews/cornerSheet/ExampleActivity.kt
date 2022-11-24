package vn.loitp.app.activity.customviews.cornerSheet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.cornerSheet.support.ShopActivity

class ExampleActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example_activity)

        findViewById<Button>(R.id.main).setOnClickListener {
            val intent = Intent(this, BehaviorSampleActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.support_sample).setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
        }
    }
}