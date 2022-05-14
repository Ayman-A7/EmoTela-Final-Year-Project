package com.example.emotela_finalyearproject


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.example.emotela_finalyearproject.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class settings : Fragment(R.layout.fragment_settings) {
    private var binding: FragmentSettingsBinding? = null
    private lateinit var auth: FirebaseAuth

    //    private val binding get() = binding!!
    var database: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Users")
        loadprofile()

    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
    fun loadprofile() {
        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        binding?.enteredemail?.text = user?.email
        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding?.enteredname?.text = snapshot.child("username").value.toString()



            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        binding?.logoutbtn?.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity, Signin::class.java))
        }
        binding?.clickedit?.setOnClickListener {
            startActivity(Intent(activity, EditProfile::class.java))

        }
    }
}


//    private lateinit var binding: FragmentSettingsBinding
//    private lateinit var auth: FirebaseAuth
//    private lateinit var databaseReference: DatabaseReference
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = FragmentSettingsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        auth = FirebaseAuth.getInstance()
//        checkUser()
//        binding.logoutbtn.setOnClickListener{
//            auth.signOut()
//            checkUser()
//
//        }
//    }
//
//
//    private fun checkUser() {
//        val firebaseUser=auth.currentUser
//        if (firebaseUser!=null){
//            val email=firebaseUser.email
//            binding.enteredemail.text = email
//            val username=firebaseUser.displayName
//            binding.enteredname.text = username
//        }
//    }
//
//    private fun setContentView(root: ConstraintLayout) {
//
//    }
//}


//    private lateinit var binding: FragmentSettingsBinding
//    private lateinit var auth: FirebaseAuth
//    private lateinit var databaseReference: DatabaseReference
//    private lateinit var user: User
//    private lateinit var uid: String
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = FragmentSettingsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        auth = FirebaseAuth.getInstance()
//        uid = auth.currentUser?.uid.toString()
//        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
//
//        if (uid.isNotEmpty()) {
//            getUserData()
//        }
//
//        binding.editbtn.setOnClickListener {
//            val intent = Intent(activity, EditProfile::class.java)
//            startActivity(intent)
//
//        }
//
//        binding.logoutbtn.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//            val intent = Intent(activity, Signin::class.java)
//            startActivity(intent)
//
//        }
//
//
//    }
//
//
//
//
//    private fun getUserData() {
//        databaseReference.child(uid).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                snapshot.getValue(User::class.java).also {
//                    if (it != null) {
//                        user = it
//                    }
//                }
//                if (::user.isInitialized) {
//
//
//                    binding.nametitle.setText(user.username)
//                    binding.emailtitle.setText(user.email)
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(requireActivity(), "Failed", Toast.LENGTH_SHORT).show()
//            }
//        })
//
//    }
//    private fun setContentView(root: ConstraintLayout) {
//
//    }
//
//
//}
