package vn.loitp.app.activity.customviews.codeView.syntax;

import android.content.Context;

import com.amrdeveloper.codeview.Code;
import com.amrdeveloper.codeview.CodeView;
import com.amrdeveloper.codeview.Keyword;
import com.loitp.core.utilities.LAppResource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import kotlin.Suppress;
import vn.loitp.app.R;

public class JavaLanguage {

    //Language Keywords
    private static final Pattern PATTERN_KEYWORDS = Pattern.compile("\\b(abstract|boolean|break|byte|case|catch" +
            "|char|class|continue|default|do|double|else" +
            "|enum|extends|final|finally|float|for|if" +
            "|implements|import|instanceof|int|interface" +
            "|long|native|new|null|package|private|protected" +
            "|public|return|short|static|strictfp|super|switch" +
            "|synchronized|this|throw|transient|try|void|volatile|while)\\b");

    private static final Pattern PATTERN_BUILTINS = Pattern.compile("[,:;[->]{}()]");
    private static final Pattern PATTERN_SINGLE_LINE_COMMENT = Pattern.compile("//[^\\n]*");
    private static final Pattern PATTERN_MULTI_LINE_COMMENT = Pattern.compile("/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/");
    private static final Pattern PATTERN_ATTRIBUTE = Pattern.compile("\\.[a-zA-Z0-9_]+");
    private static final Pattern PATTERN_OPERATION = Pattern.compile(":|==|>|<|!=|>=|<=|->|=|>|<|%|-|-=|%=|\\+|\\-|\\-=|\\+=|\\^|\\&|\\|::|\\?|\\*");
    private static final Pattern PATTERN_GENERIC = Pattern.compile("<[a-zA-Z0-9,<>]+>");
    private static final Pattern PATTERN_ANNOTATION = Pattern.compile("@.[a-zA-Z0-9]+");
    private static final Pattern PATTERN_TODO_COMMENT = Pattern.compile("//TODO[^\n]*");
    private static final Pattern PATTERN_NUMBERS = Pattern.compile("\\b(\\d*[.]?\\d+)\\b");
    private static final Pattern PATTERN_CHAR = Pattern.compile("['](.*?)[']");
    private static final Pattern PATTERN_STRING = Pattern.compile("[\"](.*?)[\"]");
    private static final Pattern PATTERN_HEX = Pattern.compile("0x[0-9a-fA-F]+");

    @Suppress(names = "unused")
    public static void applyMonokaiTheme(Context context, CodeView codeView) {
        codeView.resetSyntaxPatternList();
        codeView.resetHighlighter();

//        Resources resources = context.getResources();

        //View Background
        codeView.setBackgroundColor(LAppResource.INSTANCE.getColor(R.color.monokia_pro_black));

        //Syntax Colors
        codeView.addSyntaxPattern(PATTERN_HEX, LAppResource.INSTANCE.getColor(R.color.monokia_pro_purple));
        codeView.addSyntaxPattern(PATTERN_CHAR, LAppResource.INSTANCE.getColor(R.color.monokia_pro_green));
        codeView.addSyntaxPattern(PATTERN_STRING, LAppResource.INSTANCE.getColor(R.color.monokia_pro_orange));
        codeView.addSyntaxPattern(PATTERN_NUMBERS, LAppResource.INSTANCE.getColor(R.color.monokia_pro_purple));
        codeView.addSyntaxPattern(PATTERN_KEYWORDS, LAppResource.INSTANCE.getColor(R.color.monokia_pro_pink));
        codeView.addSyntaxPattern(PATTERN_BUILTINS, LAppResource.INSTANCE.getColor(R.color.monokia_pro_white));
        codeView.addSyntaxPattern(PATTERN_SINGLE_LINE_COMMENT, LAppResource.INSTANCE.getColor(R.color.monokia_pro_grey));
        codeView.addSyntaxPattern(PATTERN_MULTI_LINE_COMMENT, LAppResource.INSTANCE.getColor(R.color.monokia_pro_grey));
        codeView.addSyntaxPattern(PATTERN_ANNOTATION, LAppResource.INSTANCE.getColor(R.color.monokia_pro_pink));
        codeView.addSyntaxPattern(PATTERN_ATTRIBUTE, LAppResource.INSTANCE.getColor(R.color.monokia_pro_sky));
        codeView.addSyntaxPattern(PATTERN_GENERIC, LAppResource.INSTANCE.getColor(R.color.monokia_pro_pink));
        codeView.addSyntaxPattern(PATTERN_OPERATION, LAppResource.INSTANCE.getColor(R.color.monokia_pro_pink));
        //Default Color
        codeView.setTextColor(LAppResource.INSTANCE.getColor(R.color.monokia_pro_white));

        codeView.addSyntaxPattern(PATTERN_TODO_COMMENT, LAppResource.INSTANCE.getColor(R.color.gold));

        codeView.reHighlightSyntax();
    }

