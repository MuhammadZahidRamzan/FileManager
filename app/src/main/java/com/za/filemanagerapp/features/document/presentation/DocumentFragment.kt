package com.za.filemanagerapp.features.document.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.za.filemanagerapp.databinding.FragmentDocumentBinding
import com.za.filemanagerapp.features.document.adapter.DocumentAdapter
import com.za.filemanagerapp.features.document.domain.model.Document
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
            if (!it.isNullOrEmpty()){
                populateRecycler(it)
            }
        }
    }
    private fun populateRecycler(documentList: List<Document>) {
        binding.pbDocument.visibility = View.GONE
        binding.tvAllDocuments.text = "All Documents : ${documentList.size}"
        binding.documentRecycler.setHasFixedSize(true)
        binding.documentRecycler.layoutManager = LinearLayoutManager(requireContext())
        audioAdapter = DocumentAdapter(requireContext(), documentList)
        binding.documentRecycler.adapter = audioAdapter
    }
}