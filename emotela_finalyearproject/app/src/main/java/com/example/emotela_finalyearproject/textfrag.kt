package com.example.emotela_finalyearproject


import android.location.GnssAntennaInfo
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.fragment.app.Fragment
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
import com.example.emotela_finalyearproject.databinding.FragmentTextfragBinding


class textfrag : Fragment(R.layout.fragment_textfrag), View.OnClickListener {
    private var binding: FragmentTextfragBinding? = null
    private lateinit var auth: FirebaseAuth

    //    private val binding get() = binding!!
    var database: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTextfragBinding.inflate(inflater, container, false)
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
                binding?.locationId?.error = "Please Enter Text"


            }
            val requestQueue: RequestQueue
            requestQueue = Volley.newRequestQueue(context)

            @SuppressLint("SetTextI18n") val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                "https://twtsentiment-fastapi.herokuapp.com/predict_sentiment?review=$location",
                null, { response: JSONObject ->
                    Log.d("Response", response.toString())
                    try {
                        val obj=JSONObject(response)
                        val tweets: JSONObject=obj.getJSONObject("sentence")
                        val sentence=tweets.getString("sentence")
                        val prediction=tweets.getString("prediction")
                        binding!!.showData.text="Tweet: $sentence"
                        binding!!.showData.text="Prediction: $prediction"


                        for (i in 0 until tweets.length()) {

                            binding?.showData!!.append(
                                """
 ${tweets.optString(i.toString())}
 """
                            )
                            Log.d("TEXT", tweets.optString(i.toString()))
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




