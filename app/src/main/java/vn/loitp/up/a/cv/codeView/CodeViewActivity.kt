package vn.loitp.up.a.cv.codeView

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme
import vn.loitp.R
import vn.loitp.databinding.ACodeViewKbiakovBinding

@LogTag("CodeViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class CodeViewActivity : BaseActivityFont() {

    private lateinit var binding: ACodeViewKbiakovBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ACodeViewKbiakovBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/kbiakov/CodeView-Android"
                    )
                })
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = CodeViewActivity::class.java.simpleName
        }

//        binding.codeView.setCode(code())

        binding.codeView.setOptions(
            Options.Default.get(this)
//            .withLanguage("python")
                .withLanguage("java")
                .withCode(code())
                .withTheme(ColorTheme.MONOKAI)
        )
    }

    private fun code(): String {
        return "package vn.loitp.a.cv.codeView\n" +
                "\n" +
                "import android.os.Bundle\n" +
                "import androidx.core.view.isVisible\n" +
                "import com.loitp.annotation.IsAutoAnimation\n" +
                "import com.loitp.annotation.IsFullScreen\n" +
                "import com.loitp.annotation.LogTag\n" +
                "import com.loitp.core.base.BaseActivityFont\n" +
                "import com.loitp.core.common.NOT_FOUND\n" +
                "import com.loitp.core.ext.openUrlInBrowser\n" +
                "import com.loitp.core.ext.setSafeOnClickListenerElastic\n" +
                "import io.github.kbiakov.codeview.adapters.Options\n" +
                "import io.github.kbiakov.codeview.highlight.ColorTheme\n" +
                "import vn.loitp.R\n" +
                "import vn.loitp.databinding.ACodeViewKbiakovBinding\n" +
                "\n" +
                "@LogTag(\"CodeViewActivity\")\n" +
                "@IsFullScreen(false)\n" +
                "@IsAutoAnimation(false)\n" +
                "class CodeViewActivity : BaseActivityFont() {\n" +
                "\n" +
                "    private lateinit var binding: ACodeViewKbiakovBinding\n" +
                "\n" +
                "    override fun setLayoutResourceId(): Int {\n" +
                "        return NOT_FOUND\n" +
                "    }\n" +
                "\n" +
                "    override fun onCreate(savedInstanceState: Bundle?) {\n" +
                "        super.onCreate(savedInstanceState)\n" +
                "\n" +
                "        binding = ACodeViewKbiakovBinding.inflate(layoutInflater)\n" +
                "        setContentView(binding.root)\n" +
                "\n" +
                "        setupViews()\n" +
                "    }\n" +
                "\n" +
                "    private fun setupViews() {\n" +
                "        binding.lActionBar.apply {\n" +
                "            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {\n" +
                "                onBaseBackPressed()\n" +
                "            })\n" +
                "            this.ivIconRight?.apply {\n" +
                "                this.setSafeOnClickListenerElastic(runnable = {\n" +
                "                    context.openUrlInBrowser(\n" +
                "                        url = \"https://github.com/kbiakov/CodeView-Android\"\n" +
                "                    )\n" +
                "                })\n" +
                "                isVisible = true\n" +
                "                setImageResource(R.drawable.ic_baseline_code_48)\n" +
                "            }\n" +
                "            this.tvTitle?.text = CodeViewActivity::class.java.simpleName\n" +
                "        }\n" +
                "\n" +
                "        binding.codeView.setOptions(\n" +
                "            Options.Default.get(this)\n" +
                "//            .withLanguage(\"python\")\n" +
                "                .withLanguage(\"java\")\n" +
                "                .withCode(\"\")\n" +
                "                .withTheme(ColorTheme.MONOKAI)\n" +
                "        )\n" +
                "    }\n" +
                "\n" +
                "}\n"
    }
}
