package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.databinding.ActivityStudentInfoBinding

class StudentInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentInfoBinding
    private var studentPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1) Use the Toolbar as the ActionBar
        setSupportActionBar(binding.myToolbar)

        // 2) Set the screen title
        supportActionBar?.title = "Student Details"

        // 3) Enable the Up / Back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get the position passed from StudentsListActivity
        studentPosition = intent.getIntExtra("student_position", -1)

        // If position is invalid, close immediately
        if (studentPosition == -1 || studentPosition !in DataManager.students.indices) {
            finish()
            return
        }

        // Populate UI with the selected student's data
        val student = DataManager.students[studentPosition]
        binding.editImageView.setImageResource(R.drawable.user)
        binding.detailsNameTextView.text = "Name: ${student.name}"
        binding.detailsIdTextView.text = "ID: ${student.id}"
        binding.detailsPhoneTextView.text = "Phone: ${student.phone}"
        binding.detailsAddressTextView.text = "Address: ${student.address}"
        binding.detailsCheckBox.isChecked = student.isChecked

        // Edit button -> go to EditStudentActivity
        binding.editButton.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("student_position", studentPosition)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        if (studentPosition !in DataManager.students.indices) {
            finish()
            return
        }

        val student = DataManager.students[studentPosition]
        binding.detailsNameTextView.text = "name: ${student.name}"
        binding.detailsIdTextView.text = "id: ${student.id}"
        binding.detailsPhoneTextView.text = "phone: ${student.phone}"
        binding.detailsAddressTextView.text = "address: ${student.address}"
        binding.detailsCheckBox.isChecked = student.isChecked
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // This is the Back arrow in the toolbar
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}