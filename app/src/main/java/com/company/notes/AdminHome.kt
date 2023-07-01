package com.company.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.notes.databinding.ActivityAdminHomeBinding
import com.company.notes.hotel.AdminMainActivity
import com.company.notes.hotel.UserViewHotel
import com.company.notes.payment.AddPayment
import com.company.notes.sightseeing.AdminMainSightActivity
import com.company.notes.transport.AdminMainTransportActivity
import com.company.notes.transport.UserViewVehicle


class AdminHome : AppCompatActivity() {
    private lateinit var binding: ActivityAdminHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.adminhotel.setOnClickListener {
            val intent1 = Intent(this, AdminMainActivity::class.java)
            startActivity(intent1)
        }
        binding.adminsightseeing.setOnClickListener {
            val intent1 = Intent(this, AdminMainSightActivity::class.java)
            startActivity(intent1)
        }
        binding.admintransport.setOnClickListener {
            val intent1 = Intent(this, AdminMainTransportActivity::class.java)
            startActivity(intent1)
        }
    }
}