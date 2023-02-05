package vn.loitp.up.a.api.truyentranhtuan

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.*
import com.loitp.core.helper.ttt.model.comic.Comic
import com.loitp.core.helper.ttt.viewmodel.TTTViewModel
import vn.loitp.databinding.ATttApiFavListBinding

@LogTag("TTTAPIFavListActivity")
@IsFullScreen(false)
class TTTAPIFavListActivity : BaseActivityFont() {

    private lateinit var binding: ATttApiFavListBinding
    private var tttViewModel: TTTViewModel? = null

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATttApiFavListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModels()
        setupViews()

        tttViewModel?.getListComicFav()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = TTTAPIFavListActivity::class.java.simpleName
        }
        binding.btAdd.setSafeOnClickListener {
            val comic = Comic()
            comic.date = "29.07.2014"
            comic.url = "http://truyentuan.com/vuong-phong-loi-i/"
            comic.title = "Vương Phong Lôi I"
            comic.isFav = true
            tttViewModel?.favComic(comic = comic)
        }
        binding.btRemove.setSafeOnClickListener {
            val comic = Comic()
            comic.date = "29.07.2014"
            comic.url = "http://truyentuan.com/vuong-phong-loi-i/"
            comic.title = "Vương Phong Lôi I"
            comic.isFav = false
            tttViewModel?.unfavComic(comic = comic)
        }
    }

    private fun setupViewModels() {
        tttViewModel = getViewModel(TTTViewModel::class.java)
        tttViewModel?.let { vm ->
            vm.listComicFavActionLiveData.observe(this) { actionData ->
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess

                if (isDoing == true) {
                    binding.progressBar.showProgress()
                } else {
                    binding.progressBar.hideProgress()
                    if (isSuccess == true) {
                        val listComicFav = actionData.data
                        listComicFav?.let {
                            binding.textView.printBeautyJson(o = it)
                        }
                    }
                }
            }

            vm.favComicLiveData.observe(this) { actionData ->
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess

                if (isDoing == true) {
                    binding.progressBar.showProgress()
                } else {
                    binding.progressBar.hideProgress()
                    if (isSuccess == true) {
                        val id = actionData.data
                        showLongInformation("Add success with id $id")

                        tttViewModel?.getListComicFav()
                    }
                }
            }

            vm.unfavComicLiveData.observe(this) { actionData ->
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess

                if (isDoing == true) {
                    binding.progressBar.showProgress()
                } else {
                    binding.progressBar.hideProgress()
                    if (isSuccess == true) {
                        val comic = actionData.data
                        showLongInformation("Delete success ${comic?.title}")

                        tttViewModel?.getListComicFav()
                    }
                }
            }
        }
    }
}
