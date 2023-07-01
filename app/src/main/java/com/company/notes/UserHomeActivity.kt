package com.company.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.notes.databinding.ActivityUserHomeBinding
import com.company.notes.hotel.UserViewHotel
import com.company.notes.sightseeing.AdminUpdateSights
import com.company.notes.sightseeing.UserViewSights
import com.company.notes.transport.UserViewVehicle


class UserHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)

        binding = ActivityUserHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sight.setOnClickListener {
            val intent1 = Intent(this, UserViewSights::class.java)
            startActivity(intent1)
        }
        binding.hotel.setOnClickListener {
            val intent1 = Intent(this, UserViewHotel::class.java)
            startActivity(intent1)
        }
        binding.transport.setOnClickListener {
            val intent1 = Intent(this, UserViewVehicle::class.java)
            startActivity(intent1)
        }
        binding.profile.setOnClickListener {
            val intent1 = Intent(this, SettingsProfileActivity::class.java)
            startActivity(intent1)
        }




    }


}