package renetik.android.material.extensions

import android.view.View
import com.google.android.material.slider.Slider
import renetik.android.view.extensions.findView

fun View.slider(id: Int) = findView<Slider>(id)!!

fun <T : Slider> T.onChange(listener: (T) -> Unit) = apply {
    addOnChangeListener { _, _, _ -> listener(this) }
}

fun <T : Slider> T.value(value: Float) = apply { this.value = value }

fun <T : Slider> T.value(value: Int) = apply { this.value = value.toFloat() }