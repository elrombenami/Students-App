package com.example.studentsapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.databinding.ActivityAddStudentBinding

class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1) Use the Toolbar as the ActionBar
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.title = "New Student"

        // 2) Enable the Up / Back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.newCancelButton.setOnClickListener {
            finish() // just close without saving
        }

        binding.newSaveButton.setOnClickListener {
            val newName = binding.newNameEditText.text.toString()
            val newId = binding.newIdEditText.text.toString()
            val newPhone = binding.newPhoneEditText.text.toString()
            val newAddress = binding.newAddressEditText.text.toString()
            val newChecked = binding.newCheckBox.isChecked

            // Clear previous error messages
            binding.newNameErrorText.text = ""
            binding.newIdErrorText.text = ""
            binding.newPhoneErrorText.text = ""
            binding.newAddressErrorText.text = ""

            var isValid = true

            // Check if fields are empty and show the error messages
            if (newName.isEmpty()) {
                binding.newNameErrorText.text = "Name is required."
                isValid = false
            }
            if (newId.isEmpty()) {
                binding.newIdErrorText.text = "ID is required."
                isValid = false
            }
            if (newPhone.isEmpty()) {
                binding.newPhoneErrorText.text = "Phone is required."
                isValid = false
            }
            if (newAddress.isEmpty()) {
                binding.newAddressErrorText.text = "Address is required."
                isValid = false
            }

            // If valid, save the data
            if (isValid) {
                DataManager.addStudent(
                    context = this,
                    name = newName,
                    id = newId,
                    phone = newPhone,
                    address = newAddress,
                    isChecked = newChecked
                )

                finish() // Close activity
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