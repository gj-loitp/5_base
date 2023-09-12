package com.loitp.views.et.currency

import android.content.Context
import android.graphics.Rect
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.loitp.R
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class CurrencyEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.editTextStyle
) : TextInputEditText(context, attrs, defStyleAttr) {

    // private const val prefix = "VND "
    private var prefix = ""
    private var numberLength = 19
    private var decimalDigit = 3

    private val currencyTextWatcher = CurrencyTextWatcher(editText = this, prefix = prefix)

    var onTextChanged: ((String, String) -> Unit)? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CurrencyEditText)
        numberLength = typedArray.getInt(R.styleable.CurrencyEditText_numberLength, 19)
        typedArray.recycle()

        // this.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        this.inputType = InputType.TYPE_CLASS_NUMBER
        this.hint = prefix
        this.filters = arrayOf<InputFilter>(LengthFilter(numberLength))
    }

    fun setText(text: String) {
        currencyTextWatcher.format(text)
    }

    fun getTextNumber(): String {
        return this.text.toString().trim().replace(oldValue = ",", newValue = "")
    }

    override fun onFocusChanged(
        focused: Boolean,
        direction: Int,
        previouslyFocusedRect: Rect?
    ) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            addTextChangedListener(currencyTextWatcher)
        } else {
            removeTextChangedListener(currencyTextWatcher)
        }
        handleCaseCurrencyEmpty(focused)
    }

    /**
     * When currency empty <br></br>
     * + When focus EditText, set the default text = prefix (ex: VND) <br></br>
     * + When EditText lose focus, set the default text = "", EditText will display hint (ex:VND)
     */
    private fun handleCaseCurrencyEmpty(focused: Boolean) {
        if (focused) {
            if (text.toString().isEmpty()) {
                setText(prefix)
            }
        } else {
            if (text.toString() == prefix) {
                setText("")
            }
        }
    }

    inner class CurrencyTextWatcher internal constructor(
        private val editText: EditText,
        private val prefix: String
    ) : TextWatcher {
        private var previousNumber: String? = null
        private var integerFormatter: DecimalFormat =
            DecimalFormat("#,###.###", DecimalFormatSymbols(Locale.US))

        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) { // do nothing
        }

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) { // do nothing
        }

        override fun afterTextChanged(editable: Editable) {
            format(editable.toString())
        }

        fun format(str: String) {
            if (str.length < prefix.length) {
                editText.setText(prefix)
                editText.setSelection(prefix.length)
                return
            }
            if (str == prefix) {
                onTextChanged?.invoke(str, getTextNumber())
                return
            }
            // number this the string which not contain prefix and ,
            val number = str.replace(prefix, "").replace("[,]".toRegex(), "")
            // for prevent afterTextChanged recursive call
            if (number == previousNumber || number.isEmpty()) {
                return
            }
            previousNumber = number
            val formattedString = prefix + formatNumber(number)
            editText.removeTextChangedListener(this) // Remove listener
            editText.setText(formattedString)
            onTextChanged?.invoke(formattedString, getTextNumber())
            handleSelection()
            editText.addTextChangedListener(this) // Add back the listener
        }

        private fun formatNumber(number: String): String {
            return if (number.contains(".")) {
                formatDecimal(number)
            } else formatInteger(number)
        }

        private fun formatInteger(str: String): String {
            val parsed = BigDecimal(str)
            return integerFormatter.format(parsed)
        }

        private fun formatDecimal(str: String): String {
            if (str == ".") {
                return "."
            }
            val parsed = BigDecimal(str)
            // example pattern VND #,###.00
            val formatter = DecimalFormat(
                "#,###." + getDecimalPattern(str),
                DecimalFormatSymbols(Locale.US)
            )
            formatter.roundingMode = RoundingMode.DOWN
            return formatter.format(parsed)
        }

        /**
         * It will return suitable pattern for format decimal
         * For example: 10.2 -> return 0 | 10.23 -> return 00, | 10.235 -> return 000
         */
        private fun getDecimalPattern(str: String): String {
            val decimalCount = str.length - str.indexOf(".") - 1
            val decimalPattern = StringBuilder()
            var i = 0
            while (i < decimalCount && i < decimalDigit) {
                decimalPattern.append("0")
                i++
            }
            return decimalPattern.toString()
        }

        private fun handleSelection() {
            if (editText.text.length <= numberLength) {
                editText.setSelection(editText.text.length)
            } else {
                editText.setSelection(numberLength)
            }
        }
    }
}
