package com.za.filemanagerapp.features.audio.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.za.filemanagerapp.databinding.FragmentAudioBinding
import com.za.filemanagerapp.features.audio.adapter.AudioAdapter
import com.za.filemanagerapp.features.audio.domain.model.Audio
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AudioFragment : Fragment() {
    private val viewModel: AudioViewModel by viewModels()
    private lateinit var binding: FragmentAudioBinding
    private lateinit var audioAdapter: AudioAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAudioBinding.inflate(inflater, container, false)
        return binding.root
        Log.d("check life cycle","fragment on create view")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelObserver()
        Log.d("check life cycle","fragment on view created")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("check life cycle","fragment on attach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("check life cycle","fragment on create")
    }

    override fun onStart() {
        super.onStart()
        Log.d("check life cycle","fragment on start")
    }

    override fun onResume() {
        super.onResume()
        Log.d("check life cycle","fragment on resume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("check life cycle","fragment on pause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("check life cycle","fragment on stop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("check life cycle","fragment on destroy view")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("check life cycle","fragment on destroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("check life cycle","fragment on detach")
    }

    private fun viewModelObserver() {
        viewModel.audiosList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()){
                populateRecycler(it)
            }
        }
    }
    private fun populateRecycler(audioList: List<Audio>) {
        binding.audioRecycler.setHasFixedSize(true)
        binding.audioRecycler.setItemViewCacheSize(10)
        binding.audioRecycler.layoutManager = LinearLayoutManager(requireContext())
        audioAdapter = AudioAdapter(requireContext(), audioList)
        binding.audioRecycler.adapter = audioAdapter
    }
}