import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class BrazilianPhoneNumberFormatter(private val editText: EditText) : TextWatcher {
    private var isUpdating: Boolean = false
    private var oldText: String = ""

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isUpdating) return
        val str = s.toString().filter { it.isDigit() }

        isUpdating = true
        val formatted = when {

            str.length == 11 -> {
                val ddd = str.substring(0, 2)
                val firstPart = str.substring(2, 7)
                val secondPart = str.substring(7)
                "($ddd) $firstPart-$secondPart"
            }

            str.length == 10 -> {
                val ddd = str.substring(0, 2)
                val firstPart = str.substring(2, 6)
                val secondPart = str.substring(6)
                "($ddd) $firstPart-$secondPart"
            }
            else -> {
                when {
                    str.length <= 2 -> str
                    str.length <= 6 -> {
                        val ddd = str.substring(0, 2)
                        val remaining = str.substring(2)
                        "($ddd) $remaining"
                    }
                    else -> {
                        val ddd = str.substring(0, 2)
                        val firstPart = str.substring(2, minOf(str.length, 6))
                        val secondPart = str.substring(minOf(str.length, 6))
                        if (str.length <= 10) {
                            "($ddd) $firstPart-$secondPart"
                        } else {
                            "($ddd) $firstPart-$secondPart"
                        }
                    }
                }
            }
        }

        editText.setText(formatted)
        editText.setSelection(formatted.length)
        isUpdating = false
    }
}
