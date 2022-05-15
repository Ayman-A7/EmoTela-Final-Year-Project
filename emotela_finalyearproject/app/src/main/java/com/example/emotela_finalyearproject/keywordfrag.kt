package com.example.emotela_finalyearproject

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emotela_finalyearproject.databinding.FragmentKeywordfragBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.sql.Timestamp


class keywordfrag : Fragment() {
    private var binding: FragmentKeywordfragBinding? = null
    private lateinit var auth: FirebaseAuth

    var database: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKeywordfragBinding.inflate(inflater, container, false)
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val user = auth.currentUser
        databaseReference?.child(user?.uid!!)

        databaseReference = database?.getReference("Users")?.child("result")
        binding?.analyzebtn?.setOnClickListener {
            sendData()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
    private fun sendData() {

        val keywordtext = binding?.userenteredkeyword?.text.toString().trim()
        val timestamp = Timestamp(System.currentTimeMillis())
        val timendate = timestamp.toString().trim()
//        val username=auth.currentUser?.uid.toString().trim()
//        val email=auth.currentUser?.email.toString().trim()

        if (TextUtils.isEmpty(keywordtext)) {
            binding?.userenteredkeyword?.error = "keyword can not be Empty"
        } else {

            val model = User(keywordtext, timendate)
            val user = auth.currentUser
             databaseReference?.child(user?.uid!!)?.setValue(model)
            binding?.userenteredkeyword?.setText("")
            val currentUser = auth.currentUser
            val currentUserdb = databaseReference?.child((currentUser?.uid!!))

            currentUserdb?.child("keywordtext")?.setValue(keywordtext)
            currentUserdb?.child("timendate")?.setValue(timendate)



        }

    }
}