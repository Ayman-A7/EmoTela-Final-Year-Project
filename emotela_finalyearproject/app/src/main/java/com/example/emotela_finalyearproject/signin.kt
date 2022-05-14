package com.example.emotela_finalyearproject

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.emotela_finalyearproject.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference


class Signin : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        signin()


    }


    private fun signin() {
        binding.signin.setOnClickListener {

            val email = binding.emailId.text.toString().trim()
            val password = binding.password.text.toString().trim()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailId.error = "Invalid Email Format"
            } else if (TextUtils.isEmpty(password)) {
                binding.password.error = "Please Enter Password"

            }
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Account Not created", Toast.LENGTH_SHORT).show()

                    }
                }

        }
        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this@Signin, forgot_password::class.java))
        }
        binding.registerscreen.setOnClickListener {
            startActivity(Intent(this@Signin, Signup::class.java))
        }
    }
}
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.os.Bundle
//import android.text.TextUtils
//import android.util.Patterns
//import android.view.View
//import android.widget.*
//import androidx.appcompat.app.AppCompatActivity
//import com.example.emotela_finalyearproject.databinding.ActivitySigninBinding
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//
//
//class Signin : AppCompatActivity() {
//
//    private lateinit var inputEmail: EditText
//    private lateinit var inputPassword: EditText
//    private lateinit var auth: FirebaseAuth
//    private lateinit var btnSignup: Button
//    private lateinit var btnLogin: Button
//    private lateinit var tvforgotpassword: TextView
//    private lateinit var registerscreen: TextView
//
//    @SuppressLint("CutPasteId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        //Get Firebase auth instance
//        auth = FirebaseAuth.getInstance()
//        if (auth.currentUser != null) {
//            startActivity(Intent(this@Signin, Signin::class.java))
//            finish()
//        }
//        setContentView(R.layout.activity_signin)
//        inputEmail=findViewById<View>(R.id.emailId) as EditText
//        inputPassword=findViewById<View>(R.id.password) as EditText
//        btnLogin= findViewById<View>(R.id.signin) as Button
//        btnSignup= findViewById<View>(R.id.sign_Up) as Button
//
//        //Get Firebase auth instance
//      auth= FirebaseAuth.getInstance()
//        this.btnSignup.setOnClickListener {
//            startActivity(
//                Intent(
//                    this@Signin,
//                    Signup::class.java
//                )
//            )
//        }
//        btnLogin.setOnClickListener(View.OnClickListener {
//            val email = inputEmail.text.toString()
//            val password = inputPassword.text.toString()
//            if (TextUtils.isEmpty(email)) {
//                Toast.makeText(applicationContext, "Enter email address !", Toast.LENGTH_LONG)
//                    .show()
//                return@OnClickListener
//            }
//            if (TextUtils.isEmpty(password)) {
//                Toast.makeText(applicationContext, "Enter password ! ", Toast.LENGTH_LONG).show()
//                return@OnClickListener
//            }
//
//            //auth user
//            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
//                this@Signin
//            ) { task ->
////                progressBar.visibility = View.GONE
//                if (!task.isSuccessful) {
//                    //there was an error
//                    if (password.length < 6) {
//                        inputPassword.error = getString(R.string.minimum_pass)
//                    } else {
//                        Toast.makeText(
//                            this@Signin,
//                            getString(R.string.auth_failed),
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                } else {
//                    val intent = Intent(this@Signin, MainActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//            }
//        })
//        tvforgotpassword= findViewById<View>(R.id.tv_forgot_password) as TextView
//            tvforgotpassword.setOnClickListener{
//            val intent = Intent(this@Signin, forgot_password::class.java)
//            startActivity(intent)
//                finish()
//    }
//        registerscreen=findViewById<View>(R.id.registerscreen) as TextView
//        registerscreen.setOnClickListener {
//            val intent = Intent(this@Signin, Signup::class.java)
//            startActivity(intent)
//        }
//    }
//}
