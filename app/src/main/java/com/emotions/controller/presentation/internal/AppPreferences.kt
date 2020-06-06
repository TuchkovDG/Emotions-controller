package com.emotions.controller.presentation.internal

import android.content.Context
import android.content.SharedPreferences
import com.emotions.controller.domain.model.EmotionItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AppPreferences(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("AppPreferencesName", Context.MODE_PRIVATE)
    private val gson = Gson()

    private val TEXT = Pair("TEXT", "")
    private val LIST = Pair("LIST", "")

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

    var list: ArrayList<EmotionItem>?
        get() = preferences.getString(
            LIST.first,
            LIST.second
        ).orEmpty().let {
            if (it.isNotEmpty()) {
                gson.fromJson(it, object : TypeToken<ArrayList<EmotionItem>>() {}.type)
            } else {
                null
            }
        }
        set(value) = preferences.edit {
            it.putString(LIST.first, gson.toJson(value))
        }
}