package de.ktbl.android.sharedlibrary.util

import de.ktbl.android.sharedlibrary.IllegalAppStateException

fun isNull(t: Any?): Boolean = (t == null)
fun isNotNull(t: Any?): Boolean = (t != null)

class Validator(val throws: Boolean = false) {
    fun eval(lambda: Validator.() -> Unit): Boolean {
        try {
            this.apply(lambda)
        } catch (e: IllegalAppStateException) {
            if (throws) {
                throw e
            } else {
                return false
            }
        }
        return true
    }

    fun isNull(t: Any?, msg: String = "") {
        if (t != null) throw IllegalAppStateException(msg)
    }

    fun isNotNull(t: Any?, msg: String = "") {
        if (t == null) throw IllegalAppStateException(msg)
    }

    fun isTrue(t: Boolean) {
        if (!t) throw IllegalAppStateException()
    }

    fun isFalse(t: Boolean) {
        if (t) throw IllegalAppStateException()
    }
}

fun validator(throws: Boolean, lambda: Validator.() -> Unit): Boolean = Validator(throws).eval(
        lambda)