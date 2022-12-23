package vn.loitp.a.db.room

import com.loitp.core.base.BaseViewModel
import com.loitp.sv.liveData.ActionData
import com.loitp.sv.liveData.ActionLiveData
import kotlinx.coroutines.launch
import vn.loitp.a.db.room.db.FNBDatabase
import vn.loitp.a.db.room.md.Area
import vn.loitp.a.db.room.md.FloorPlan
import vn.loitp.a.db.room.md.Table

class HomeViewModel : BaseViewModel() {
    val saveFloorPlanActionLiveData: ActionLiveData<ActionData<ArrayList<FloorPlan>>> =
        ActionLiveData()
    val getFloorPlanActionLiveData: ActionLiveData<ActionData<List<FloorPlan>>> = ActionLiveData()
    val getByIndexFloorPlanActionLiveData: ActionLiveData<ActionData<List<FloorPlan>>> =
        ActionLiveData()
    val deleteFloorPlanActionLiveData: ActionLiveData<ActionData<FloorPlan>> = ActionLiveData()
    val updateFloorPlanActionLiveData: ActionLiveData<ActionData<FloorPlan>> = ActionLiveData()
    val deleteAllFloorPlanActionLiveData: ActionLiveData<ActionData<Boolean>> = ActionLiveData()
    val insertFloorPlanActionLiveData: ActionLiveData<ActionData<FloorPlan>> = ActionLiveData()
    val findFloorPlanActionLiveData: ActionLiveData<ActionData<FloorPlan>> = ActionLiveData()

    private fun genFloorPlan(
        id: String,
        name: String
    ): FloorPlan {
        val floorPlan = FloorPlan()

        floorPlan.id = id
        floorPlan.name = name

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

        return floorPlan
    }

    private fun genListFloorPlan(
        fromId: Int,
        toId: Int
    ): ArrayList<FloorPlan> {
        val listFloorPlan = ArrayList<FloorPlan>()
        for (i in fromId until toId) {
            listFloorPlan.add(genFloorPlan(id = "$i", name = "Name $i"))
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
                // handle error
                // floorPlanActionLiveData.postAction()
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
                // handle error
                // floorPlanActionLiveData.postAction()
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

    fun getListByIndex(
        fromIndex: Int,
        toIndex: Int
    ) {
        getByIndexFloorPlanActionLiveData.set(ActionData(isDoing = true))
        ioScope.launch {
            val offset = toIndex - fromIndex + 1
            val listFloorPlan = FNBDatabase.instance?.floorPlanDao()
                ?.getListFloorPlanByIndex(fromIndex = fromIndex, offset = offset)
            getByIndexFloorPlanActionLiveData.post(
                ActionData(
                    isDoing = false,
                    isSuccess = true,
                    data = listFloorPlan
                )
            )
        }
    }

    fun deleteFloorPlan(floorPlan: FloorPlan) {
        ioScope.launch {
            deleteFloorPlanActionLiveData.post(ActionData(isDoing = true))
            FNBDatabase.instance?.floorPlanDao()?.delete(floorPlan)
            deleteFloorPlanActionLiveData.post(ActionData(isDoing = false, data = floorPlan))
        }
    }

    fun updateFloorPlan(floorPlan: FloorPlan) {
        ioScope.launch {
            updateFloorPlanActionLiveData.post(ActionData(isDoing = true))
            FNBDatabase.instance?.floorPlanDao()?.update(floorPlan)
            updateFloorPlanActionLiveData.post(ActionData(isDoing = false, data = floorPlan))
        }
    }

    fun deleteAll() {
        ioScope.launch {
            deleteAllFloorPlanActionLiveData.post(ActionData(isDoing = true))
            FNBDatabase.instance?.floorPlanDao()?.deleteAll()
            deleteAllFloorPlanActionLiveData.post(ActionData(isDoing = false, data = true))
        }
    }

    fun insertFloorPlan() {
        ioScope.launch {
            insertFloorPlanActionLiveData.post(ActionData(isDoing = true))
            val id = System.currentTimeMillis()
            val floorPlan = genFloorPlan(id = id.toString(), name = "Name $id")
            FNBDatabase.instance?.floorPlanDao()?.insert(floorPlan)
            insertFloorPlanActionLiveData.post(ActionData(isDoing = false, data = floorPlan))
        }
    }

    fun findId(id: String) {
        ioScope.launch {
            findFloorPlanActionLiveData.post(ActionData(isDoing = true))
            val floorPlan = FNBDatabase.instance?.floorPlanDao()?.find(id = id)
            findFloorPlanActionLiveData.post(ActionData(isDoing = false, data = floorPlan))
        }
    }
}
