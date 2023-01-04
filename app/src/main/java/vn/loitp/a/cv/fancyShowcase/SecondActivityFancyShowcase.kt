package vn.loitp.a.cv.fancyShowcase

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.a_fancy_showcase_second.*
import me.toptas.fancyshowcase.FancyShowCaseView
import vn.loitp.R

class SecondActivityFancyShowcase : BaseActivityFancyShowcase() {
    override fun setLayoutResourceId(): Int {
        return R.layout.a_fancy_showcase_second
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        focusOnButton()

        button1.setOnClickListener {
            focusOnButton()
        }

        button2.setOnClickListener {
            if (toolbar.visibility == View.VISIBLE) {
                toolbar.visibility = View.GONE
            } else {
                toolbar.visibility = View.VISIBLE
            }
        }
    }


    private fun focusOnButton() {
        FancyShowCaseView.Builder(this@SecondActivityFancyShowcase)
            .focusOn(button1)
            .title("Focus a view")
            .fitSystemWindows(true)
            .build()
            .show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_fancy_show_case, menu)
        return super.onCreateOptionsMenu(menu)
    }


    /**
     * Shows a FancyShowCaseView that focuses to ActionBar items
     *
     * @param item actionbar item to focus
     * @return true
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        FancyShowCaseView.Builder(this)
            .focusOn(findViewById(item.itemId))
            .title("Focus on Actionbar items")
            .fitSystemWindows(true)
            .build()
            .show()
        return true
    }
}