    @Suppress(names = "unused")
    public static void applyNoctisWhiteTheme(Context context, CodeView codeView) {
        codeView.resetSyntaxPatternList();
        codeView.resetHighlighter();

//        Resources resources = context.getResources();

        //View Background
        codeView.setBackgroundColor(LAppResource.INSTANCE.getColor(R.color.noctis_white));

        //Syntax Colors
        codeView.addSyntaxPattern(PATTERN_HEX, LAppResource.INSTANCE.getColor(R.color.noctis_purple));
        codeView.addSyntaxPattern(PATTERN_CHAR, LAppResource.INSTANCE.getColor(R.color.noctis_green));
        codeView.addSyntaxPattern(PATTERN_STRING, LAppResource.INSTANCE.getColor(R.color.noctis_green));
        codeView.addSyntaxPattern(PATTERN_NUMBERS, LAppResource.INSTANCE.getColor(R.color.noctis_purple));
        codeView.addSyntaxPattern(PATTERN_KEYWORDS, LAppResource.INSTANCE.getColor(R.color.noctis_pink));
        codeView.addSyntaxPattern(PATTERN_BUILTINS, LAppResource.INSTANCE.getColor(R.color.noctis_dark_blue));
        codeView.addSyntaxPattern(PATTERN_SINGLE_LINE_COMMENT, LAppResource.INSTANCE.getColor(R.color.noctis_grey));
        codeView.addSyntaxPattern(PATTERN_MULTI_LINE_COMMENT, LAppResource.INSTANCE.getColor(R.color.noctis_grey));
        codeView.addSyntaxPattern(PATTERN_ANNOTATION, LAppResource.INSTANCE.getColor(R.color.monokia_pro_pink));
        codeView.addSyntaxPattern(PATTERN_ATTRIBUTE, LAppResource.INSTANCE.getColor(R.color.noctis_blue));
        codeView.addSyntaxPattern(PATTERN_GENERIC, LAppResource.INSTANCE.getColor(R.color.monokia_pro_pink));
        codeView.addSyntaxPattern(PATTERN_OPERATION, LAppResource.INSTANCE.getColor(R.color.monokia_pro_pink));

        //Default Color
        codeView.setTextColor(LAppResource.INSTANCE.getColor(R.color.noctis_orange));

        codeView.addSyntaxPattern(PATTERN_TODO_COMMENT, LAppResource.INSTANCE.getColor(R.color.gold));

        codeView.reHighlightSyntax();
    }

    @Suppress(names = "unused")
    public static void applyFiveColorsDarkTheme(Context context, CodeView codeView) {
        codeView.resetSyntaxPatternList();
        codeView.resetHighlighter();

//        Resources resources = context.getResources();

        //View Background
        codeView.setBackgroundColor(LAppResource.INSTANCE.getColor(R.color.five_dark_black));

        //Syntax Colors
        codeView.addSyntaxPattern(PATTERN_HEX, LAppResource.INSTANCE.getColor(R.color.five_dark_purple));
        codeView.addSyntaxPattern(PATTERN_CHAR, LAppResource.INSTANCE.getColor(R.color.five_dark_yellow));
        codeView.addSyntaxPattern(PATTERN_STRING, LAppResource.INSTANCE.getColor(R.color.five_dark_yellow));
        codeView.addSyntaxPattern(PATTERN_NUMBERS, LAppResource.INSTANCE.getColor(R.color.five_dark_purple));
        codeView.addSyntaxPattern(PATTERN_KEYWORDS, LAppResource.INSTANCE.getColor(R.color.five_dark_purple));
        codeView.addSyntaxPattern(PATTERN_BUILTINS, LAppResource.INSTANCE.getColor(R.color.five_dark_white));
        codeView.addSyntaxPattern(PATTERN_SINGLE_LINE_COMMENT, LAppResource.INSTANCE.getColor(R.color.five_dark_grey));
        codeView.addSyntaxPattern(PATTERN_MULTI_LINE_COMMENT, LAppResource.INSTANCE.getColor(R.color.five_dark_grey));
        codeView.addSyntaxPattern(PATTERN_ANNOTATION, LAppResource.INSTANCE.getColor(R.color.five_dark_purple));
        codeView.addSyntaxPattern(PATTERN_ATTRIBUTE, LAppResource.INSTANCE.getColor(R.color.five_dark_blue));
        codeView.addSyntaxPattern(PATTERN_GENERIC, LAppResource.INSTANCE.getColor(R.color.five_dark_purple));
        codeView.addSyntaxPattern(PATTERN_OPERATION, LAppResource.INSTANCE.getColor(R.color.five_dark_purple));

        //Default Color
        codeView.setTextColor(LAppResource.INSTANCE.getColor(R.color.five_dark_white));

        codeView.addSyntaxPattern(PATTERN_TODO_COMMENT, LAppResource.INSTANCE.getColor(R.color.gold));

        codeView.reHighlightSyntax();
    }

