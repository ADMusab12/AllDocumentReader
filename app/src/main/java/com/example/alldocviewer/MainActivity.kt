package com.example.alldocviewer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.alldocviewer.adapter.AllDocAdapter
import com.example.alldocviewer.databinding.ActivityMainBinding
import com.example.alldocviewer.model.Document
import com.example.alldocviewer.util.Extension.setStatusBarColor
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val documents = mutableListOf<Document>()
    private var REQUEST_READ_STORAGE = 1001
    private val manageExternalStoragePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
            if (hasManageExternalStoragePermission()) {
                fetchFilesFromStorage()
            } else {
                Toast.makeText(this,"storage not allowed",Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //todo set status bar color
        setStatusBarColor(R.color.white)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestStoragePermissions()
    }

    private fun requestStoragePermissions() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                if (!hasManageExternalStoragePermission()) {
                    val intent =
                        Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                            data = Uri.parse("package:${applicationContext.packageName}")
                        }
                    manageExternalStoragePermissionLauncher.launch(intent)
                }else{
                    fetchFilesFromStorage()
                }
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                //todo Android 11 to 12: Use MANAGE_EXTERNAL_STORAGE
                if (!hasManageExternalStoragePermission()) {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                        data = Uri.parse("package:${applicationContext.packageName}")
                    }
                    manageExternalStoragePermissionLauncher.launch(intent)
                } else {
                    fetchFilesFromStorage()
                }
            }
            else -> {
                //todo Android 10 and below: Request READ_EXTERNAL_STORAGE
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_READ_STORAGE
                    )
                } else {
                    fetchFilesFromStorage()
                }
            }
        }
    }

    private fun hasManageExternalStoragePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_READ_STORAGE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            fetchFilesFromStorage()
        }
    }

    private fun fetchFilesFromStorage() {
        documents.clear()
        val storageDir = Environment.getExternalStorageDirectory()

        if (storageDir.exists() && storageDir.isDirectory) {
            searchFiles(storageDir)
        }
        setupRecyclerViews()
    }

    private fun searchFiles(dir: File) {
        dir.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                searchFiles(file)
            } else if (file.isFile) {
                when (file.extension.lowercase()) {
                    "pdf" -> documents.add(Document(imageResId = R.drawable.pdf, fileName = file.name, fileType = "PDF", filePath = file.path))
                    "ppt", "pptx" -> documents.add(Document(imageResId = R.drawable.power, fileName = file.name, fileType = "PowerPoint", filePath = file.path))
                    "xls", "xlsx" -> documents.add(Document(imageResId = R.drawable.excel, fileName = file.name, fileType = "Excel", filePath = file.path))
                    "doc", "docx" -> documents.add(Document(imageResId = R.drawable.word, fileName = file.name, fileType = "Word", filePath = file.path))
                }
            }
        }
    }

    private fun setupRecyclerViews() {
        val pdfDocs = documents.filter { it.fileType == "PDF" }
        val pptDocs = documents.filter { it.fileType == "PowerPoint" }
        val excelDocs = documents.filter { it.fileType == "Excel" }
        val wordDocs = documents.filter { it.fileType == "Word" }

        binding.recPdf.adapter = AllDocAdapter(pdfDocs){
            navigate(it.filePath,it.fileName,it.fileType)
        }
        binding.recPpt.adapter = AllDocAdapter(pptDocs){
            navigate(it.filePath,it.fileName,it.fileType)
        }
        binding.recExcel.adapter = AllDocAdapter(excelDocs){
            navigate(it.filePath,it.fileName,it.fileType)
        }
        binding.recWord.adapter = AllDocAdapter(wordDocs){
            navigate(it.filePath,it.fileName,it.fileType)
        }
    }

    private fun navigate(filepath:String,fileName:String,fileType:String){
        val intent = Intent(this, DocViewerActivity::class.java).apply {
            putExtra("filePath", filepath)
            putExtra("fileName",fileName)
            putExtra("fileType",fileType)
        }
        startActivity(intent)
    }
}