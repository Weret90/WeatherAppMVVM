package com.umbrella.weatherappmvvp.view

import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(text: String, actionText: String, action: (View) -> Unit){
    Snackbar.make(this, text, Snackbar.LENGTH_INDEFINITE)
        .setAction(actionText, action).show()
}

fun View.show(): View {
    if (visibility != View.VISIBLE){
        visibility = View.VISIBLE
    }
    return this
}

fun View.hide(): View {
    if (visibility != View.GONE){
        visibility = View.GONE
    }
    return this
}

fun View.showToast(text: String) {
    Toast.makeText(this.context, text, Toast.LENGTH_LONG).show()
}