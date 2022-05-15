package com.example.emotela_finalyearproject
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.os.PersistableBundle
import android.widget.Toast
import android.text.TextUtils
import android.util.Patterns
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.emotela_finalyearproject.databinding.ActivitySignupBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    lateinit var auth:FirebaseAuth
    var databaseReference:DatabaseReference?=null
    var database:FirebaseDatabase?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseReference= database?.reference!!.child("Users").child("userInfo")
        signup()

        binding.loginscreen.setOnClickListener {
            startActivity(Intent(this@Signup, Signin::class.java))
        }

    }
    private fun signup() {
        binding.signup.setOnClickListener {

            val email = binding.emailId.text.toString().trim()
            val username = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val confirmpassword = binding.retypePass.text.toString().trim()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailId.error = "Invalid Email Format"
            } else if (TextUtils.isEmpty(username)) {
                binding.password.error = "Please Enter username"

            } else if (TextUtils.isEmpty(password)) {
                binding.password.error = "Please Enter Password"

            } else if (password.length < 6) {
                binding.password.error = "Password must be of At least 6 Characters"

            } else if (confirmpassword.length < 6) {
                binding.retypePass.error = "Password must be of At least 6 Characters"

            } else if (password != confirmpassword) {
                binding.retypePass.error = "Password does not Match"
            }
            else {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        val currentUser = auth.currentUser
                        val currentUserdb = databaseReference?.child((currentUser?.uid!!))
                        currentUserdb?.child("email")?.setValue(email)
                        currentUserdb?.child("username")?.setValue(username)

                        Toast.makeText(
                            this,
                            "Account created with email $email",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        startActivity(Intent(this, Signin::class.java))
                        finish()


                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Account Not created due to ${e.message}",
                            Toast.LENGTH_SHORT
                        )
                            .show()

                    }
            }
        }
    }

}
//import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import com.google.firebase.auth.FirebaseAuth
//import android.content.Intent
//import android.os.PersistableBundle
//import android.widget.Toast
//import android.text.TextUtils
//import android.util.Patterns
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.database.DatabaseReference
//import com.example.emotela_finalyearproject.databinding.ActivitySignupBinding
//import com.google.android.gms.tasks.OnCompleteListener
//import com.google.firebase.database.FirebaseDatabase
//
//
//class Signup : AppCompatActivity() {
//
//
//    private var inputEmail: EditText? = null
//    private var username: EditText? = null
//    private var inputPassword: EditText? = null
//    private var confirmpass: EditText? = null
//    private var btnSignIn: Button? = null
//    private var btnSignUp: Button? = null
//    private var auth: FirebaseAuth? = null
//    private var loginscreen: TextView? = null
//    private var databaseReference: DatabaseReference? = null
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_signup)
//
////        add my code
//        auth = FirebaseAuth.getInstance()
//
//        btnSignIn = findViewById<Button>(R.id.signup)
//        btnSignUp = findViewById<Button>(R.id.sign_Up)
//        username = findViewById<EditText>(R.id.username)
//        inputEmail = findViewById<EditText>(R.id.emailId)
//        inputPassword = findViewById<EditText>(R.id.password)
//        confirmpass = findViewById<EditText>(R.id.retypePass)
//        loginscreen = findViewById<TextView>(R.id.loginscreen)
//
//        loginscreen!!.setOnClickListener {
//            val intent = Intent(this@Signup, Signin::class.java)
//            startActivity(intent)
//        }
//        btnSignUp!!.setOnClickListener(View.OnClickListener {
//            val email = inputEmail!!.text.toString().trim()
//            val username = username!!.text.toString().trim()
//            val password = inputPassword!!.text.toString().trim()
//            val confirmpassword = confirmpass!!.text.toString().trim()
//
//
//            if (TextUtils.isEmpty(email)) {
//                Toast.makeText(applicationContext, "Enter your email Address!!", Toast.LENGTH_LONG)
//                    .show()
//                return@OnClickListener
//            }
//            if (TextUtils.isEmpty(username)) {
//                Toast.makeText(applicationContext, "Enter your User Name!!", Toast.LENGTH_LONG)
//                    .show()
//                return@OnClickListener
//            }
//            if (TextUtils.isEmpty(password)) {
//                Toast.makeText(applicationContext, "Enter your Password", Toast.LENGTH_LONG).show()
//                return@OnClickListener
//            }
//            if (password.length < 6) {
//                Toast.makeText(
//                    applicationContext,
//                    "Password too short, enter mimimum 6 charcters",
//                    Toast.LENGTH_LONG
//                ).show()
//                return@OnClickListener
//            }
//            if (confirmpassword.length < 6) {
//                Toast.makeText(
//                    applicationContext,
//                    "Password too short, enter mimimum 6 charcters",
//                    Toast.LENGTH_LONG
//                ).show()
//                return@OnClickListener
//            }
//
//            //create user
//            auth!!.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, OnCompleteListener { task ->
//                    Toast.makeText(
//                        this@Signup,
//                        "createUserWithEmail:onComplete" + task.isSuccessful,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    databaseReference = FirebaseDatabase.getInstance().getReference("Users")
//                    val User = User(username, email)
//                    databaseReference!!.child(username).setValue(User).addOnSuccessListener {
//                        if (task.isSuccessful) {
//                            Toast.makeText(this@Signup, "User created", Toast.LENGTH_SHORT).show()
//                            return@addOnSuccessListener
//
//                        } else {
//                            Toast.makeText(this@Signup, "User Not created", Toast.LENGTH_SHORT)
//                                .show()
//
//                        }
//                    }
//
//                })
//            val intent = Intent(this@Signup, MainActivity::class.java)
//            startActivity(intent)
//
//        })
//    }
//}
