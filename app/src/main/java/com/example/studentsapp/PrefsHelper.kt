package com.example.studentsapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PrefsHelper {
    private const val PREFS_NAME = "students_prefs"
    private const val KEY_STUDENTS_JSON = "students_json"

    fun saveStudents(context: Context, students: List<Student>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val jsonString = gson.toJson(students)
        editor.putString(KEY_STUDENTS_JSON, jsonString)
        editor.apply()
    }

    fun loadStudents(context: Context): MutableList<Student> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonString = prefs.getString(KEY_STUDENTS_JSON, null)
        return if (jsonString != null) {
            val type = object : TypeToken<List<Student>>() {}.type
            gson.fromJson(jsonString, type)
        } else {
            mutableListOf()
        }
    }
}