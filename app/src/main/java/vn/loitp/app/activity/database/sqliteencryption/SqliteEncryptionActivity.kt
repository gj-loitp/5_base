package vn.loitp.app.activity.database.sqliteencryption

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

    private fun showProgress() {
        pb.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        pb.visibility = View.GONE
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return "TAG" + javaClass.simpleName
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
        LLog.d(TAG, "getAllBike")
        showProgress()
        compositeDisposable.add(
                Single.create<List<Bike>> {
                    val bikeList = db.allBike
                    if (bikeList.isNullOrEmpty()) {
                        it.onError(Throwable("bikeList isNullOrEmpty"))
                    } else {
                        it.onSuccess(bikeList)
                    }
                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    LLog.d(TAG, "getAllBike success " + it.size)
                                    for (bike in it) {
                                        addButtonByBike(bike)
                                    }
                                    hideProgress()
                                },
                                {
                                    LLog.e(TAG, "getAllBike failed: $it")
                                    hideProgress()
                                }
                        ))
    }

    private fun addButtonByBike(bike: Bike) {
        addButtonById(bike.id)
    }

    private fun addButtonById(idBike: Long) {
        val button = Button(activity)
        val bike = db.getBike(idBike)
        LLog.d(TAG, "addButton bike " + LApplication.gson.toJson(bike))
        if (bike != null) {
            LUIUtil.printBeautyJson(bike, button)
            button.isAllCaps = false
            button.gravity = Gravity.START
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
        showProgress()
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
        compositeDisposable.add(
                Single.create<Long> {
                    val id = db.addBike(bike)
                    if (id == BikeDatabase.RESULT_FAILED) {
                        it.onError(Throwable("id == BikeDatabase.RESULT_FAILED"))
                    } else {
                        it.onSuccess(id)
                    }
                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { idBike ->
                                    if (idBike != BikeDatabase.RESULT_FAILED) {
                                        addButtonById(idBike)
                                    }
                                    hideProgress()
                                },
                                { t ->
                                    LLog.e(TAG, "addBike failed: $t")
                                    hideProgress()
                                }
                        ))

    }

    private fun clearAllBike() {
        LLog.d(TAG, "clearAllContact")
        ll.removeAllViews()
        db.clearAllBike()
        getAllBike()
    }

    private fun getBikeWithId(id: Long) {
        showProgress()
        compositeDisposable.add(
                Single.create<Bike> {
                    val bike = db.getBike(id)
                    if (bike == null) {
                        it.onError(Throwable("Bike with ID=$id not found"))
                    } else {
                        it.onSuccess(bike)
                    }
                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { bike ->
                                    showShort("Found: " + LApplication.gson.toJson(bike))
                                    hideProgress()
                                },
                                { t ->
                                    LLog.e(TAG, "addBike failed: $t")
                                    showShort("addBike failed: $t")
                                    hideProgress()
                                }
                        ))
    }

    private fun updateBike(bike: Bike, button: Button) {
        bike.name = "Monster " + System.currentTimeMillis()
        bike.branch = "Ducati"
        bike.hp += 1
        bike.price += 2
        val result = db.updateBike(bike)
        if (result == BikeDatabase.RESULT_SUCCESS) {
            LUIUtil.printBeautyJson(bike, button)
        }
    }

    private fun deleteBike(bike: Bike, button: Button) {
        val result = db.deleteBike(bike)
        LLog.d(TAG, "deleteContact result $result")
        ll.removeView(button)
    }
}
