package com.company.notes.transport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.notes.R
import com.company.notes.databinding.ActivityAdminAddTransportBinding
import com.google.firebase.database.*

class AdminAddTransport : AppCompatActivity() {
    private lateinit var binding: ActivityAdminAddTransportBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_transport)

        binding = ActivityAdminAddTransportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Success"
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Transport")

        binding.btnAddvehicle.setOnClickListener {
            val vehicleName = binding.etvehicleName.text.toString()
            val vehicleSuitedFor = binding.etvehicleSuitedFor.text.toString()
            val vehicleImageLink = binding.etvehicleImageLink.text.toString()
            val vehicleLink = binding.etvehicleLink.text.toString()
            val vehicleDescription = binding.etvehicleDescription.text.toString()

            if (vehicleName.isEmpty() || vehicleSuitedFor.isEmpty() || vehicleImageLink.isEmpty() || vehicleLink.isEmpty() || vehicleDescription.isEmpty()) {
                Toast.makeText(this, "Enter all fields first!!", Toast.LENGTH_SHORT).show()
            } else {
                val VehicleModel = VehicleModel(
                    vehicleName,
                    vehicleSuitedFor,
                    vehicleImageLink,
                    vehicleLink,
                    vehicleDescription
                )

                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        databaseReference.child(vehicleName).setValue(VehicleModel)
                        Toast.makeText(
                            this@AdminAddTransport,
                            "Vehicle Added Successfully !!!",
                            Toast.LENGTH_SHORT
                        ).show()


                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@AdminAddTransport,
                            "Error: $error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }

    }}



