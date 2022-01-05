package vn.loitp.app.activity.database.sqliteencryption

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sqlite_encryption.*
import vn.loitp.app.R

@LogTag("SqliteEncryptionActivity")
@IsFullScreen(false)
class SqliteEncryptionActivity : BaseFontActivity(), View.OnClickListener {
    private lateinit var bikeDatabase: BikeDatabase

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_sqlite_encryption
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bikeDatabase = BikeDatabase(this)
        btAddBike.setOnClickListener(this)
        btClearAll.setOnClickListener(this)
        btGetBikeWithId.setOnClickListener(this)

        getAllBike()
    }

    private fun showProgress() {
        pb.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        pb.visibility = View.GONE
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
        logD("getAllBike")
        showProgress()
        compositeDisposable.add(
            Single.create<List<Bike>> {
                val bikeList = bikeDatabase.allBike
                if (bikeList.isNullOrEmpty()) {
                    it.onError(Throwable("bikeList isNullOrEmpty"))
                } else {
                    it.onSuccess(bikeList)
                }
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        logD("getAllBike success " + it.size)
                        for (bike in it) {
                            addButtonByBike(bike)
                        }
                        hideProgress()
                    },
                    {
                        logE("getAllBike failed: $it")
                        hideProgress()
                    }
                )
        )
    }

    private fun addButtonByBike(bike: Bike) {
        addButtonById(bike.id)
    }

    private fun addButtonById(idBike: Long?) {
        val button = Button(this)
        val bike = bikeDatabase.getBike(idBike)
        logD("addButton bike " + BaseApplication.gson.toJson(bike))
        if (bike != null) {
            LUIUtil.printBeautyJson(bike, button)
            button.isAllCaps = false
            button.gravity = Gravity.START
            button.setOnClickListener {
                updateBike(bike, button)
            }
            button.setOnLongClickListener {
                deleteBike(bike, button)
                true
            }
            ll.addView(button)
        }
    }

    private fun addBike() {
        showProgress()
        val size = bikeDatabase.bikeCount
        logD("size: $size")
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
                val id = bikeDatabase.addBike(bike)
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
                        logE("addBike failed: $t")
                        hideProgress()
                    }
                )
        )
    }

    private fun clearAllBike() {
        logD("clearAllContact")
        ll.removeAllViews()
        bikeDatabase.clearAllBike()
        getAllBike()
    }

    private fun getBikeWithId(id: Long) {
        showProgress()
        compositeDisposable.add(
            Single.create<Bike> {
                val bike = bikeDatabase.getBike(id)
                if (bike == null) {
                    it.onError(Throwable("Bike with ID=$id not found"))
                } else {
                    it.onSuccess(bike)
                }
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { bike ->
                        showShortInformation("Found: " + BaseApplication.gson.toJson(bike))
                        hideProgress()
                    },
                    { t ->
                        logE("addBike failed: $t")
                        showShortInformation("addBike failed: $t")
                        hideProgress()
                    }
                )
        )
    }

    private fun updateBike(bike: Bike, button: Button) {
        bike.name = "Monster " + System.currentTimeMillis()
        bike.branch = "Ducati"
        bike.hp = bike.hp?.plus(1)
        bike.price = bike.price?.plus(2)
        showProgress()
        compositeDisposable.add(
            Single.create<Long> {
                val id = bikeDatabase.updateBike(bike)
                if (id == BikeDatabase.RESULT_FAILED) {
                    it.onError(Throwable("updateBike id == BikeDatabase.RESULT_FAILED"))
                } else {
                    it.onSuccess(id)
                }
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        LUIUtil.printBeautyJson(bike, button)
                        hideProgress()
                    },
                    { t ->
                        logE("updateBike failed: $t")
                        showShortError("updateBike failed: $t")
                        hideProgress()
                    }
                )
        )
    }

    private fun deleteBike(bike: Bike, button: Button) {
        val result = bikeDatabase.deleteBike(bike)
        logD("deleteContact result $result")
        ll.removeView(button)
    }
}
