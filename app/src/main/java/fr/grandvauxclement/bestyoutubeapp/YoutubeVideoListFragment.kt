package fr.grandvauxclement.bestyoutubeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import fr.grandvauxclement.bestyoutubeapp.databinding.YoutubeVideoListFragmentBinding

class YoutubeVideoListFragment : Fragment() {

    private val viewModel: ListVideoViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as YoutubeVideoApplication).database.youtubeVideoDao()
        )
    }

    private var _binding: YoutubeVideoListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = YoutubeVideoListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = YoutubeVideoListAdapter {
            val action =
                YoutubeVideoListFragmentDirections.actionYoutubeVideoListFragmentToYoutubeVideoDetailFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter
        // Attach an observer on the allItems list to update the UI automatically when the data
        // changes.
        viewModel.allVideos.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val action = YoutubeVideoListFragmentDirections.actionYoutubeVideoListFragmentToAddYoutubeVideoFragment(
                getString(R.string.add_fragment_title)
            )
            this.findNavController().navigate(action)
        }
    }
}