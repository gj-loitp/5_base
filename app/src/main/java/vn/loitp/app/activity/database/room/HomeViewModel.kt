package vn.loitp.app.activity.database.room

import android.app.Application
import kotlinx.coroutines.launch
import vn.loitp.app.activity.api.coroutine.livedata.ActionData
import vn.loitp.app.activity.api.coroutine.livedata.ActionLiveData
import vn.loitp.app.activity.api.coroutine.viewmodel.BaseViewModel
import vn.loitp.app.activity.database.room.db.FNBDatabase
import vn.loitp.app.activity.database.room.model.Area
import vn.loitp.app.activity.database.room.model.FloorPlan
import vn.loitp.app.activity.database.room.model.Table

class HomeViewModel(application: Application) : BaseViewModel(application) {
    private val TAG = "loitpp" + javaClass.simpleName
    val saveFloorPlanActionLiveData: ActionLiveData<ActionData<ArrayList<FloorPlan>>> = ActionLiveData()
    val getFloorPlanActionLiveData: ActionLiveData<ActionData<List<FloorPlan>>> = ActionLiveData()

    private fun genListFloorPlan(fromId: Int, toId: Int): ArrayList<FloorPlan> {
        val listFloorPlan = ArrayList<FloorPlan>()

        for (i in fromId..toId) {
            val floorPlan = FloorPlan()

            floorPlan.id = i.toString()
            floorPlan.name = "Name $i"

            val listArea = ArrayList<Area>()
            for (j in 0..1) {
                val area = Area()

                area.id = "$j"
                area.name = "Name $j"

                val listTable = ArrayList<Table>()

                for (u in 0..1) {
                    val table = Table()

                    table.id = "$u"
                    table.name = "Name $u"

                    listTable.add(table)
                }

                area.tables = listTable

                listArea.add(area)
            }

            floorPlan.areas = listArea

            listFloorPlan.add(floorPlan)
        }
        return listFloorPlan
    }

    fun saveListFrom0To10() {
        saveFloorPlanActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {

            val listFloorPlan = genListFloorPlan(fromId = 0, toId = 10)
            if (listFloorPlan.isNotEmpty()) {
                FNBDatabase.instance?.floorPlanDao()?.insertListFloorPlanConflict(listFloorPlan)

                saveFloorPlanActionLiveData.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                data = listFloorPlan
                        )
                )
            } else {
                //handle error
                //floorPlanActionLiveData.postAction()
            }
        }
    }

    fun saveListFrom10To20() {
        saveFloorPlanActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {

            val listFloorPlan = genListFloorPlan(fromId = 10, toId = 20)
            if (listFloorPlan.isNotEmpty()) {
                FNBDatabase.instance?.floorPlanDao()?.insertListFloorPlanConflict(listFloorPlan)

                saveFloorPlanActionLiveData.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                data = listFloorPlan
                        )
                )
            } else {
                //handle error
                //floorPlanActionLiveData.postAction()
            }
        }
    }

    fun getList() {
        getFloorPlanActionLiveData.set(ActionData(isDoing = true))
        ioScope.launch {
            val listFloorPlan = FNBDatabase.instance?.floorPlanDao()?.getAllFloorPlan()
            getFloorPlanActionLiveData.post(
                    ActionData(
                            isDoing = false,
                            isSuccess = true,
                            data = listFloorPlan
                    )
            )
        }
    }
}
