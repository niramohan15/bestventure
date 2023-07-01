package com.company.notes.hotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.company.notes.databinding.ActivityAdminUpdateBinding
import com.google.firebase.database.*

class AdminUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminUpdateBinding

    private lateinit var hotelID: String

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var hotel: HotelModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hotelID = intent.getStringExtra("hotelID").toString()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Hotel").child(hotelID)

        fetchhotelDetails()

        binding.btnCancel.setOnClickListener {
            val intent1 = Intent(this, AdminMainActivity::class.java)
            intent1.putExtra("hotelID", hotelID)
            startActivity(intent1)
            finish()
        }



        binding.btnUpdatehotel.setOnClickListener {
            val hotelName = binding.ethotelName.text.toString()
            val hotelSuitedFor = binding.ethotelSuitedFor.text.toString()
            val hotelImageLink = binding.ethotelImageLink.text.toString()
            val hotelPrice= binding.ethotelPrice.text.toString()
            val hotelDescription = binding.ethotelDescription.text.toString()

            if (hotelName.isEmpty() ||  hotelSuitedFor.isEmpty() || hotelImageLink.isEmpty() || hotelPrice.isEmpty() || hotelDescription.isEmpty()) {
                Toast.makeText(this, "Enter all fields first!!", Toast.LENGTH_SHORT).show()
            } else {


                databaseReference  = firebaseDatabase.getReference("Hotel").child(hotelName)

                var hashMap : HashMap<String, String>
                        = HashMap<String, String> ()
                hashMap.put("hotelDescription",hotelDescription);
                hashMap.put("hotelImageLink",hotelImageLink);
                hashMap.put("hotelPrice",hotelPrice);
                hashMap.put("hotelName",hotelName);
                hashMap.put("hotelSuitedFor",hotelSuitedFor);
                databaseReference.updateChildren(hashMap as Map<String, Any>);
                Toast.makeText(this, "Updated Succesfully", Toast.LENGTH_SHORT).show()






//                binding.progressBar.visibility = View.VISIBLE
//                databaseReference.removeValue()
//
//                val hotel = hotelRVModel(
//                    hotelName,
//                    hotelSuitedFor,
//                    hotelImageLink,
//                    hotelLink,
//                    hotelDescription
//                )
//                databaseReference = firebaseDatabase.getReference("hotel").child(hotelName)
//                Handler(Looper.getMainLooper()).postDelayed({
//                    databaseReference.setValue(hotel)
//                    val intent = Intent(this, hotelDetails::class.java)
//                    intent.putExtra("hotelID", hotel.hotelName)
//                    startActivity(intent)
//                    finish()
//                }, 3000)
            }
        }
    }

    private fun fetchhotelDetails() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(HotelModel::class.java)?.let {
                    hotel = it
                    val hotelName = hotel.hotelName
                    val hotelSuitedFor = hotel.hotelSuitedFor
                    val hotelImageLink = hotel.hotelImageLink
                    val hotelLink = hotel.hotelPrice
                    val hotelDescription = hotel.hotelDescription

                    binding.ethotelName.setText(hotelName)
                    binding.ethotelSuitedFor.setText(hotelSuitedFor)
                    binding.ethotelImageLink.setText(hotelImageLink)
                    binding.ethotelPrice.setText(hotelLink)
                    binding.ethotelDescription.setText(hotelDescription)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AdminUpdateActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent1 = Intent(this, AdminHotelDetails::class.java)
        intent1.putExtra("hotelID", hotelID)
        startActivity(intent1)
        finish()
    }
}