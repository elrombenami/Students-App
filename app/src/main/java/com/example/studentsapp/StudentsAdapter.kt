package com.example.studentsapp

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.databinding.ItemStudentBinding

class StudentsAdapter(
    private val students: List<Student>,
    private val onRowClicked: (Int) -> Unit
) : RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(val binding: ItemStudentBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        with(holder.binding) {
            // Setting a default image for all students
            studentImageView.setImageResource(R.drawable.user)

            // Displaying the student's name and ID
            nameTextView.setText(student.name)

            idTextView.text = "Student ID: ${student.id}"

            // Cancel previous CheckBox listener
            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = student.isChecked

            // CheckBox listener to save state
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                student.isChecked = isChecked
                PrefsHelper.saveStudents(checkBox.context, DataManager.students)
            }

            // Listener for changes to the student's name
            nameTextView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    student.name = s.toString()
                    PrefsHelper.saveStudents(root.context, DataManager.students)
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

            // Click on the whole row to view details
            root.setOnClickListener {
                onRowClicked(position)
            }
        }
    }

    override fun getItemCount(): Int = students.size
}