package com.example.studentsapp

/*object DataManager{

    val students = mutableListOf<Student>()

    // A helper function to generate some sample data,
    // if you want to start the app with a few items
    fun generateSampleData() {
        students.add(Student("Rachel", "88", "0501111111", "Tel Aviv", true))
        students.add(Student("David",   "89", "0502222222", "Hadera",    true))
        students.add(Student("Israel",  "90", "0503333333", "Rishon...", true))
    }
}*/

import android.content.Context

object DataManager {
    var students = mutableListOf<Student>()

    fun init(context: Context) {
        // Load the student list from SharedPreferences
        students = PrefsHelper.loadStudents(context)
    }

    fun addStudent(context: Context, name: String, id: String, phone: String, address: String, isChecked: Boolean) {
        students.add(Student(name, id, phone, address, isChecked))
        // Save after adding
        PrefsHelper.saveStudents(context, students)
    }
}