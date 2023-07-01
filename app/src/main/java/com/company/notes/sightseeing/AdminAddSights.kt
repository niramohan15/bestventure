package com.company.notes.sightseeing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.notes.R
import com.company.notes.databinding.ActivityAdminAddSightsBinding
import com.google.firebase.database.*

class AdminAddSights : AppCompatActivity() {
    private lateinit var binding: ActivityAdminAddSightsBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_transport)

        binding = ActivityAdminAddSightsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Success"
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Sights")

        binding.btnAddsight.setOnClickListener {
            val sightName = binding.etsightName.text.toString()
            val sightSuitedFor = binding.etsightSuitedFor.text.toString()
            val sightImageLink = binding.etsightImageLink.text.toString()
            val sightLink = binding.etsightLink.text.toString()
            val sightDescription = binding.etsightDescription.text.toString()

            if (sightName.isEmpty() || sightSuitedFor.isEmpty() || sightImageLink.isEmpty() || sightLink.isEmpty() || sightDescription.isEmpty()) {
                Toast.makeText(this, "Enter all fields first!!", Toast.LENGTH_SHORT).show()
            } else {
                val SightModel = SightModel(
                    sightName,
                    sightSuitedFor,
                    sightImageLink,
                    sightLink,
                    sightDescription
                )

                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        databaseReference.child(sightName).setValue(SightModel)
                        Toast.makeText(
                            this@AdminAddSights,
                            "sight Added Successfully !!!",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@AdminAddSights,
                            "Error: $error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }

    }}



