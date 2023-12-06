package com.za.filemanagerapp.features.video.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.SpannableStringBuilder
import android.text.format.DateUtils
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.za.filemanagerapp.R
import com.za.filemanagerapp.databinding.DetailViewBinding
import com.za.filemanagerapp.databinding.MoreBinding
import com.za.filemanagerapp.databinding.VideoViewBinding
import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.features.video.domain.model.Video

class VideoAdapter(private val context: Context,
                   private val videoList:List<Video>,
                   private val onItemClicked: (Video) -> Unit):
RecyclerView.Adapter<VideoAdapter.MyHolder>(){
    class MyHolder(binding: VideoViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.videoName
        val folderName = binding.videoFolderName
        val image = binding.videoImage
        val duration = binding.vidoeDuration
        val root = binding.getRoot()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(VideoViewBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.title.text = videoList[position].title
        holder.folderName.text = videoList[position].folderName
        holder.duration.text = DateUtils.formatElapsedTime(videoList[position].duration?.div(1000) ?: 0)
        Glide.with(context)
            .asBitmap()
            .load(videoList[position].artUri)
            .apply(RequestOptions().placeholder(R.mipmap.ic_launcher).centerCrop())
            .into(holder.image)
        holder.root.setOnClickListener {
            onItemClicked(videoList[position])
        }
        holder.root.setOnLongClickListener {
            val customDialog =
                LayoutInflater.from(context).inflate(R.layout.more, holder.root, false)
            val bindingDialog = MoreBinding.bind(customDialog)
            val dialog = MaterialAlertDialogBuilder(context).setView(customDialog).create()
            dialog.show()
            bindingDialog.btnShare.setOnClickListener {
                dialog.dismiss()
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "video/*"
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(videoList[position].path))
                ContextCompat.startActivity(
                    context,
                    Intent.createChooser(shareIntent, "Shearing Audio File!!"),
                    null
                )
            }
            bindingDialog.btnDetails.setOnClickListener {
                dialog.dismiss()
                val customDialogDetails =
                    LayoutInflater.from(context).inflate(R.layout.detail_view, holder.root, false)
                val bindingIF = DetailViewBinding.bind(customDialogDetails)
                val dialogDetails = MaterialAlertDialogBuilder(context).setView(customDialogDetails)
                    .setCancelable(false)
                    .create()
                dialogDetails.show()
                val detailText = SpannableStringBuilder().bold { append("DETAILS\n\nName:") }
                    .append(videoList[position].title)
                    .bold { append("\n\nDuration:") }
                    .append(DateUtils.formatElapsedTime(videoList[position].duration?.div(1000) ?: 0))
                    .bold { append("\n\nFile Size:") }.append(
                        Formatter.formatShortFileSize(
                            context,
                            videoList[position].size?.toLong() ?: 0
                        )
                    )
                    .bold { append("\n\nLocation:") }.append(videoList[position].path)
                bindingIF.tvDetails.text = detailText
                bindingIF.btnOk.setOnClickListener {
                    dialogDetails.dismiss()
                }

            }
            return@setOnLongClickListener true
        }
    }
}