    @Suppress(names = "unused")
    public static void applyOrangeBoxTheme(Context context, CodeView codeView) {
        codeView.resetSyntaxPatternList();
        codeView.resetHighlighter();

//        Resources resources = context.getResources();

        //View Background
        codeView.setBackgroundColor(LAppResource.INSTANCE.getColor(R.color.orange_box_black));

        //Syntax Colors
        codeView.addSyntaxPattern(PATTERN_HEX, LAppResource.INSTANCE.getColor(R.color.gold));
        codeView.addSyntaxPattern(PATTERN_CHAR, LAppResource.INSTANCE.getColor(R.color.orange_box_orange2));
        codeView.addSyntaxPattern(PATTERN_STRING, LAppResource.INSTANCE.getColor(R.color.orange_box_orange2));
        codeView.addSyntaxPattern(PATTERN_NUMBERS, LAppResource.INSTANCE.getColor(R.color.five_dark_purple));
        codeView.addSyntaxPattern(PATTERN_KEYWORDS, LAppResource.INSTANCE.getColor(R.color.orange_box_orange1));
        codeView.addSyntaxPattern(PATTERN_BUILTINS, LAppResource.INSTANCE.getColor(R.color.orange_box_grey));
        codeView.addSyntaxPattern(PATTERN_SINGLE_LINE_COMMENT, LAppResource.INSTANCE.getColor(R.color.orange_box_dark_grey));
        codeView.addSyntaxPattern(PATTERN_MULTI_LINE_COMMENT, LAppResource.INSTANCE.getColor(R.color.orange_box_dark_grey));
        codeView.addSyntaxPattern(PATTERN_ANNOTATION, LAppResource.INSTANCE.getColor(R.color.orange_box_orange1));
        codeView.addSyntaxPattern(PATTERN_ATTRIBUTE, LAppResource.INSTANCE.getColor(R.color.orange_box_orange3));
        codeView.addSyntaxPattern(PATTERN_GENERIC, LAppResource.INSTANCE.getColor(R.color.orange_box_orange1));
        codeView.addSyntaxPattern(PATTERN_OPERATION, LAppResource.INSTANCE.getColor(R.color.gold));

        //Default Color
        codeView.setTextColor(LAppResource.INSTANCE.getColor(R.color.five_dark_white));

        codeView.addSyntaxPattern(PATTERN_TODO_COMMENT, LAppResource.INSTANCE.getColor(R.color.gold));

        codeView.reHighlightSyntax();
    }

    public static String[] getKeywords(Context context) {
        return context.getResources().getStringArray(R.array.java_keywords);
    }

    public static List<Code> getCodeList(Context context) {
        List<Code> codeList = new ArrayList<>();
        String[] keywords = getKeywords(context);
        for (String keyword : keywords) {
            codeList.add(new Keyword(keyword));
        }
        return codeList;
    }

    public static Set<Character> getIndentationStarts() {
        Set<Character> characterSet = new HashSet<>();
        characterSet.add('{');
        return characterSet;
    }

    public static Set<Character> getIndentationEnds() {
        Set<Character> characterSet = new HashSet<>();
        characterSet.add('}');
        return characterSet;
    }

    public static String getCommentStart() {
        return "//";
    }

    public static String getCommentEnd() {
        return "";
    }
}
