package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.adatpter.TrackBestAdapter
import jp.ceed.android.mylapslogger.databinding.FragmentTrackBestBinding
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.viewModel.TrackBestFragmentViewModel

@AndroidEntryPoint
class TrackBestFragment: Fragment() {

    private var _binding: FragmentTrackBestBinding? = null

    private val binding get() = _binding!!

    private val viewModel: TrackBestFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_track_best, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout(){
        val adapter = TrackBestAdapter(requireContext(), ::navigateToPracticeResult)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        viewModel.trackBestList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

    private fun navigateToPracticeResult(item: PracticeTrack){
        findNavController().navigate(
            TrackBestFragmentDirections.goToPracticeResultFragment(
                item.id,
                item.displayTime,
                item.trackId,
                item.trackLength,
                0
            )
        )
    }

}