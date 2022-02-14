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
import jp.ceed.android.mylapslogger.adatpter.TotalDistanceAdapter
import jp.ceed.android.mylapslogger.databinding.FragmentTotalDistanceBinding
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.entity.TotalDistance
import jp.ceed.android.mylapslogger.viewModel.TotalDistanceFragmentViewModel

class TotalDistanceFragment: Fragment() {

    private var _binding: FragmentTotalDistanceBinding? = null

    private val binding get() = _binding!!

    private val viewModel: TotalDistanceFragmentViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_total_distance, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TotalDistanceAdapter(requireContext(), ::navigateToPracticeByTrack)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        viewModel.totalDistanceList.observe(viewLifecycleOwner){
            adapter.setListItem(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun navigateToPracticeByTrack(totalDistance: TotalDistance){
        findNavController().navigate(
            TotalDistanceFragmentDirections.actionTotalDistanceFragmentToPracticeByTrackFragment(
                totalDistance.id,
                totalDistance.name
            )
        )
    }

}