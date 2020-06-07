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

    private val LIST = Pair("LIST", "")
    private val MODE_NIGHT = Pair("MODE_NIGHT", 0)
    private val LANGUAGE = Pair("LANGUAGE", "")
    private val REMINDER = Pair("REMINDER", false)
    private val TIME = Pair("TIME", "")

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
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

    var modeNight: Int
        get() = preferences.getInt(
            MODE_NIGHT.first,
            MODE_NIGHT.second
        )
        set(value) = preferences.edit {
            it.putInt(MODE_NIGHT.first, value)
        }

    var language: String
        get() = preferences.getString(
            LANGUAGE.first,
            LANGUAGE.second
        ).orEmpty()
        set(value) = preferences.edit {
            it.putString(LANGUAGE.first, value)
        }

    var reminder: Boolean
        get() = preferences.getBoolean(
            REMINDER.first,
            REMINDER.second
        )
        set(value) = preferences.edit {
            it.putBoolean(REMINDER.first, value)
        }

    var time: String
        get() = preferences.getString(
            TIME.first,
            TIME.second
        ).orEmpty()
        set(value) = preferences.edit {
            it.putString(TIME.first, value)
        }
}