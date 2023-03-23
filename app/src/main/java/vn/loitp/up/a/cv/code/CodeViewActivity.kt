package vn.loitp.up.a.cv.code

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ACodeViewBinding
import vn.loitp.up.a.cv.code.plugin.CommentManager
import vn.loitp.up.a.cv.code.plugin.SourcePositionListener
import vn.loitp.up.a.cv.code.plugin.UndoRedoManager
import vn.loitp.up.a.cv.code.syntax.LanguageManager
import vn.loitp.up.a.cv.code.syntax.LanguageName
import vn.loitp.up.a.cv.code.syntax.ThemeName
import java.util.*
import java.util.regex.Pattern

@LogTag("CodeViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class CodeViewActivity : BaseActivityFont() {

    private var languageManager: LanguageManager? = null
    private var commentManager: CommentManager? = null
    private var undoRedoManager: UndoRedoManager? = null

    private var currentLanguage: LanguageName = LanguageName.JAVA
    private var currentTheme: ThemeName = ThemeName.MONOKAI

    private val useModernAutoCompleteAdapter = true
    private lateinit var binding: ACodeViewBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ACodeViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        configCodeView()
        configCodeViewPlugins()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/AmrDeveloper/CodeView"
                    )
                })
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = CodeViewActivity::class.java.simpleName
        }
    }

    private fun configCodeView() {
        // Change default font to JetBrains Mono font
        val jetBrainsMono = ResourcesCompat.getFont(this, R.font.jetbrains_mono_medium)
        binding.codeView.typeface = jetBrainsMono

        // Setup Line number feature
        binding.codeView.setEnableLineNumber(true)
        binding.codeView.setLineNumberTextColor(Color.GRAY)
        binding.codeView.setLineNumberTextSize(25f)

        // Setup Auto indenting feature
        binding.codeView.setTabLength(4)
        binding.codeView.setEnableAutoIndentation(true)

        // Setup the language and theme with SyntaxManager helper class
        languageManager = LanguageManager(this, binding.codeView)
        languageManager?.applyTheme(currentLanguage, currentTheme)

        // Setup auto pair complete
        val pairCompleteMap: MutableMap<Char, Char> = HashMap()
        pairCompleteMap['{'] = '}'
        pairCompleteMap['['] = ']'
        pairCompleteMap['('] = ')'
        pairCompleteMap['<'] = '>'
        pairCompleteMap['"'] = '"'
        pairCompleteMap['\''] = '\''
        binding.codeView.setPairCompleteMap(pairCompleteMap)
        binding.codeView.enablePairComplete(true)
        binding.codeView.enablePairCompleteCenterCursor(true)

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
            binding.codeView.setAdapter(adapter)
        } else {
            val languageKeywords = languageManager?.getLanguageKeywords(currentLanguage)

            // Custom list item xml layout
            val layoutId = R.layout.list_item_suggestion

            // TextView id to put suggestion on it
            val viewId = R.id.suggestItemTextView
            val adapter = ArrayAdapter(this, layoutId, viewId, languageKeywords!!)

            // Add the ArrayAdapter to the CodeView
            binding.codeView.setAdapter(adapter)
        }
    }

    private fun configLanguageAutoIndentation() {
        binding.codeView?.setIndentationStarts(
            languageManager?.getLanguageIndentationStarts(currentLanguage)
        )
        binding.codeView?.setIndentationEnds(
            languageManager?.getLanguageIndentationEnds(
                currentLanguage
            )
        )
    }

    private fun configCodeViewPlugins() {
        commentManager = CommentManager(binding.codeView)
        configCommentInfo()
        undoRedoManager = UndoRedoManager(binding.codeView)
        undoRedoManager?.connect()
        configLanguageName()
        binding.sourcePositionTxt.text = getString(R.string.source_position, 0, 0)
        configSourcePositionListener()
    }

    private fun configCommentInfo() {
        commentManager?.setCommentStart(languageManager?.getCommentStart(currentLanguage))
        commentManager?.setCommendEnd(languageManager?.getCommentEnd(currentLanguage))
    }

    private fun configLanguageName() {
        binding.languageNameTxt.text = currentLanguage.name.lowercase(Locale.getDefault())
    }

    private fun configSourcePositionListener() {
        val sourcePositionListener = SourcePositionListener(binding.codeView)
        sourcePositionListener.setOnPositionChanged { line, column ->
            binding.sourcePositionTxt.text = getString(R.string.source_position, line, column)
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
        else if (menuItemId == R.id.clearText) binding.codeView?.setText("")
        else if (menuItemId == R.id.undo) undoRedoManager?.undo()
        else if (menuItemId == R.id.redo) undoRedoManager?.redo()
        return super.onOptionsItemSelected(item)
    }

    private fun changeTheEditorLanguage(languageId: Int) {
        val oldLanguage = currentLanguage
        when (languageId) {
            R.id.language_java -> currentLanguage = LanguageName.JAVA
            R.id.language_python -> currentLanguage = LanguageName.PYTHON
            R.id.language_go -> currentLanguage = LanguageName.GO_LANG
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
            R.id.theme_monokia -> currentTheme = ThemeName.MONOKAI
            R.id.theme_noctics -> currentTheme = ThemeName.NOCTIS_WHITE
            R.id.theme_five_color -> currentTheme = ThemeName.FIVE_COLOR
            R.id.theme_orange_box -> currentTheme = ThemeName.ORANGE_BOX
        }
        if (currentTheme !== oldTheme) {
            languageManager?.applyTheme(currentLanguage, currentTheme)
        }
    }

    private fun launchEditorButtonSheet() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottom_sheet_dialog)
        dialog.window?.setDimAmount(0f)
        val searchEdit = dialog.findViewById<EditText>(R.id.search_edit)
        val findPrevAction = dialog.findViewById<ImageButton>(R.id.find_prev_action)
        val findNextAction = dialog.findViewById<ImageButton>(R.id.find_next_action)
        val replaceAction = dialog.findViewById<ImageButton>(R.id.replace_action)
        val replacementEdit = dialog.findViewById<EditText>(R.id.replacement_edit)

        searchEdit?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                val text = editable.toString().trim { it <= ' ' }
                if (text.isEmpty()) binding.codeView.clearMatches()
                binding.codeView.findMatches(Pattern.quote(text))
            }
        })
        findPrevAction?.setOnClickListener { binding.codeView.findPrevMatch() }
        findNextAction?.setOnClickListener { binding.codeView.findNextMatch() }
        replaceAction?.setOnClickListener {
            val regex = searchEdit?.text.toString()
            val replacement = replacementEdit?.text.toString()
            binding.codeView.replaceAllMatches(regex, replacement)
        }
        dialog.setOnDismissListener { binding.codeView.clearMatches() }
        dialog.show()
    }
}
