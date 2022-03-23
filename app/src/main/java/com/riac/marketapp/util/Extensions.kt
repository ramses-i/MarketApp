package com.riac.marketapp.util

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren

fun <T1 : Any, T2 : Any, R : Any> safeLetAnd(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun notNull(vararg args: Any?, action: () -> Unit) = when (args.filterNotNull().size) {
    args.size -> action()
    else -> {
    }
}

fun View.visible(condition: () -> Boolean): Unit =
    run { visibility = if (condition()) View.VISIBLE else View.GONE }

fun View.showKeyboard() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() = (context.getSystemService(Context.INPUT_METHOD_SERVICE)
        as InputMethodManager).run { hideSoftInputFromWindow(windowToken, 0) }

fun Context.showToast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun EditText.addOnTextChangedListener(call: () -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            call.invoke()
        }
    })
}

public inline fun <T> Iterable<T>.findOrFirst(predicate: (T) -> Boolean): T? {
    return firstOrNull(predicate) ?: first()


}

fun String?.isNotNullOrEmpty(): Boolean {
    return !isNullOrEmpty()
}

fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun MutableList<String?>.firstOrEmpty(): String = this.firstOrNull()?.let {
    it
} ?: ""

fun List<Job>.cancelJobs(reason: String? = null) {
    forEach { coroutineJob ->
        coroutineJob.cancelChildren()
        coroutineJob.cancel(reason ?: "")
    }
}
