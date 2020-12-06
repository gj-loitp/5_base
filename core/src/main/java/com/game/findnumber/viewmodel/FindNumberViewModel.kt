package com.game.findnumber.viewmodel

import com.annotation.LogTag
import com.core.helper.mup.girl.service.BaseGirlViewModel
import com.game.findnumber.db.FindNumberDatabase
import com.game.findnumber.model.Level
import com.service.livedata.ActionData
import com.service.livedata.ActionLiveData
import kotlinx.coroutines.launch

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

@LogTag("FindNumberViewModel")
class FindNumberViewModel : BaseGirlViewModel() {
    val listLevelActionLiveData: ActionLiveData<ActionData<ArrayList<Level>>> = ActionLiveData()

    fun getListLevelSingle() {
        listLevelActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {

            val listLevel = ArrayList<Level>()

            val listLevelDb = FindNumberDatabase.instance?.levelDao()?.getListLevel()
            if (listLevelDb.isNullOrEmpty()) {
                logD("listLevelDb.isNullOrEmpty()")
                //create first db

                for (i in 0 until 100) {
                    val level = Level()
                    level.id = System.nanoTime().toString()
                    level.name = "${i + 1}"
                    listLevel.add(element = level)
                }

                //save first data
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

}
