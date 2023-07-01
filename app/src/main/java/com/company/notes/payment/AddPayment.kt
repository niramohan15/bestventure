package com.company.notes.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.notes.R
import com.company.notes.ThankYou
import com.company.notes.databinding.ActivityAddPaymentBinding
import com.company.notes.sightseeing.AdminViewSightDetails
import com.google.firebase.database.*

class AddPayment : AppCompatActivity() {
    private lateinit var binding: ActivityAddPaymentBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_payment)

        binding = ActivityAddPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Success"
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Payment")



binding.savecard.setOnClickListener {
    val intent1 = Intent(this, AllCardDetails::class.java)
    startActivity(intent1)
    finish()
}

        binding.btnAddpayment.setOnClickListener {
            val cardholderName = binding.etCardholdername.text.toString()
            val cardNumber = binding.etCardnumber.text.toString()
            val cardDate = binding.etCarddate.text.toString()
            val cardCvc = binding.etCvc.text.toString()

            if (cardholderName.isEmpty() || cardNumber.isEmpty() || cardDate.isEmpty() || cardCvc.isEmpty()) {
                Toast.makeText(this, "Enter all fields first!!", Toast.LENGTH_SHORT).show()
            } else {


                val PaymentModel = PaymentModel(
                    cardholderName,
                    cardNumber,
                    cardDate,
                    cardCvc
                )

                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        databaseReference.child(cardholderName).setValue(PaymentModel)
                        Toast.makeText(
                            this@AddPayment,
                            "Card Added Successfully !!!",
                            Toast.LENGTH_SHORT
                        ).show();
                    }


                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@AddPayment,
                            "Error: $error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                )

                val intent = Intent(this, ThankYou::class.java)
                startActivity(intent)
                finish()
            }
        }

    }}



