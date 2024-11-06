package com.example.alldocviewer.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alldocviewer.databinding.ItemListAllDocsBinding
import com.example.alldocviewer.model.Document

class AllDocAdapter(private val documents: List<Document>,private val onClick:(Document)->Unit):RecyclerView.Adapter<AllDocAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemListAllDocsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val document = documents[position]
        holder.image.setImageResource(document.imageResId)
        holder.filename.text = document.fileName
        holder.card.setCardBackgroundColor(getBackgroundColor(document.fileType))

        holder.card.setOnClickListener {
            onClick.invoke(document)
        }
    }

    override fun getItemCount(): Int {
        return documents.size
    }

    private fun getBackgroundColor(fileType: String): Int {
        return when (fileType) {
            "PDF" -> Color.parseColor("#FFCCCB") // Light Red
            "PowerPoint" -> Color.parseColor("#FFEBB2") // Light Orange
            "Excel" -> Color.parseColor("#B2FFB2") // Light Green
            "Word" -> Color.parseColor("#ADD8E6") // Light Blue
            else -> Color.WHITE // Default background color
        }
    }

    class ViewHolder(binding:ItemListAllDocsBinding):RecyclerView.ViewHolder(binding.root){
        val image = binding.imageDoc
        val filename = binding.textFileName
        val card = binding.card
    }
}