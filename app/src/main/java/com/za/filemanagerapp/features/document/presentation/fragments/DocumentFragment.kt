package com.za.filemanagerapp.features.document.presentation.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.za.filemanagerapp.R
import com.za.filemanagerapp.databinding.FragmentDocumentBinding
import com.za.filemanagerapp.features.document.adapter.DocumentAdapter
import com.za.filemanagerapp.features.document.domain.model.Document
import com.za.filemanagerapp.features.document.presentation.activities.DocumentViewerActivity
import com.za.filemanagerapp.features.document.presentation.view_models.DocumentViewModel
import com.za.filemanagerapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DocumentFragment : Fragment() {
    private val viewModel: DocumentViewModel by viewModels()
    private lateinit var binding: FragmentDocumentBinding
    private lateinit var audioAdapter: DocumentAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDocumentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelObserver()
    }

    private fun viewModelObserver() {
        viewModel.documentList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                populateRecycler(it)
            }
        }
    }

    private fun populateRecycler(documentList: List<Document>) {
        binding.pbDocument.visibility = View.GONE
        binding.tvAllDocuments.visibility = View.VISIBLE
        binding.tvAllDocuments.text =
            resources.getString(R.string.all_documents_string, documentList.size)
        binding.documentRecycler.setHasFixedSize(true)
        binding.documentRecycler.layoutManager = LinearLayoutManager(requireContext())
        audioAdapter = DocumentAdapter(requireContext(), documentList) {
            if (it.name?.substringAfterLast(".")=="pdf"){
                goToDocumentViewerActivity(it)
            }else{
            //    goToDocumentViewerActivity(it)
                openFile(Uri.parse(it.uri))
            }
        }
        binding.documentRecycler.adapter = audioAdapter
    }

    private fun goToDocumentViewerActivity(document:Document){
        val intent = Intent(requireContext(), DocumentViewerActivity::class.java)
        intent.putExtra(Constants.DOCUMENT,document)
        startActivity(intent)
    }

    private fun openFile(uri: Uri) {
        try {
            // Replace 'your_file_uri' with the URI of the file you want to open
            // Create an intent with ACTION_VIEW to open the file
            val intent = Intent(Intent.ACTION_VIEW)
            // Set the data (URI) of the file to open
            intent.data = uri
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                context,
                "No application found which can open the file",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}