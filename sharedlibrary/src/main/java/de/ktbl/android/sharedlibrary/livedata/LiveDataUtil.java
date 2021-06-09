package de.ktbl.android.sharedlibrary.livedata;

import androidx.lifecycle.LiveData;

public class LiveDataUtil {
    private LiveDataUtil() {
        // static stuff only
    }

    public static <T> T getValueOrDefault(LiveData<T> liveData, T defaultValue) {
        return liveData.getValue() == null ? defaultValue : liveData.getValue();
    }
}
