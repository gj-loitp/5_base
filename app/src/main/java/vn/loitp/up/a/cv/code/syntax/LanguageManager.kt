package vn.loitp.up.a.cv.code.syntax

import android.content.Context
import com.amrdeveloper.codeview.Code
import com.amrdeveloper.codeview.CodeView

class LanguageManager(private val context: Context, private val codeView: CodeView) {
    fun applyTheme(language: LanguageName, theme: ThemeName?) {
        when (theme) {
            ThemeName.MONOKAI -> applyMonokaiTheme(language)
            ThemeName.NOCTIS_WHITE -> applyNoctisWhiteTheme(language)
            ThemeName.FIVE_COLOR -> applyFiveColorsDarkTheme(language)
            ThemeName.ORANGE_BOX -> applyOrangeBoxTheme(language)
            else -> {}
        }
    }

    fun getLanguageKeywords(language: LanguageName?): Array<String> {
        return when (language) {
            LanguageName.JAVA -> JavaLanguage.getKeywords(context)
            LanguageName.PYTHON -> PythonLanguage.getKeywords(context)
            LanguageName.GO_LANG -> GoLanguage.getKeywords(context)
            else -> arrayOf()
        }
    }

    fun getLanguageCodeList(language: LanguageName?): List<Code> {
        return when (language) {
            LanguageName.JAVA -> JavaLanguage.getCodeList(context)
            LanguageName.PYTHON -> PythonLanguage.getCodeList(context)
            LanguageName.GO_LANG -> GoLanguage.getCodeList(context)
            else -> ArrayList()
        }
    }

    fun getLanguageIndentationStarts(language: LanguageName?): Set<Char> {
        return when (language) {
            LanguageName.JAVA -> JavaLanguage.getIndentationStarts()
            LanguageName.PYTHON -> PythonLanguage.getIndentationStarts()
            LanguageName.GO_LANG -> GoLanguage.getIndentationStarts()
            else -> HashSet()
        }
    }

    fun getLanguageIndentationEnds(language: LanguageName?): Set<Char> {
        return when (language) {
            LanguageName.JAVA -> JavaLanguage.getIndentationEnds()
            LanguageName.PYTHON -> PythonLanguage.getIndentationEnds()
            LanguageName.GO_LANG -> GoLanguage.getIndentationEnds()
            else -> HashSet()
        }
    }

    fun getCommentStart(language: LanguageName?): String {
        return when (language) {
            LanguageName.JAVA -> JavaLanguage.getCommentStart()
            LanguageName.PYTHON -> PythonLanguage.getCommentStart()
            LanguageName.GO_LANG -> GoLanguage.getCommentStart()
            else -> ""
        }
    }

    fun getCommentEnd(language: LanguageName?): String {
        return when (language) {
            LanguageName.JAVA -> JavaLanguage.getCommentEnd()
            LanguageName.PYTHON -> PythonLanguage.getCommentEnd()
            LanguageName.GO_LANG -> GoLanguage.getCommentEnd()
            else -> ""
        }
    }

    private fun applyMonokaiTheme(language: LanguageName) {
        when (language) {
            LanguageName.JAVA -> JavaLanguage.applyMonokaiTheme(context, codeView)
            LanguageName.PYTHON -> PythonLanguage.applyMonokaiTheme(context, codeView)
            LanguageName.GO_LANG -> GoLanguage.applyMonokaiTheme(context, codeView)
        }
    }

    private fun applyNoctisWhiteTheme(language: LanguageName) {
        when (language) {
            LanguageName.JAVA -> JavaLanguage.applyNoctisWhiteTheme(context, codeView)
            LanguageName.PYTHON -> PythonLanguage.applyNoctisWhiteTheme(context, codeView)
            LanguageName.GO_LANG -> GoLanguage.applyNoctisWhiteTheme(context, codeView)
        }
    }

    private fun applyFiveColorsDarkTheme(language: LanguageName) {
        when (language) {
            LanguageName.JAVA -> JavaLanguage.applyFiveColorsDarkTheme(context, codeView)
            LanguageName.PYTHON -> PythonLanguage.applyFiveColorsDarkTheme(context, codeView)
            LanguageName.GO_LANG -> GoLanguage.applyFiveColorsDarkTheme(context, codeView)
        }
    }

    private fun applyOrangeBoxTheme(language: LanguageName) {
        when (language) {
            LanguageName.JAVA -> JavaLanguage.applyOrangeBoxTheme(context, codeView)
            LanguageName.PYTHON -> PythonLanguage.applyOrangeBoxTheme(context, codeView)
            LanguageName.GO_LANG -> GoLanguage.applyOrangeBoxTheme(context, codeView)
        }
    }
}
