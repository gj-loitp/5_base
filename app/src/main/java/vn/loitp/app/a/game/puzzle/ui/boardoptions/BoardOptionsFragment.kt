package vn.loitp.app.a.game.puzzle.ui.boardoptions

import `in`.srain.cube.views.GridViewWithHeaderAndFooter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.loitp.core.utilities.LActivityUtil
import vn.loitp.R
import vn.loitp.app.a.game.puzzle.BoardActivityParams
import vn.loitp.app.a.game.puzzle.GameActivity

class ImageCardsAdapterGridView(
    private val parentContext: Context,
    private val cards: Array<TitledCardInfo>
) : BaseAdapter() {
    override fun getCount(): Int {
        return cards.size
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getItem(position: Int): Any {
        return cards[position]
    }

    @SuppressLint("InflateParams")
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var newView: View? = convertView
        if (newView == null) {
            val vi =
                parentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            newView = vi.inflate(R.layout.frm_titled_image_card, null)
            newView.tag = cards[position]
        }

        val tag: TitledCardInfo = newView?.tag as TitledCardInfo
        newView.findViewById<TextView>(R.id.title).text = tag.title
        newView.findViewById<ImageView>(R.id.image).setImageBitmap(tag.image)

        return newView
    }
}

class BoardOptionsFragment : androidx.fragment.app.Fragment() {
    companion object {
        fun newInstance() = BoardOptionsFragment()
    }

    private val viewModel: BoardOptionsViewModel? by lazy {
        activity?.let {
            ViewModelProviders.of(it)[BoardOptionsViewModel::class.java]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.frm_puzzle_board_options, container, false)
        (activity as AppCompatActivity).setSupportActionBar(
            view.findViewById(R.id.tbBoardOptions)
        )
        return view
    }

    @SuppressLint("InflateParams")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val context = this.context
        val board = view?.findViewById<GridViewWithHeaderAndFooter>(R.id.gvImages)

        if (context != null && board != null) {
            val layoutInflater = LayoutInflater.from(view?.context)
            board.addHeaderView(
                layoutInflater.inflate(R.layout.view_puzzle_board_options_grid_header, null)
            )

            board.adapter = ImageCardsAdapterGridView(
                parentContext = context,
                cards = BoardOptionsViewModel.PREDEFINED_IMAGES.map { (id, name) ->
                    TitledCardInfo(
                        BitmapFactory.decodeResource(resources, id),
                        name
                    )
                }.toTypedArray()
            )

            board.setOnItemClickListener { _, view, _, _ ->
                viewModel?.let {
                    it.boardImage.value = (view.tag as TitledCardInfo).image

                    GameActivity.initialConfig = BoardActivityParams(
                        bitmap = it.boardImage.value!!,
                        size = it.boardSize.value!!
                    )
                    startActivity(Intent(this.activity, GameActivity::class.java))
                    LActivityUtil.tranIn(this.activity)
                }
            }
        }
    }
}
