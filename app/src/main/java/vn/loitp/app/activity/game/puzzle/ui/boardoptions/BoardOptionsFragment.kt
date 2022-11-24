package vn.loitp.app.activity.game.puzzle.ui.boardoptions

import `in`.srain.cube.views.GridViewWithHeaderAndFooter
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
import vn.loitp.app.R
import vn.loitp.app.activity.game.puzzle.BoardActivityParams
import vn.loitp.app.activity.game.puzzle.GameActivity

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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var newView: View? = convertView
        if (newView == null) {
            val vi =
                parentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            newView = vi.inflate(R.layout.titled_image_card_fragment, null)
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
            ViewModelProviders.of(it).get(BoardOptionsViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.board_options_fragment, container, false)
        (activity as AppCompatActivity).setSupportActionBar(
            view.findViewById(R.id.board_options_toolbar)
        )
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val context = this.context
        val board = view?.findViewById<GridViewWithHeaderAndFooter>(R.id.images_grid)

        if (context != null && board != null) {
            val layoutInflater = LayoutInflater.from(view?.context)
            board.addHeaderView(
                layoutInflater.inflate(R.layout.board_options_grid_header, null)
            )

            board.adapter = ImageCardsAdapterGridView(
                context,
                BoardOptionsViewModel.PREDEFINED_IMAGES.map { (id, name) ->
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
                        it.boardImage.value!!,
                        it.boardSize.value!!
                    )
                    startActivity(
                        Intent(this.activity, GameActivity::class.java)
                    )
                }
            }
        }
    }
}
