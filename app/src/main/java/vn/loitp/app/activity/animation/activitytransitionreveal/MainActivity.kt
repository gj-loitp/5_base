package vn.loitp.app.activity.animation.activitytransitionreveal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.tombayley.activitycircularreveal.CircularReveal
import kotlinx.android.synthetic.main.activity_main.*
import vn.loitp.app.R

class MainActivity : AppCompatActivity(),
        View.OnClickListener {

    companion object {
        const val REQUEST_CODE = 69
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab -> {
                val builder = CircularReveal.Builder(
                        this,
                        fab,
                        Intent(this, OtherActivity::class.java),
                        500
                ).apply {
                    revealColor = ContextCompat.getColor(
                            this@MainActivity,
                            R.color.colorPrimary
                    )
                    requestCode = REQUEST_CODE
                }

                CircularReveal.presentActivity(builder)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val msg = data?.getStringExtra(OtherActivity.KEY_DATA_RETURN)
            Log.d("loitpp", "onActivityResult REQUEST_CODE $REQUEST_CODE -> msg $msg")
        }
    }

}
