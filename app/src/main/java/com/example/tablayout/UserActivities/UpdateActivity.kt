package com.example.tablayout.UserActivities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.tablayout.R
import com.example.tablayout.ui.main.Profile.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_update.*


class UpdateActivity : AppCompatActivity() {


    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    //1. declare the type of input field
    private var name: EditText? = null
    private var etPicture: ImageView?=null

    //2. declare data type
    private var aName:String?=null
    private var picture:String?=null
    private var email:String?=null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)



           initialise()



    }



    private fun initialise(){

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        name = findViewById<View>(R.id.up_username) as EditText
        etPicture = findViewById<View>(R.id.lb_profile_pic) as ImageView


    }

    override fun onStart() {
        super.onStart()

        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)

        //3. reference them together
        aName = up_username.text.toString()


            mUserReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    name?.setText(snapshot.child("name").value as String)
                    picture = snapshot.child("pictureUrl").value as String

                    editProfile.setOnClickListener {

                        //update name set here
                        var user = Users(mUser.email.toString(), name!!.text.toString(),picture.toString())

                        mUserReference.setValue(user)

                        Toast.makeText(this@UpdateActivity,"Username successfully updated!!",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@UpdateActivity, ProfileFragment::class.java))                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {

                    Toast.makeText(this@UpdateActivity,"Database error",Toast.LENGTH_SHORT).show()

                }
            })
    }







}
