package com.za.filemanagerapp.features.document.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.za.filemanagerapp.R
import com.za.filemanagerapp.databinding.AudioViewBinding
import com.za.filemanagerapp.databinding.DocumentViewBinding
import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.features.document.domain.model.Document
import com.za.filemanagerapp.utils.Utils.convertBytesToMegabytes
import com.za.filemanagerapp.utils.Utils.formateDuration

class DocumentAdapter(private val context: Context, private val documentList: List<Document>) :
    RecyclerView.Adapter<DocumentAdapter.MyHolder>() {
    class MyHolder(binding: DocumentViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.documentName
        val album = binding.documentAlbum
        val image = binding.image
        val size = binding.documentSize
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(DocumentViewBinding.inflate(LayoutInflater.from(context), parent, false))

    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.title.text = documentList[position].title
        holder.album.text = documentList[position].mimeType
        holder.size.text = "convertBytesToMegabytes(documentList[position].size.toLong()).toString() "
        Glide.with(context).load(R.drawable.docs)
            .apply(RequestOptions().placeholder(R.mipmap.ic_launcher).centerCrop())
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return documentList.size
    }
}