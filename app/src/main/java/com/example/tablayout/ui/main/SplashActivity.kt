package com.example.tablayout.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tablayout.MainActivity
import com.example.tablayout.R
import com.example.tablayout.UserActivities.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class SplashActivity : AppCompatActivity() {

    private var authListener: FirebaseAuth.AuthStateListener? = null
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Timer().schedule(object : TimerTask() {
            override fun run() {



                auth = FirebaseAuth.getInstance()
                authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                    val currentUser = firebaseAuth.currentUser
                    if (currentUser != null) {

                        val intent = Intent(this@SplashActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{

                        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()

                    }

                }

                auth!!.addAuthStateListener(authListener!!)


            }

        }, 3000L)


    }
}
