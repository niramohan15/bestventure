package com.company.notes.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.notes.R
import com.company.notes.databinding.ActivityAllCardDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AllCardDetails : AppCompatActivity(), PaymentRVAdapter.PaymentClickInterface {

    private lateinit var binding: ActivityAllCardDetailsBinding
    private lateinit var mAuth: FirebaseAuth

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var sightRVModalArrayList: ArrayList<PaymentModel>
    private lateinit var sightRVAdapter: PaymentRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main_transport)


        binding = ActivityAllCardDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //! Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Payment")



        //! sightRV Array List.
        sightRVModalArrayList = ArrayList()

        //! recyclerView
        binding.rvpayments.layoutManager = LinearLayoutManager(this)
        sightRVAdapter = PaymentRVAdapter(sightRVModalArrayList, this, this);
        binding.rvpayments.adapter = sightRVAdapter
        getAllsights()
    }


    private fun getAllsights() {
        sightRVModalArrayList.clear()
        databaseReference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //sightRVModalArrayList.add(snapshot.getValue(sightRVModal::class.java))
                snapshot.getValue(PaymentModel::class.java)?.let { sightRVModalArrayList.add(it) }
                sightRVAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                sightRVAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                sightRVAdapter.notifyDataSetChanged()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                sightRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onPaymentClick(position: Int) {
        val intent = Intent(this, UpdateCard::class.java)
        intent.putExtra("paymentID", sightRVModalArrayList[position].cardholderName)
        startActivity(intent)
        finish()
    }
}