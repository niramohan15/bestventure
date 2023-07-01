package com.company.notes.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.company.notes.LoginActivity
import com.company.notes.RegisterActivity
import com.company.notes.SettingsProfileActivity
import com.company.notes.UserProfileActivity
import com.company.notes.model.User
import com.company.notes.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FireStoreClass {

    private val mFirestoreClass = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {

        mFirestoreClass.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }
            .addOnFailureListener { e ->

                Log.e(activity.javaClass.simpleName, "Error while registering the user.", e)
            }
    }

    fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }

    //supposed to be in activity, don't forget to update the verification status, put it in register activity
    fun sendEmailVerification(activity: Activity) {
        val currentUser = FirebaseAuth.getInstance().currentUser!!

        currentUser.sendEmailVerification()
            .addOnSuccessListener {
                Toast.makeText(activity, "Email verification sent", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(activity, "Failed while sending email verification: " + e.message, Toast.LENGTH_SHORT).show()
            }
    }

    fun checkIfEmailVerified(activity: Activity) {
        val currentUser = FirebaseAuth.getInstance().currentUser!!

        if (currentUser.isEmailVerified) {
            Toast.makeText(activity, "Your email is verified", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Your email is NOT verified", Toast.LENGTH_SHORT).show()
        }
    }
    fun getUserDetails(activity: Activity) {
        mFirestoreClass.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.javaClass.simpleName, document.toString())

                val user = document.toObject(User::class.java)!!

                val sharedPreferences = activity.getSharedPreferences(Constants.RUMI_PREFERENCES, Context.MODE_PRIVATE)

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(Constants.LOGGED_IN_USERNAME, "${user.firstName} ${user.lastName}")
                editor.apply()

                when(activity) {
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                    is SettingsProfileActivity -> {
                        activity.userDetailsSuccess(user)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error. And print the error in log.
                when (activity) {
                    is LoginActivity -> {
//                        activity.hideProgressDialog()
                    }
//                    is SettingsProfileActivity -> {
//                        activity.hideProgressDialog()
//                    }
                }

                Log.e(activity.javaClass.simpleName, "Error while getting user details.", e)
            }
    }
    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {
        mFirestoreClass.collection(Constants.USERS)
            .document(getCurrentUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                when (activity) {
                    is UserProfileActivity -> {
                        activity.userProfileUpdateSuccess()
                    }
                }
            }

            .addOnFailureListener {
                when (activity) {
                    is UserProfileActivity -> {
//                        activity.hideProgressDialog()
                    }
                }
                Log.e(activity.javaClass.simpleName, "Error while updating the user details")
            }
    }

    fun uploadImageToCloudStorage(activity: Activity, imageFileUri: Uri?, imageType: String) {
        val storageRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType + System.currentTimeMillis() + "." + Constants.getFileExtension(activity, imageFileUri)
        )

        storageRef.putFile(imageFileUri!!)
            .addOnSuccessListener { taskSnapshot ->
                Log.i("Firebase image URL", taskSnapshot.metadata!!.reference!!.downloadUrl.toString())

                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        when (activity) {
                            is UserProfileActivity -> {
                                activity.imageUploadSuccess(uri.toString())
                            }
                        }
                    }
            }
            .addOnFailureListener { exception ->
                when (activity) {
                    is UserProfileActivity -> {
//                        activity.hideProgressDialog()
                    }
                }
                Log.e(activity.javaClass.simpleName, exception.message, exception)
            }
    }



}