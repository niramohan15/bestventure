package com.company.notes.hotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.notes.R
import com.google.firebase.auth.FirebaseAuth
import com.company.notes.databinding.ActivityAdminMainBinding
import com.google.firebase.database.*


 class AdminMainActivity : AppCompatActivity(), HotelRVAdapter.HotelClickInterface {

    private lateinit var binding: ActivityAdminMainBinding
    private lateinit var mAuth: FirebaseAuth

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var hotelRVModalArrayList: ArrayList<HotelModel>
    private lateinit var hotelRVAdapter: HotelRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)



        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //! Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Hotel")

        //! Add hotel Button
        binding.btnAddhotel.setOnClickListener {
            startActivity(Intent(this, AdminAddHotel::class.java))
        }

        //! hotelRV Array List.
        hotelRVModalArrayList = ArrayList()

        //! recyclerView
        binding.rvhotels.layoutManager = LinearLayoutManager(this)
        hotelRVAdapter = HotelRVAdapter(hotelRVModalArrayList, this, this);
        binding.rvhotels.adapter = hotelRVAdapter
        getAllhotels()
    }

    private fun getAllhotels() {
        hotelRVModalArrayList.clear()
        databaseReference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //hotelRVModalArrayList.add(snapshot.getValue(hotelRVModal::class.java))
                snapshot.getValue(HotelModel::class.java)?.let { hotelRVModalArrayList.add(it) }
                hotelRVAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                hotelRVAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                hotelRVAdapter.notifyDataSetChanged()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                hotelRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

     override fun onHotelClick(position: Int) {
        val intent = Intent(this, AdminHotelDetails::class.java)
        intent.putExtra("hotelID", hotelRVModalArrayList[position].hotelName)
        startActivity(intent)
        finish()
             }

     //! Menu creation
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(com.google.firebase.database.R.menu.main_activity_menu, menu)
//        return true
//    }
//
//    ! on menu selection
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//        if (id == com.google.firebase.database.R.id.mLogout) {
//            Toast.makeText(this, "Logged Out!", Toast.LENGTH_SHORT).show()
//            mAuth.signOut()
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }
//        return super.onOptionsItemSelected(item)
//    }

//    override fun onhotelClick(position: Int) {
//        val intent = Intent(this, TipsDetails::class.java)
//        intent.putExtra("TipsID", hotelRVModalArrayList[position].tipsName)
//        startActivity(intent)
//        finish()
//    }
}