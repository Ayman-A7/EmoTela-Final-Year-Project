package com.example.emotela_finalyearproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.emotela_finalyearproject.databinding.EditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditProfile : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: EditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()
        loaduserinfo()

        binding.backbtn.setOnClickListener {
            onBackPressed()
        }

        binding.editbtn.setOnClickListener {
            validateData()

        }
    }
    private var username= ""
    private var email= ""
    private fun validateData() {
        username=binding.editusername.text.toString().trim()



        if(username.isEmpty()){
            Toast.makeText(this,"Enter name",Toast.LENGTH_SHORT).show()

        }

        else{
            val hashMap:HashMap<String, Any> = HashMap()
            hashMap["username"]="$username"

            val reference= FirebaseDatabase.getInstance().getReference("Users")
            reference.child(auth.uid!!)
                .updateChildren(hashMap)
                .addOnSuccessListener {
                    Toast.makeText(this,"Profile Updated",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {e->
                    Toast.makeText(this,"Profile Not Updated due to ${e.message}",Toast.LENGTH_SHORT).show()

                }
        }
    }

    private fun loaduserinfo(){
        val ref=FirebaseDatabase.getInstance().getReference("Users")
        ref.child(auth.uid!!)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    val username="${snapshot.child("username").value}"

                    binding.editusername.setText(username)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
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
//
//import androidx.appcompat.app.AppCompatActivity
//import com.example.emotela_finalyearproject.databinding.EditProfileBinding
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//
//
//class EditProfile :AppCompatActivity(){
//    private lateinit var binding: EditProfileBinding
//    private lateinit var auth: FirebaseAuth
//    private lateinit var databaseReference: DatabaseReference
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = EditProfileBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        auth = FirebaseAuth.getInstance()
//
//        binding.savebtn.setOnClickListener{
//            val username=binding.editname.text.toString()
//            val email=binding.editemail.text.toString()
//
//            databaseReference = FirebaseDatabase.getInstance().getReference("Users")
//            val User=User(username,email)
//            databaseReference.child(username).setValue(User).addOnSuccessListener {
//
//                binding.editname.text.clear()
//                binding.editemail.text.clear()
//                Toast.makeText(this,"Successfully Saved", Toast.LENGTH_SHORT).show()
//
//        }
//            .addOnFailureListener{
//                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
//
//            }
//
//
//
//                }
//            }
//
//
//        }