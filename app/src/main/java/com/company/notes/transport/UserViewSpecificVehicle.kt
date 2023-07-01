package com.company.notes.transport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.notes.R
import com.company.notes.databinding.ActivityUserViewSpecificVehicleBinding
import com.company.notes.payment.AddPayment
import com.company.notes.transport.VehicleModel

import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class UserViewSpecificVehicle : AppCompatActivity() {
    lateinit var binding: ActivityUserViewSpecificVehicleBinding

    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var vehicle: VehicleModel

    lateinit var vehicleID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserViewSpecificVehicleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vehicleID = intent.getStringExtra("transportID").toString()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Transport").child(vehicleID)

        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(VehicleModel::class.java)?.let {
                    vehicle = it
                    Picasso.get().load(vehicle.vehicleImageLink.toString()).into(binding.ivvehicleImage)
//                    binding.tvPrice.text = vehicle.vehiclePrice.toString()
                    binding.tvvehicleTitle.text = vehicle.vehicleName.toString()
                    binding.tvvehicleDescription.text = vehicle.vehicleDescription.toString()

                    val suitedFor = "Suited For: ${vehicle.vehicleSuitedFor.toString()}"
                    binding.tvSuitedFor.text = suitedFor

                    val price = "Amount $ : ${vehicle.vehiclePrice.toString()}"
                    binding.tvPrice.text = price
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, error.message, Toast.LENGTH_SHORT).show()
            }
        })

        binding.btncancelvehicle.setOnClickListener {
            val intent = Intent(this, UserViewVehicle::class.java)
            intent.putExtra("transportID", vehicleID)
            startActivity(intent)
            finish()
        }

        //! remaining
        binding.btnpaymentvehicle.setOnClickListener {
            val intent = Intent(this, AddPayment::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, UserViewVehicle::class.java))
        finish()
    }
}