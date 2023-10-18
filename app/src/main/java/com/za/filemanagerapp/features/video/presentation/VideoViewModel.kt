package com.za.filemanagerapp.features.video.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.za.filemanagerapp.features.video.domain.model.Video
import com.za.filemanagerapp.features.video.domain.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(private val repository: VideoRepository):ViewModel() {
    private var _videosList = MutableLiveData<List<Video>>()
    val videosList : LiveData<List<Video>>
        get()=_videosList
    init {
        getVideosList()
    }
    private fun getVideosList(){
        viewModelScope.launch(Dispatchers.IO) {
            _videosList.postValue(repository.getVideoFiles())
        }
    }
}
