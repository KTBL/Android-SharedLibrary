package de.ktbl.android.sharedlibrary.view

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData


@BindingAdapter("onFocus")
fun onFocusChange(text: EditText, listener: View.OnFocusChangeListener?) {
    text.onFocusChangeListener = listener
}

// TODO Now image's type is LiveData<Drawable?>?, but will this work if the content of livedata changes?!
@BindingAdapter(value = ["image", "placeholder"])
fun setImage(imageView: ImageView, drawableAsset: LiveData<Drawable?>?, placeholder: Drawable) {
    if (drawableAsset == null || drawableAsset.value == null) {
        imageView.setImageDrawable(placeholder)
    } else {
        imageView.setImageDrawable(drawableAsset.value)
    }
}
