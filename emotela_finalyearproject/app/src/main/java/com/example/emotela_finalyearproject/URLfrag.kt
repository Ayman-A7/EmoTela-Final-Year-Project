package com.example.emotela_finalyearproject



import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import com.example.emotela_finalyearproject.databinding.FragmentURLfragBinding
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


class URLfrag : Fragment(R.layout.fragment_u_r_lfrag), View.OnClickListener {
    private var binding: FragmentURLfragBinding? = null
    private lateinit var auth: FirebaseAuth

    //    private val binding get() = binding!!
    var database: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentURLfragBinding.inflate(inflater, container, false)
        return binding!!.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getdata = binding?.gettweet

        getdata!!.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.getData) {
            val link: String = binding!!.getURL.getText().toString().trim()
            if (link.isEmpty()) {
                binding?.getURL?.error = "Please Enter URL"
                return

            }
            val requestQueue: RequestQueue = Volley.newRequestQueue(context)
            @SuppressLint("SetTextI18n") val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                "https://twtsentiment-fastapi.herokuapp.com/get_trend?loc=$link",
                null, { response: JSONObject ->
                    Log.d("Response", response.toString())
                    try {
                        val jsonArray = response.getJSONArray("Trends")
                        for (i in 0 until jsonArray.length()) {
                            binding?.showTweet!!.append(
                                """
 ${jsonArray.optString(i)}
 """
                            )
                            Log.d("TEXT", jsonArray.optString(i))
                            Log.d("myapp", "Success")
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }) { error: VolleyError? -> Log.d("myapp", "something went wrong") }
            requestQueue.add(jsonObjectRequest)
        }
//        binding?.getURL!!.setOnClickListener {
//            binding?.showTweet!!.setText("")
//        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}






