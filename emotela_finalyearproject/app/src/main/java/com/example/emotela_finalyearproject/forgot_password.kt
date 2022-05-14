package com.example.emotela_finalyearproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class forgot_password : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var forgotemail: EditText

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        val submit_btn = findViewById<Button>(R.id.submit)
        (findViewById<View>(R.id.forgotemail) as EditText)

        submit_btn.setOnClickListener{
          val email:String= forgotemail.text.toString().trim()
            if (email.isEmpty()){
                 Toast.makeText(applicationContext,"Enter your email Address!!", Toast.LENGTH_LONG).show()
            }
            else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{task ->
                        if (task.isSuccessful){
                            Toast.makeText(applicationContext,"Email sent succesfully to reset your password!!", Toast.LENGTH_LONG).show()
                          finish()
                        }
                        else{
                            Toast.makeText(applicationContext,task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                        }

                    }
            }
            }

        }
    }
