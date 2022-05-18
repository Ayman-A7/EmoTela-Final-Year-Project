package com.example.emotela_finalyearproject


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.emotela_finalyearproject.databinding.FragmentURLfragBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.json.JSONException
import org.json.JSONObject


class URLfrag : Fragment(R.layout.fragment_trends), View.OnClickListener {
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
        val getdata= binding?.gettweet

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
            val link: String = binding!!.getURL.getText().toString().trim()
            if (link.isEmpty()) {
                binding?.getURL?.error = "Please Enter URL"


            }
            val requestQueue: RequestQueue
            requestQueue = Volley.newRequestQueue(context)
            @SuppressLint("SetTextI18n") val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                "https://twtsentiment-fastapi.herokuapp.com/twt_link?link=$link",
                null, { response: JSONObject ->
                    Log.d("Response", response.toString())
                    try {
                        val jsonArray = response.getJSONArray("sentence")
                        for (i in 0 until jsonArray.length()) {
                            binding?.showTweet!!.append(
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
        binding?.getURL!!.setOnClickListener {
            binding?.showTweet!!.setText("")
        }

    }

}




