package br.com.test.horarioetec

import android.content.Context

class MyPreference(context: Context) {

    val PREFERENCE_NAME = "SharedPreference"
    val PREFERENCE_SELECTED_THEME = "SelectedTheme"
    val PREFERENCE_MESSAGE_DISPLAY = "MessageDisplay"

    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getThemeSelected(): Int {
        return preference.getInt(PREFERENCE_SELECTED_THEME, 2)
    }

    fun setThemeSelected(theme: Int) {
        val editor = preference.edit()
        editor.putInt(PREFERENCE_SELECTED_THEME, theme)
        editor.apply()
    }

    fun getMessageDisplay(): Boolean {
        return preference.getBoolean(PREFERENCE_MESSAGE_DISPLAY, true)
    }

    fun setMessageDisplay(display: Boolean) {
        val editor = preference.edit()
        editor.putBoolean(PREFERENCE_MESSAGE_DISPLAY, display)
        editor.apply()
    }

}