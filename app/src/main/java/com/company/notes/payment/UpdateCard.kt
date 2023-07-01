package com.company.notes.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.notes.R
import com.company.notes.databinding.ActivityUpdateCardBinding
import com.company.notes.sightseeing.AdminViewSightDetails

import com.google.firebase.database.*


class UpdateCard : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateCardBinding
    private lateinit var paymentID: String
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var payment: PaymentModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_card)


        binding = ActivityUpdateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        paymentID = intent.getStringExtra("paymentID").toString()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Payment").child(paymentID)

        fetchsightDetails()

        binding.btndelete.setOnClickListener {
            databaseReference.removeValue()
            val intent = Intent(this, AddPayment::class.java)
            startActivity(intent)
            finish()

        }

        binding.btnupdate.setOnClickListener {
            val name = binding.etCardholdername.text.toString()
            val cardno = binding.etCardnumber.text.toString()
            val date = binding.etCarddate.text.toString()
            val cvc = binding.etCvc.text.toString()

            if (name.isEmpty() ||  cardno.isEmpty() || date.isEmpty() || cvc.isEmpty()) {
                Toast.makeText(this, "Enter all fields first!!", Toast.LENGTH_SHORT).show()
            } else {


                databaseReference  = firebaseDatabase.getReference("Payment").child(name)

                var hashMap : HashMap<String, String>
                        = HashMap<String, String> ()
                hashMap.put("cardholderName",name);
                hashMap.put("cardNumber",cardno);
                hashMap.put("cardDate",date);
                hashMap.put("cardCvc",cvc);

                databaseReference.updateChildren(hashMap as Map<String, Any>);

                Toast.makeText(this, "Updated Succesfully", Toast.LENGTH_SHORT).show()



            }
        }


    }

    private fun fetchsightDetails() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(PaymentModel::class.java)?.let {
                    payment = it
                    val holdername = payment.cardholderName
                    val cardnumber = payment.cardNumber
                    val expiry = payment.cardDate
                    val cvc = payment.cardCvc

                    binding.etCardholdername.setText(holdername)
                    binding.etCardnumber.setText(cardnumber)
                    binding.etCarddate.setText(expiry)
                    binding.etCvc.setText(cvc)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@UpdateCard, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent1 = Intent(this, AllCardDetails::class.java)
        intent1.putExtra("paymentID", paymentID)
        startActivity(intent1)
        finish()
    }
}