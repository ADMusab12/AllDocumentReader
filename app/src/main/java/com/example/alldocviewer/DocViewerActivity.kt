package com.example.alldocviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.cherry.lib.doc.bean.DocEngine
import com.example.alldocviewer.databinding.ActivityDocViewerBinding
import com.example.alldocviewer.util.Extension.setStatusBarColor

class DocViewerActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDocViewerBinding
    private var filePath = ""
    private var fileName = ""
    private var fileType = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataUpdateUi()
    }

   private fun getDataUpdateUi(){
       filePath = intent?.getStringExtra("filePath").toString()
       fileName = intent?.getStringExtra("fileName").toString()
       fileType = intent?.getStringExtra("fileType").toString()

       //todo set file name
       binding.tvFileName.text = fileName

       //todo set app bar color according file type
       val colorResId = when (fileType) {
           "PDF" -> R.color.light_red
           "PowerPoint" -> R.color.light_orange
           "Excel" -> R.color.light_green
           "Word" -> R.color.light_blue
           else -> R.color.white
       }

       binding.appbar.setBackgroundColor(ContextCompat.getColor(this, colorResId))
       setStatusBarColor(colorResId)

       //todo update document

       try {
           binding.docview.openDoc(this, filePath, 3, -1, false, DocEngine.MICROSOFT)
       } catch (e: Exception) {
           Toast.makeText(this, "Failed to open document.", Toast.LENGTH_SHORT).show()
       }
   }

}