package com.example.emotela_finalyearproject

import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emotela_finalyearproject.databinding.FragmentRecentHistoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class recent_history : Fragment(R.layout.fragment_recent_history) {

    private var binding: FragmentRecentHistoryBinding? = null
    private lateinit var auth: FirebaseAuth

    var database: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
     private lateinit var userArrayList: ArrayList<User>
     private lateinit var myAdapter: AdapterClass


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecentHistoryBinding.inflate(inflater, container, false)
        userArrayList= arrayListOf()
        myAdapter=AdapterClass(userArrayList)
        binding?.recyclerview?.adapter=myAdapter
        var linearLayoutManager = LinearLayoutManager(context)
        binding?.recyclerview?.layoutManager = linearLayoutManager
        binding?.recyclerview?.setHasFixedSize(true)



        return binding!!.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Users").child("result")
        getData()

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
    private fun getData() {
        val user = auth.currentUser


        databaseReference?.child(user?.uid!!)
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        var model = data.getValue(User::class.java)
                        userArrayList.add(model!!)

                    }

                }

                }

            override fun onCancelled(error: DatabaseError) {
                Log.e("cancel", error.toString())
            }

        })
    }
}

