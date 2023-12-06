package com.za.filemanagerapp.features.document.presentation.activities

import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.za.filemanagerapp.databinding.ActivityDocumentViewerBinding
import com.za.filemanagerapp.features.document.domain.model.Document
import com.za.filemanagerapp.utils.Constants
import com.za.filemanagerapp.utils.Extensions.parcelable

class DocumentViewerActivity : AppCompatActivity() {
    private var document:Document? = null
    private lateinit var binding:ActivityDocumentViewerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        getIntentData()

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    private fun getIntentData(){
        document = intent.parcelable(Constants.DOCUMENT)
        val uri = Uri.parse(document?.uri)
        binding.tvToolBar.text = document?.name?.substringBeforeLast(".")
        binding.pdfViewer.fromUri(uri).load()

        // Determine the document type based on the file extension (e.g., .docx, .xlsx)
       // val documentType = determineDocumentType(uri)
     //   viewDocumentInWebView(binding.webView, uri, "application/msword")


      ////  when (documentType) {
       //     DocumentType.WORD -> viewDocumentInWebView(binding.webView, uri, "application/msword")
       //     DocumentType.EXCEL -> viewDocumentInWebView(binding.webView, uri, "application/vnd.ms-excel")
        //    else -> {
                // Handle unsupported document type
       //     }
      //  }



    }


    private fun determineDocumentType(documentUri: Uri): DocumentType {
        val extension = MimeTypeMap.getFileExtensionFromUrl(documentUri.toString())
        return when (extension) {
            "doc", "docx" -> DocumentType.WORD
            "xls", "xlsx" -> DocumentType.EXCEL
            else -> DocumentType.UNKNOWN
        }
    }

    private fun viewDocumentInWebView(webView: WebView, documentUri: Uri, mimeType: String) {
        val googleDocsViewerUrl = "https://docs.google.com/gview?embedded=true&url=$documentUri"
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(googleDocsViewerUrl)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let { view?.loadUrl(it) }
                return true
            }
        }
    }

}
enum class DocumentType {
    WORD,
    EXCEL,
    UNKNOWN
}
