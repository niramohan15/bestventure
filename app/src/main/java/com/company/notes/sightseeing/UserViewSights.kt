package com.company.notes.sightseeing
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.notes.R
import com.company.notes.databinding.ActivityUserViewSightsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserViewSights : AppCompatActivity(), SightRVAdapter.SightClickInterface {

    private lateinit var binding: ActivityUserViewSightsBinding
    private lateinit var mAuth: FirebaseAuth

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var sightRVModalArrayList: ArrayList<SightModel>
    private lateinit var sightRVAdapter: SightRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main_transport)


        binding = ActivityUserViewSightsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //! Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Sights")

        //! Add sight ButtonAdmin Add sightssss Detailsssssssssssssss
//        binding.btnAddsight.setOnClickListener {
//            startActivity(Intent(this, AdminAddsight::class.java))
//        }

        //! sightRV Array List.
        sightRVModalArrayList = ArrayList()

        //! recyclerView
        binding.rvsights.layoutManager = LinearLayoutManager(this)
        sightRVAdapter = SightRVAdapter(sightRVModalArrayList, this, this);
        binding.rvsights.adapter = sightRVAdapter
        getAllsights()
    }


    private fun getAllsights() {
        sightRVModalArrayList.clear()
        databaseReference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //sightRVModalArrayList.add(snapshot.getValue(sightRVModal::class.java))
                snapshot.getValue(SightModel::class.java)?.let { sightRVModalArrayList.add(it) }
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



    override fun onSightClick(position: Int) {
         val intent = Intent(this, UserViewSpecificSight::class.java)
        intent.putExtra("sightID", sightRVModalArrayList[position].sightName)
        startActivity(intent)
        finish()
    }
}