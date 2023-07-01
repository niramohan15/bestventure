package com.company.notes.sightseeing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.notes.R
import com.company.notes.databinding.ActivityUserViewSpecificSightBinding
import com.company.notes.payment.AddPayment

import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class UserViewSpecificSight : AppCompatActivity() {
    lateinit var binding: ActivityUserViewSpecificSightBinding

    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var sight: SightModel

    lateinit var sightID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserViewSpecificSightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sightID = intent.getStringExtra("sightID").toString()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Sights").child(sightID)

        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(SightModel::class.java)?.let {
                    sight = it
                    Picasso.get().load(sight.sightImageLink.toString()).into(binding.ivsightImage)
//                    binding.tvPrice.text = sight.sightPrice.toString()
                    binding.tvsightTitle.text = sight.sightName.toString()
                    binding.tvsightDescription.text = sight.sightDescription.toString()

                    val suitedFor = "Suited For: ${sight.sightSuitedFor.toString()}"
                    binding.tvSuitedFor.text = suitedFor

                    val amount = "Amount $ : ${sight.sightPrice.toString()}"
                    binding.tvPrice.text = amount
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, error.message, Toast.LENGTH_SHORT).show()
            }
        })

        binding.btncancelsight.setOnClickListener {
            val intent = Intent(this, UserViewSights::class.java)
            intent.putExtra("sightID", sightID)
            startActivity(intent)
            finish()
        }

        //! remaining
        binding.btnpaymentsight.setOnClickListener {
            val intent = Intent(this, AddPayment::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, UserViewSights::class.java))
        finish()
    }
}