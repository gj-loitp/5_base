package vn.loitp.app.activity.database.sqliteencryption

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import kotlinx.android.synthetic.main.activity_sqlite_encryption.*
import loitp.basemaster.R
import vn.loitp.app.app.LApplication

class SqliteEncryptionActivity : BaseFontActivity(), View.OnClickListener {
    private lateinit var db: BikeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = BikeDatabase(this)
        btAddBike.setOnClickListener(this)
        btClearAll.setOnClickListener(this)
        btGetBikeWithId.setOnClickListener(this)

        getAllBike();
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_sqlite_encryption
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btAddBike -> {
                addBike()
            }
            R.id.btClearAll -> {
                clearAllBike()
            }
            R.id.btGetBikeWithId -> {
                getBikeWithId(2)
            }
        }
    }

    private fun getAllBike() {
        val bikeList = db.allBike
        for (bike in bikeList) {
            addButtonByBike(bike)
        }
    }

    private fun addButtonByBike(bike: Bike) {
        val button = Button(activity)
        val text = LApplication.gson.toJson(bike)
        button.text = text
        button.isAllCaps = false
        button.setOnClickListener { v ->
            updateBike(bike, button)
        }
        button.setOnLongClickListener { v ->
            deleteBike(bike, button)
            true
        }
        ll.addView(button)
    }

    private fun addButtonById(idBike: Long) {
        val button = Button(activity)
        val bike = db.getBike(idBike)
        LLog.d(TAG, "addButton bike " + LApplication.gson.toJson(bike))
        if (bike != null) {
            val text = LApplication.gson.toJson(bike)
            button.text = text
            button.isAllCaps = false
            button.setOnClickListener { v ->
                updateBike(bike, button)
            }
            button.setOnLongClickListener { v ->
                deleteBike(bike, button)
                true
            }
            ll.addView(button)
        }
    }

    private fun addBike() {
        val size = db.bikeCount
        LLog.d(TAG, "size: $size")
        val bike = Bike()
        bike.name = "GSX " + (size + 1)
        bike.branch = "Suzuki " + (size + 1)
        bike.hp = size
        bike.price = size * 2
        bike.imgPath0 = "path0 " + System.currentTimeMillis()
        bike.imgPath1 = "path1 " + System.currentTimeMillis()
        bike.imgPath2 = "path2 " + System.currentTimeMillis()
        val idBike = db.addBike(bike)
        if (idBike != BikeDatabase.RESULT_FAILED) {
            addButtonById(idBike)
        }
    }

    private fun clearAllBike() {
        LLog.d(TAG, "clearAllContact")
        ll.removeAllViews()
        db.clearAllBike()
        getAllBike()
    }

    private fun getBikeWithId(id: Long) {
        val bike = db.getBike(id)
        if (bike == null) {
            showShort("Bike with ID=$id not found")
        } else {
            showShort("Found: " + LApplication.gson.toJson(bike))
        }
    }

    private fun updateBike(bike: Bike, button: Button) {
        bike.branch = "Ducati"
        bike.name = "Monster " + System.currentTimeMillis()
        val result = db.updateBike(bike)
        if (result == BikeDatabase.RESULT_SUCCESS) {
            val text = LApplication.gson.toJson(bike)
            button.text = text
        }
    }

    private fun deleteBike(bike: Bike, button: Button) {
        val result = db.deleteBike(bike)
        LLog.d(TAG, "deleteContact result $result")
        ll.removeView(button)
    }
}
