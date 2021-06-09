package de.ktbl.android.sharedlibrary.livedata

import androidx.lifecycle.LiveData

/**
 * Extension function to replace [de.ktbl.android.sharedlibrary.livedata.LiveDataUtil.getValueOrDefault]
 * in the future.
 *
 * @param default default value to be returned in case the LiveData element or its value is null
 * @return default if LiveData or its value is null, else LiveData.value
 *
 */
fun <T> LiveData<T>?.valueOrDefault(default: T): T {
    return when {
        this == null       -> {
            default
        }
        this.value != null -> {
            this.value!!
        }
        else               -> {
            default
        }
    }
}