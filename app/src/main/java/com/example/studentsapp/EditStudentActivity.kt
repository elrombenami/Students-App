package com.example.studentsapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.databinding.ActivityEditStudentBinding

class EditStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditStudentBinding
    private var studentPosition: Int = -1
    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1) Use the Toolbar as the ActionBar
        setSupportActionBar(binding.myToolbar)

        // 2) Set the screen title
        supportActionBar?.title = "Edit Student"

        // 3) Enable the Up / Back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get the position passed from wherever you started this activity
        studentPosition = intent.getIntExtra("student_position", -1)
        if (studentPosition == -1) {
            // If we can't find a valid position, just close
            finish()
            return
        }

        // Grab the existing student from DataManager
        student = DataManager.students[studentPosition]

        // Populate the UI fields
        binding.editImageView.setImageResource(R.drawable.user)
        binding.editNameEditText.setText(student.name)
        binding.editIdEditText.setText(student.id)
        binding.editPhoneEditText.setText(student.phone)
        binding.editAddressEditText.setText(student.address)
        binding.editCheckBox.isChecked = student.isChecked

        // Cancel
        binding.cancelButton.setOnClickListener {
            finish()
        }

        // Delete
        binding.deleteButton.setOnClickListener {
            // Display an alert dialog before deleting
            val alertDialog = androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Delete Student")
                .setMessage("Are you sure you want to delete this student?")
                .setPositiveButton("Delete") { _, _ ->
                    // Perform deletion if confirmed
                    if (studentPosition in DataManager.students.indices) {
                        DataManager.students.removeAt(studentPosition)
                        // Save the updated list to JSON
                        PrefsHelper.saveStudents(this, DataManager.students)
                        finish()
                    } else {
                        Toast.makeText(this, "Invalid student position", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
                .create()

            alertDialog.show()
        }

        // Save (update)
        binding.saveButton.setOnClickListener {
            // Clear previous error messages
            binding.editNameErrorText.text = ""
            binding.editIdErrorText.text = ""
            binding.editPhoneErrorText.text = ""
            binding.editAddressErrorText.text = ""

            // Get the input values
            val newName = binding.editNameEditText.text.toString()
            val newId = binding.editIdEditText.text.toString()
            val newPhone = binding.editPhoneEditText.text.toString()
            val newAddress = binding.editAddressEditText.text.toString()

            var isValid = true

            // Check if fields are empty and show the error messages
            if (newName.isEmpty()) {
                binding.editNameErrorText.text = "Name is required."
                isValid = false
            }
            if (newId.isEmpty()) {
                binding.editIdErrorText.text = "ID is required."
                isValid = false
            }
            if (newPhone.isEmpty()) {
                binding.editPhoneErrorText.text = "Phone is required."
                isValid = false
            }
            if (newAddress.isEmpty()) {
                binding.editAddressErrorText.text = "Address is required."
                isValid = false
            }

            // If valid, save the updated student data
            if (isValid) {
                student.name = newName
                student.id = newId
                student.phone = newPhone
                student.address = newAddress
                student.isChecked = binding.editCheckBox.isChecked

                // Save the updated list to JSON
                PrefsHelper.saveStudents(this, DataManager.students)

                finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // This is the Back arrow in the toolbar
            finish() // or onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}