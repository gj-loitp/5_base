package vn.loitp.app.a.cv.code

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_0.*
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_code_view.*
import kotlinx.android.synthetic.main.bottom_sheet_dialog.*
import vn.loitp.R
import vn.loitp.app.a.cv.code.plugin.CommentManager
import vn.loitp.app.a.cv.code.plugin.SourcePositionListener
import vn.loitp.app.a.cv.code.plugin.UndoRedoManager
import vn.loitp.app.a.cv.code.syntax.LanguageManager
import vn.loitp.app.a.cv.code.syntax.LanguageName
import vn.loitp.app.a.cv.code.syntax.ThemeName
import java.util.*
import java.util.regex.Pattern

@LogTag("CodeViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class CodeViewActivity : BaseFontActivity() {

    private var languageManager: LanguageManager? = null
    private var commentManager: CommentManager? = null
    private var undoRedoManager: UndoRedoManager? = null

    private var currentLanguage: LanguageName = LanguageName.JAVA
    private var currentTheme: ThemeName = ThemeName.MONOKAI

    private val useModernAutoCompleteAdapter = true

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_code_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        configCodeView()
        configCodeViewPlugins()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/AmrDeveloper/CodeView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = CodeViewActivity::class.java.simpleName
        }
    }

    private fun configCodeView() {
        // Change default font to JetBrains Mono font
        val jetBrainsMono = ResourcesCompat.getFont(this, R.font.jetbrains_mono_medium)
        codeView.typeface = jetBrainsMono

        // Setup Line number feature
        codeView.setEnableLineNumber(true)
        codeView.setLineNumberTextColor(Color.GRAY)
        codeView.setLineNumberTextSize(25f)

        // Setup Auto indenting feature
        codeView.setTabLength(4)
        codeView.setEnableAutoIndentation(true)

        // Setup the language and theme with SyntaxManager helper class
        languageManager = LanguageManager(this, codeView)
        languageManager?.applyTheme(currentLanguage, currentTheme)

        // Setup auto pair complete
        val pairCompleteMap: MutableMap<Char, Char> = HashMap()
        pairCompleteMap['{'] = '}'
        pairCompleteMap['['] = ']'
        pairCompleteMap['('] = ')'
        pairCompleteMap['<'] = '>'
        pairCompleteMap['"'] = '"'
        pairCompleteMap['\''] = '\''
        codeView.setPairCompleteMap(pairCompleteMap)
        codeView.enablePairComplete(true)
        codeView.enablePairCompleteCenterCursor(true)

        // Setup the auto complete and auto indenting for the current language
        configLanguageAutoComplete()
        configLanguageAutoIndentation()
    }

    private fun configLanguageAutoComplete() {
        if (useModernAutoCompleteAdapter) {
            // Load the code list (keywords and snippets) for the current language
            val codeList = languageManager?.getLanguageCodeList(currentLanguage)

            // Use CodeViewAdapter or custom one
            val adapter = CustomCodeViewAdapter(this, codeList!!)

            // Add the odeViewAdapter to the CodeView
            codeView.setAdapter(adapter)
        } else {
            val languageKeywords = languageManager?.getLanguageKeywords(currentLanguage)

            // Custom list item xml layout
            val layoutId = R.layout.list_item_suggestion

            // TextView id to put suggestion on it
            val viewId = R.id.suggestItemTextView
            val adapter = ArrayAdapter(this, layoutId, viewId, languageKeywords!!)

            // Add the ArrayAdapter to the CodeView
            codeView.setAdapter(adapter)
        }
    }

    private fun configLanguageAutoIndentation() {
        codeView?.setIndentationStarts(
            languageManager?.getLanguageIndentationStarts(currentLanguage)
        )
        codeView?.setIndentationEnds(languageManager?.getLanguageIndentationEnds(currentLanguage))
    }

    private fun configCodeViewPlugins() {
        commentManager = CommentManager(codeView)
        configCommentInfo()
        undoRedoManager = UndoRedoManager(codeView)
        undoRedoManager?.connect()
        configLanguageName()
        source_position_txt.text = getString(R.string.source_position, 0, 0)
        configSourcePositionListener()
    }

    private fun configCommentInfo() {
        commentManager?.setCommentStart(languageManager?.getCommentStart(currentLanguage))
        commentManager?.setCommendEnd(languageManager?.getCommentEnd(currentLanguage))
    }

    private fun configLanguageName() {
        language_name_txt.text = currentLanguage.name.lowercase(Locale.getDefault())
    }

    private fun configSourcePositionListener() {
        val sourcePositionListener = SourcePositionListener(codeView)
        sourcePositionListener.setOnPositionChanged { line, column ->
            source_position_txt.text = getString(R.string.source_position, line, column)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_code_view, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuItemId = item.itemId
        val menuGroupId = item.groupId
        if (menuGroupId == R.id.group_languages) changeTheEditorLanguage(menuItemId)
        else if (menuGroupId == R.id.group_themes) changeTheEditorTheme(menuItemId)
        else if (menuItemId == R.id.findMenu) launchEditorButtonSheet()
        else if (menuItemId == R.id.comment) commentManager?.commentSelected()
        else if (menuItemId == R.id.un_comment) commentManager?.unCommentSelected()
        else if (menuItemId == R.id.clearText) codeView?.setText("")
        else if (menuItemId == R.id.undo) undoRedoManager?.undo()
        else if (menuItemId == R.id.redo) undoRedoManager?.redo()
        return super.onOptionsItemSelected(item)
    }

    private fun changeTheEditorLanguage(languageId: Int) {
        val oldLanguage = currentLanguage
        when (languageId) {
            R.id.language_java -> currentLanguage =
                LanguageName.JAVA
            R.id.language_python -> currentLanguage =
                LanguageName.PYTHON
            R.id.language_go -> currentLanguage =
                LanguageName.GO_LANG
        }
        if (currentLanguage !== oldLanguage) {
            languageManager?.applyTheme(currentLanguage, currentTheme)
            configLanguageName()
            configLanguageAutoComplete()
            configLanguageAutoIndentation()
            configCommentInfo()
        }
    }

    private fun changeTheEditorTheme(themeId: Int) {
        val oldTheme = currentTheme
        when (themeId) {
            R.id.theme_monokia -> currentTheme =
                ThemeName.MONOKAI
            R.id.theme_noctics -> currentTheme =
                ThemeName.NOCTIS_WHITE
            R.id.theme_five_color -> currentTheme =
                ThemeName.FIVE_COLOR
            R.id.theme_orange_box -> currentTheme =
                ThemeName.ORANGE_BOX
        }
        if (currentTheme !== oldTheme) {
            languageManager?.applyTheme(currentLanguage, currentTheme)
        }
    }

    private fun launchEditorButtonSheet() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottom_sheet_dialog)
        dialog.window?.setDimAmount(0f)
        search_edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                val text = editable.toString().trim { it <= ' ' }
                if (text.isEmpty()) codeView.clearMatches()
                codeView.findMatches(Pattern.quote(text))
            }
        })
        find_prev_action.setOnClickListener { codeView.findPrevMatch() }
        find_next_action.setOnClickListener { codeView.findNextMatch() }
        replace_action.setOnClickListener {
            val regex = search_edit.text.toString()
            val replacement = replacement_edit.text.toString()
            codeView.replaceAllMatches(regex, replacement)
        }
        dialog.setOnDismissListener { codeView.clearMatches() }
        dialog.show()
    }
}
