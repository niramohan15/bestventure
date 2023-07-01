package com.company.notes.utils

import android.content.Context
import android.net.Uri
//import com.bumptech.glide.Glide
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.company.notes.R
import java.io.IOException

class GlideLoader(val context: Context) {

    fun loadUserPicture(imageUri: Any, imageView: ImageView) {
        try {
            Glide.with(context)
                .load(Uri.parse(imageUri.toString()))
                .centerCrop()
                .placeholder(R.drawable.img)
                .into(imageView)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadMenuItemPicture(imageUri: Any, imageView: ImageView) {
        try {
            Glide.with(context)
                .load(Uri.parse(imageUri.toString()))
                .centerCrop()
                .into(imageView)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}