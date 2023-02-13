package vn.loitp.up.a.api.truyentranhtuan

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.*
import com.loitp.core.helper.ttt.helper.ComicUtils
import com.loitp.core.helper.ttt.model.comictype.ComicType
import com.loitp.core.helper.ttt.viewmodel.TTTViewModel
import vn.loitp.databinding.ATttApiComicListBinding

@LogTag("TTTAPIComicListActivity")
@IsFullScreen(false)
class TTTAPIComicListActivity : BaseActivityFont() {
    private var tttViewModel: TTTViewModel? = null
    private var comicTypeList = ArrayList<ComicType>()
    private lateinit var binding: ATttApiComicListBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATttApiComicListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupViewModels()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = TTTAPIComicListActivity::class.java.simpleName
        }
        binding.progressBar.hideProgress()
        comicTypeList.addAll(ComicUtils.comicTypeList)

        binding.btSelect.setSafeOnClickListener {
            showDialogSelect()
        }
        binding.btTestQueuedMutableLiveData.setSafeOnClickListener {
            for (i in 0..10) {
                tttViewModel?.setStringQueued("Number $i")
            }
        }
    }

    private fun setupViewModels() {
        tttViewModel = getViewModel(TTTViewModel::class.java)
        tttViewModel?.let { vm ->
            vm.comicTypeLiveEvent.observe(this) { comicType ->
                logD(">>>comicTypeLiveEvent comicType ${comicType.url}")
                vm.getListComic(link = comicType.url)
            }
            vm.testStringQueuedMutableLiveData.observe(this) { s ->
                logD(">>>loitpp testStringQueuedMutableLiveData $s")
            }
            vm.listComicActionLiveData.observe(this) { actionData ->
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess

                if (isDoing == true) {
                    binding.progressBar.showProgress()
                } else {
                    binding.progressBar.hideProgress()
                    if (isSuccess == true) {
                        val listComic = actionData.data
                        listComic?.let {
                            binding.textView.printBeautyJson(o = it)
                        }
                    }
                }
            }
        }
    }

    private fun showDialogSelect() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Chọn thể loại:")
        val items = arrayOfNulls<String>(comicTypeList.size)
        for (i in comicTypeList.indices) {
            items[i] = comicTypeList[i].type
        }
        builder.setItems(items) { _: DialogInterface?, position: Int ->
            tttViewModel?.setComicType(comicTypeList[position])
        }
        val dialog = builder.create()
        dialog.show()
    }
}
