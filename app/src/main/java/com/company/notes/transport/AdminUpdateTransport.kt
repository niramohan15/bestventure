package com.company.notes.transport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.notes.R

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.company.notes.databinding.ActivityAdminUpdateTransportBinding
import com.google.firebase.database.*

class AdminUpdateTransport : AppCompatActivity() {
    private lateinit var binding: ActivityAdminUpdateTransportBinding

    private lateinit var vehicleID: String

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var vehicle: VehicleModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminUpdateTransportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vehicleID = intent.getStringExtra("transportID").toString()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Transport").child(vehicleID)

        fetchvehicleDetails()

        binding.btnCancel.setOnClickListener {
            val intent1 = Intent(this, AdminMainTransportActivity::class.java)
            intent1.putExtra("transportID", vehicleID)
            startActivity(intent1)
            finish()
        }



        binding.btnUpdatevehicle.setOnClickListener {
            val vehicleName = binding.etvehicleName.text.toString()
            val vehicleSuitedFor = binding.etvehicleSuitedFor.text.toString()
            val vehicleImageLink = binding.etvehicleImageLink.text.toString()
            val vehicleLink = binding.etvehiclePrice.text.toString()
            val vehicleDescription = binding.etvehicleDescription.text.toString()

            if (vehicleName.isEmpty() ||  vehicleSuitedFor.isEmpty() || vehicleImageLink.isEmpty() || vehicleLink.isEmpty() || vehicleDescription.isEmpty()) {
                Toast.makeText(this, "Enter all fields first!!", Toast.LENGTH_SHORT).show()
            } else {


                databaseReference  = firebaseDatabase.getReference("Transport").child(vehicleName)

                var hashMap : HashMap<String, String>
                        = HashMap<String, String> ()
                hashMap.put("vehicleDescription",vehicleDescription);
                hashMap.put("vehicleImageLink",vehicleImageLink);
                hashMap.put("vehicleName",vehicleName);
                hashMap.put("vehicleSuitedFor",vehicleSuitedFor);

                databaseReference.updateChildren(hashMap as Map<String, Any>);

                Toast.makeText(this, "Updated Succesfully", Toast.LENGTH_SHORT).show()




            }
        }
    }

    private fun fetchvehicleDetails() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(VehicleModel::class.java)?.let {
                    vehicle = it
                    val vehicleName = vehicle.vehicleName
                    val vehicleSuitedFor = vehicle.vehicleSuitedFor
                    val vehicleImageLink = vehicle.vehicleImageLink
                    val vehicleLink = vehicle.vehiclePrice
                    val vehicleDescription = vehicle.vehicleDescription

                    binding.etvehicleName.setText(vehicleName)
                    binding.etvehicleSuitedFor.setText(vehicleSuitedFor)
                    binding.etvehicleImageLink.setText(vehicleImageLink)
                    binding.etvehiclePrice.setText(vehicleLink)
                    binding.etvehicleDescription.setText(vehicleDescription)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AdminUpdateTransport, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent1 = Intent(this, AdminViewTransportDetails::class.java)
        intent1.putExtra("transportID", vehicleID)
        startActivity(intent1)
        finish()
    }
}