package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentsapp.databinding.ActivityStudentsListBinding

class StudentsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentsListBinding
    private lateinit var adapter: StudentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.title = "Students List"

        DataManager.init(context = this)

        // 4) Setup RecyclerView + Adapter
        adapter = StudentsAdapter(DataManager.students) { position ->
            // On row clicked -> go to StudentDetailsActivity
            val intent = Intent(this, StudentInfoActivity::class.java)
            intent.putExtra("student_position", position)
            startActivity(intent)
        }
        binding.studentsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.studentsRecyclerView.adapter = adapter

        // 5) Floating Action Button -> NewStudentActivity
        binding.addStudentFab.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // The list may have changed (new student added, or a student edited/deleted)
        // So refresh the adapter to show the current data
        adapter.notifyDataSetChanged()
    }
}