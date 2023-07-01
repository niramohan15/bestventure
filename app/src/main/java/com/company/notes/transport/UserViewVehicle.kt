package com.company.notes.transport
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.notes.R
import com.company.notes.databinding.ActivityUserViewVehicleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserViewVehicle : AppCompatActivity(), VehicleRVAdapter.VehicleClickInterface {

    private lateinit var binding: ActivityUserViewVehicleBinding
    private lateinit var mAuth: FirebaseAuth

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var vehicleRVModalArrayList: ArrayList<VehicleModel>
    private lateinit var vehicleRVAdapter: VehicleRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main_transport)


        binding = ActivityUserViewVehicleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //! Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Transport")

        //! Add vehicle ButtonAdmin Add Vehiclessss Detailsssssssssssssss
//        binding.btnAddvehicle.setOnClickListener {
//            startActivity(Intent(this, AdminAddvehicle::class.java))
//        }

        //! vehicleRV Array List.
        vehicleRVModalArrayList = ArrayList()

        //! recyclerView
        binding.rvvehicles.layoutManager = LinearLayoutManager(this)
        vehicleRVAdapter = VehicleRVAdapter(vehicleRVModalArrayList, this, this);
        binding.rvvehicles.adapter = vehicleRVAdapter
        getAllvehicles()
    }


    private fun getAllvehicles() {
        vehicleRVModalArrayList.clear()
        databaseReference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //vehicleRVModalArrayList.add(snapshot.getValue(vehicleRVModal::class.java))
                snapshot.getValue(VehicleModel::class.java)?.let { vehicleRVModalArrayList.add(it) }
                vehicleRVAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                vehicleRVAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                vehicleRVAdapter.notifyDataSetChanged()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                vehicleRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onVehicleClick(position: Int) {
        val intent = Intent(this, UserViewSpecificVehicle::class.java)
        intent.putExtra("transportID", vehicleRVModalArrayList[position].vehicleName)
        startActivity(intent)
        finish()
    }
}