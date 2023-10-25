package com.za.filemanagerapp.features.document.presentation.activities

import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.za.filemanagerapp.databinding.ActivityDocumentViewerBinding
import com.za.filemanagerapp.utils.Constants

class DocumentViewerActivity : AppCompatActivity() {
    private var pdfUri:String? = null
    private lateinit var binding:ActivityDocumentViewerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()

    }
    private fun getIntentData(){
        pdfUri = intent.getStringExtra(Constants.DOCUMENT)
        val uri = Uri.parse(pdfUri)
        binding.pdfViewer.fromUri(uri).load()

    }
}
