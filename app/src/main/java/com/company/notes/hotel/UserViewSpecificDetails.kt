package com.company.notes.hotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.notes.R
import com.company.notes.databinding.ActivityUserViewSpecificDetailsBinding
import com.company.notes.payment.AddPayment

import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class UserViewSpecificDetails : AppCompatActivity() {
    lateinit var binding: ActivityUserViewSpecificDetailsBinding

    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var hotel: HotelModel

    lateinit var hotelID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserViewSpecificDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hotelID = intent.getStringExtra("hotelID").toString()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Hotel").child(hotelID)

        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(HotelModel::class.java)?.let {
                    hotel = it
                    Picasso.get().load(hotel.hotelImageLink.toString()).into(binding.ivhotelImage)

                    val PriceFor = "Amount $ : ${hotel.hotelPrice.toString()}"
                    binding.tvPrice.text = PriceFor

//                    binding.tvPrice.text = hotel.hotelPrice.toString()
                    binding.tvhotelTitle.text = hotel.hotelName.toString()
                    binding.tvhotelDescription.text = hotel.hotelDescription.toString()
                    val suitedFor = "Suited For: ${hotel.hotelSuitedFor.toString()}"
                    binding.tvSuitedFor.text = suitedFor
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, error.message, Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnpayment.setOnClickListener {
            val intent = Intent(this, AddPayment::class.java)
            startActivity(intent)

        }

//        //! remaining
//        binding.btnDeletehotel.setOnClickListener {
//            databaseReference.removeValue()
//            val intent = Intent(this, AdminMainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, UserViewHotel::class.java))
        finish()
    }
}