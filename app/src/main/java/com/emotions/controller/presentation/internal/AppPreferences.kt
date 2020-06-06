package com.emotions.controller.presentation.internal

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("AppPreferencesName", Context.MODE_PRIVATE)

    private val TEXT = Pair("TEXT", "")

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var text: String
        get() = preferences.getString(
            TEXT.first,
            TEXT.second
        ).orEmpty()
        set(value) = preferences.edit {
            it.putString(TEXT.first, value)
        }
}