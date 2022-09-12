package fr.grandvauxclement.bestyoutubeapp
import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.grandvauxclement.bestyoutubeapp.databinding.YoutubeVideoListItemBinding
import fr.grandvauxclement.bestyoutubeapp.pojo.YoutubeVideo

class YoutubeVideoListAdapter(private val onItemClicked: (YoutubeVideo) -> Unit) :
    ListAdapter<YoutubeVideo, YoutubeVideoListAdapter.YoutubeVideoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeVideoViewHolder {
        return YoutubeVideoViewHolder(
            YoutubeVideoListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: YoutubeVideoViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class YoutubeVideoViewHolder(private var binding: YoutubeVideoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(video: YoutubeVideo) {
            binding.itemName.text = video.title
            binding.itemPrice.text = video.category
            binding.itemQuantity.text = video.url
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<YoutubeVideo>() {
            override fun areItemsTheSame(oldItem: YoutubeVideo, newItem: YoutubeVideo): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: YoutubeVideo, newItem: YoutubeVideo): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}