package com.example.emotela_finalyearproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.emotela_finalyearproject.databinding.FragmentKeywordfragBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONException
import org.json.JSONObject
import java.sql.Timestamp


class keywordfrag : Fragment(R.layout.fragment_keywordfrag), View.OnClickListener  {
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
        val getdata= binding?.getData

        getdata!!.setOnClickListener(this)
        binding?.getData?.setOnClickListener {
            sendData()
            onClick(view)

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
    @SuppressLint("SetTextI18n")
    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.getData) {
            val keyword = binding?.userenteredkeyword!!.text.toString().trim { it <= ' ' }
            val count = binding?.countedittext!!.text.toString().trim { it <= ' ' }

            if (keyword.isEmpty()) {
                binding?.userenteredkeyword?.error = "Please Enter Keyword"

            }
            if (count.isEmpty()) {
                binding?.countedittext?.error = "Please Enter Number of Tweets "

            }
            val requestQueue: RequestQueue
            requestQueue = Volley.newRequestQueue(context)
            @SuppressLint("SetTextI18n") val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                "https://twtsentiment-fastapi.herokuapp.com/pred_keyword?qr=$keyword&count=$count",
                null, { response: JSONObject ->
                    Log.d("Response", response.toString())
                    try {
                        val jsonArray = response.getJSONArray("Tweets")

                        for (i in 0 until jsonArray.length()) {
                            binding?.showData!!.append(
                                """
 ${jsonArray.optString(i)}
 """
                            )
                            Log.d("TEXT", jsonArray.optString(i))
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }) { error: VolleyError? -> Log.d("myapp", "something went wrong") }
            requestQueue.add(jsonObjectRequest)
        }
        binding?.userenteredkeyword!!.setOnClickListener {
            binding?.showData!!.setText("")
        }
        binding?.countedittext!!.setOnClickListener {
            binding?.showData!!.setText("")
        }

    }

}

//private fun AppCompatButton.setOnClickListener(keywordfrag: keywordfrag) {
//
//}
