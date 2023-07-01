package com.company.notes.sightseeing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.notes.R

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.company.notes.databinding.ActivityAdminUpdateSightsBinding
import com.company.notes.sightseeing.AdminViewSightDetails
import com.company.notes.sightseeing.SightModel
import com.google.firebase.database.*

class AdminUpdateSights : AppCompatActivity() {

    private lateinit var binding: ActivityAdminUpdateSightsBinding
    private lateinit var sightID: String
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var sight: SightModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminUpdateSightsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sightID = intent.getStringExtra("sightID").toString()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Sights").child(sightID)

        fetchsightDetails()

        binding.btnCancel.setOnClickListener {
            val intent1 = Intent(this, AdminUpdateSights::class.java)
            intent1.putExtra("sightID", sightID)
            startActivity(intent1)
            finish()
        }

        binding.btnUpdatesight.setOnClickListener {
            val sightName = binding.etsightName.text.toString()
            val sightSuitedFor = binding.etsightSuitedFor.text.toString()
            val sightImageLink = binding.etsightImageLink.text.toString()
            val sightLink = binding.etsightPrice.text.toString()
            val sightDescription = binding.etsightDescription.text.toString()

            if (sightName.isEmpty() ||  sightSuitedFor.isEmpty() || sightImageLink.isEmpty() || sightLink.isEmpty() || sightDescription.isEmpty()) {
                Toast.makeText(this, "Enter all fields first!!", Toast.LENGTH_SHORT).show()
            } else {


                databaseReference  = firebaseDatabase.getReference("Sights").child(sightName)

                var hashMap : HashMap<String, String>
                        = HashMap<String, String> ()
                hashMap.put("sightDescription",sightDescription);
                hashMap.put("sightImageLink",sightImageLink);
                hashMap.put("sightName",sightName);
                hashMap.put("sightSuitedFor",sightSuitedFor);

                databaseReference.updateChildren(hashMap as Map<String, Any>);

                Toast.makeText(this, "Updated Succesfully", Toast.LENGTH_SHORT).show()



            }
        }
    }

    private fun fetchsightDetails() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(SightModel::class.java)?.let {
                    sight = it
                    val sightName = sight.sightName
                    val sightSuitedFor = sight.sightSuitedFor
                    val sightImageLink = sight.sightImageLink
                    val sightLink = sight.sightPrice
                    val sightDescription = sight.sightDescription

                    binding.etsightName.setText(sightName)
                    binding.etsightSuitedFor.setText(sightSuitedFor)
                    binding.etsightImageLink.setText(sightImageLink)
                    binding.etsightPrice.setText(sightLink)
                    binding.etsightDescription.setText(sightDescription)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AdminUpdateSights, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent1 = Intent(this, AdminViewSightDetails::class.java)
        intent1.putExtra("sightID", sightID)
        startActivity(intent1)
        finish()
    }
}