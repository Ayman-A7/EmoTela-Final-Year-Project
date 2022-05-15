package com.example.emotela_finalyearproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.emotela_finalyearproject.databinding.AdapterviewBinding
import kotlin.collections.ArrayList

class AdapterClass(var list:ArrayList<User>) :RecyclerView.Adapter<AdapterClass.ViewHolder>() {
    class ViewHolder(val binding: AdapterviewBinding) : RecyclerView.ViewHolder(binding.root) {
        var keyword = binding.keywordtv
        var timenddate=binding.timendatetv


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                keyword.text = this.keywordtext
                timenddate.text= this.timendate

            }


        }


    }

}
