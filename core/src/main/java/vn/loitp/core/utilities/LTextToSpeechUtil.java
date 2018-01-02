package vn.loitp.core.utilities;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by Loitp on 5/6/2017.
 */

public class LTextToSpeechUtil implements TextToSpeech.OnInitListener {
    private final String TAG = getClass().getSimpleName();
    private TextToSpeech tts;
    private Context context;
    private static final LTextToSpeechUtil ourInstance = new LTextToSpeechUtil();

    public static LTextToSpeechUtil getInstance() {
        return ourInstance;
    }

    private LTextToSpeechUtil() {
    }

    public void setupTTS(Context context) {
        this.context = context;
        tts = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                LLog.d(TAG, "This Language is not supported");
            } else {
                //speakOut("Example");
            }
        } else {
            LLog.d("TTS", "Initilization Failed!");
        }
    }

    public void speakOut(String text) {
        if (tts == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void destroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
