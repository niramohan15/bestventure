package com.company.notes.hotel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.notes.R
import com.company.notes.databinding.ActivityAdminAddHotelBinding
import com.google.firebase.database.*

class AdminAddHotel : AppCompatActivity() {
    private lateinit var binding: ActivityAdminAddHotelBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_hotel)

        binding = ActivityAdminAddHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Success"
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Hotel")

        binding.btnAddhotel.setOnClickListener {
            val hotelName = binding.ethotelName.text.toString()
            val hotelSuitedFor = binding.ethotelSuitedFor.text.toString()
            val hotelImageLink = binding.ethotelImageLink.text.toString()
            val hotelLink = binding.ethotelLink.text.toString()
            val hotelDescription = binding.ethotelDescription.text.toString()

            if (hotelName.isEmpty() || hotelSuitedFor.isEmpty() || hotelImageLink.isEmpty() || hotelLink.isEmpty() || hotelDescription.isEmpty()) {
                Toast.makeText(this, "Enter all fields first!!", Toast.LENGTH_SHORT).show()
            }
            else {
                val HotelRVModal = HotelModel(hotelName, hotelSuitedFor, hotelImageLink, hotelLink, hotelDescription)

                databaseReference.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        databaseReference.child(hotelName).setValue(HotelRVModal)
                        Toast.makeText(this@AdminAddHotel, "Hotel Added Successfully !!!", Toast.LENGTH_SHORT).show()

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@AdminAddHotel,
                            "Error: $error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }




    }
}