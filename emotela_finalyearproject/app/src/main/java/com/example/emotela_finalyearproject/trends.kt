package com.example.emotela_finalyearproject


import android.location.GnssAntennaInfo
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import com.example.emotela_finalyearproject.databinding.FragmentTrendsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import org.json.JSONArray
import org.json.JSONException
import com.android.volley.VolleyError


class trends : Fragment(R.layout.fragment_trends), View.OnClickListener {
    private var binding: FragmentTrendsBinding? = null
    private lateinit var auth: FirebaseAuth

    //    private val binding get() = binding!!
    var database: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrendsBinding.inflate(inflater, container, false)
        return binding!!.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getdata= binding?.getData

        getdata!!.setOnClickListener(this)


    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    @SuppressLint("SetTextI18n")
   override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.getData) {
            val location = binding?.locationId!!.text.toString().trim { it <= ' ' }
            if (location.isEmpty()) {
                binding?.locationId?.error = "Please Enter Your Country"


            }
            val requestQueue: RequestQueue
            requestQueue = Volley.newRequestQueue(context)
            @SuppressLint("SetTextI18n") val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                "https://twtsentiment-fastapi.herokuapp.com/get_trend?loc=$location",
                null, { response: JSONObject ->
                    Log.d("Response", response.toString())
                    try {
                        val jsonArray = response.getJSONArray("Trends")
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
        binding?.locationId!!.setOnClickListener {
            binding?.showData!!.setText("")
        }

    }


}




