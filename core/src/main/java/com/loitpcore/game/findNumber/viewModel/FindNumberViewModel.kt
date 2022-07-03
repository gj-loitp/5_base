package com.loitpcore.game.findNumber.viewModel

import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseViewModel
import com.loitpcore.game.findNumber.db.Db
import com.loitpcore.game.findNumber.db.Db.Companion.STATUS_LEVEL_OPEN
import com.loitpcore.game.findNumber.db.FindNumberDatabase
import com.loitpcore.game.findNumber.model.Level
import com.loitpcore.service.liveData.ActionData
import com.loitpcore.service.liveData.ActionLiveData
import kotlinx.coroutines.launch

@LogTag("FindNumberViewModel")
class FindNumberViewModel : BaseViewModel() {
    val listLevelActionLiveData: ActionLiveData<ActionData<ArrayList<Level>>> = ActionLiveData()
    val firstLevelOpenActionLiveData: ActionLiveData<ActionData<Level>> = ActionLiveData()

    fun getListLevelSingle() {
        listLevelActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {

            val listLevel = ArrayList<Level>()

            val listLevelDb = FindNumberDatabase.instance?.levelDao()?.getListLevel()
            if (listLevelDb.isNullOrEmpty()) {
                logD("listLevelDb.isNullOrEmpty()")
                // create first db

//                for (i in 0 until 99) {
//                    val level = Level()
//                    level.id = System.nanoTime().toString()
//                    level.name = i + 1
//
//                    if (i < 7) {
//                        level.status = Level.STATUS_LEVEL_WIN
//                    }
//
//                    listLevel.add(element = level)
//                }
                listLevel.addAll(Db.genListLevel())

                // save first data
                listLevel.forEach { lv ->
                    val id = FindNumberDatabase.instance?.levelDao()?.insert(lv)
                    logD("save first data id $id")
                }
            } else {
                logD("!listLevelDb.isNullOrEmpty()")
                listLevel.addAll(listLevelDb)
            }

            listLevelActionLiveData.post(
                ActionData(
                    isDoing = false,
                    isSuccess = true,
                    data = listLevel
                )
            )
        }
    }

    fun getFirstLevelOpen() {
        firstLevelOpenActionLiveData.set(ActionData(isDoing = true))
        ioScope.launch {
            val lastedLevel =
                FindNumberDatabase.instance?.levelDao()?.getFirstLevelOpen(STATUS_LEVEL_OPEN)
            firstLevelOpenActionLiveData.post(
                ActionData(
                    isDoing = false,
                    isSuccess = true,
                    data = lastedLevel
                )
            )
        }
    }
}
