package vn.loitp.app.activity.database.room

import android.app.Application
import kotlinx.coroutines.launch
import vn.loitp.app.activity.api.coroutine.livedata.ActionData
import vn.loitp.app.activity.api.coroutine.livedata.ActionLiveData
import vn.loitp.app.activity.api.coroutine.viewmodel.BaseViewModel

class HomeViewModel(application: Application) : BaseViewModel(application) {
    private val TAG = "loitpp" + javaClass.simpleName
    val saveFloorPlanActionLiveData: ActionLiveData<ActionData<ArrayList<FloorPlan>>> = ActionLiveData()
    val getFloorPlanActionLiveData: ActionLiveData<ActionData<List<FloorPlan>>> = ActionLiveData()

    fun saveList() {
        saveFloorPlanActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {
            val listFloorPlan = ArrayList<FloorPlan>()

            for (i in 0..3) {
                val floorPlan = FloorPlan()

                floorPlan.id = i.toString()
                floorPlan.name = "Name$i"

                val listArea = ArrayList<Area>()
                for (j in 0..5) {
                    val area = Area()

                    area.id = "$j"
                    area.name = "Name$j"

                    val listTable = ArrayList<Table>()

                    for (u in 0..10) {
                        val table = Table()

                        table.id = "$u"
                        table.name = "Name$u"

                        listTable.add(table)
                    }

                    area.tables = listTable

                    listArea.add(area)
                }

                floorPlan.areas = listArea

                listFloorPlan.add(floorPlan)
            }

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
