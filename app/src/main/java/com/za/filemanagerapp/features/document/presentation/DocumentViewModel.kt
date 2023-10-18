package com.za.filemanagerapp.features.document.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.features.audio.domain.repository.AudioRepository
import com.za.filemanagerapp.features.document.domain.model.Document
import com.za.filemanagerapp.features.document.domain.repository.DocumentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentViewModel @Inject constructor(private val repository: DocumentRepository):
    ViewModel() {
    private var _documentList = MutableLiveData<List<Document>>()
    val documentList : LiveData<List<Document>>
        get()=_documentList
    init {
        getDocumentsList()
    }
    private fun getDocumentsList(){
        viewModelScope.launch(Dispatchers.IO) {
            _documentList.postValue(repository.getDocumentFiles())
        }
    }
}