package com.company.notes.sightseeing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.notes.R
import com.company.notes.databinding.ActivityAdminViewSightDetailsBinding


import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class AdminViewSightDetails : AppCompatActivity() {
    lateinit var binding: ActivityAdminViewSightDetailsBinding

    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var sight: SightModel

    lateinit var sightID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminViewSightDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sightID = intent.getStringExtra("sightID").toString()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Sights").child(sightID)

        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(SightModel::class.java)?.let {
                    sight = it
                    Picasso.get().load(sight.sightImageLink.toString()).into(binding.ivsightImage)
                    binding.tvPrice.text = sight.sightPrice.toString()
                    binding.tvsightTitle.text = sight.sightName.toString()
                    binding.tvsightDescription.text = sight.sightDescription.toString()

                    val suitedFor = "Suitabel For: ${sight.sightSuitedFor.toString()}"
                    binding.tvSuitedFor.text = suitedFor
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, error.message, Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnEditsight.setOnClickListener {
            val intent = Intent(this, AdminUpdateSights::class.java)
            intent.putExtra("sightID", sightID)
            startActivity(intent)
            finish()
        }

        //! remaining
        binding.btnDeletesight.setOnClickListener {
            databaseReference.removeValue()
            val intent = Intent(this, AdminViewSightDetails::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, AdminMainSightActivity::class.java))
        finish()
    }
}