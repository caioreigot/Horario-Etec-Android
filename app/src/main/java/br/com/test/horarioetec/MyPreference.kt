package br.com.test.horarioetec

import android.content.Context

class MyPreference(context: Context) {

    val PREFERENCE_NAME = "SharedPreference"
    val PREFERENCE_SELECTED_THEME = "SelectedTheme"

    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getThemeSelected(): Int {
        return preference.getInt(PREFERENCE_SELECTED_THEME, 2)
    }

    fun setThemeSelected(theme: Int) {
        val editor = preference.edit()
        editor.putInt(PREFERENCE_SELECTED_THEME, theme)
        editor.apply()
    }

}