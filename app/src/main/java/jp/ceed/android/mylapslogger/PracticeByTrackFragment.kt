package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ceed.android.mylapslogger.adatpter.TrackBestAdapter
import jp.ceed.android.mylapslogger.databinding.FragmentPracticeByTrackBinding
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.util.AppSettings
import jp.ceed.android.mylapslogger.viewModel.PracticeByTrackFragmentViewModel

class PracticeByTrackFragment: Fragment() {

    var _binding: FragmentPracticeByTrackBinding? = null;

    val binding get() = _binding!!

    val args: PracticeByTrackFragmentArgs by navArgs()

    val viewModel: PracticeByTrackFragmentViewModel by viewModels(factoryProducer = ::factoryProducer)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_practice_by_track, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout(){
        val adapter = TrackBestAdapter(requireContext(), ::navigateToPracticeResults, true)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        viewModel.practiceTrackList.observe(viewLifecycleOwner){
            adapter.setListItems(it)
            adapter.notifyDataSetChanged()
        }
    }


    private fun factoryProducer(): PracticeByTrackFragmentViewModel.Factory{
        return PracticeByTrackFragmentViewModel.Factory(args, requireContext())
    }

    private fun navigateToPracticeResults(practiceTrack: PracticeTrack) {
        findNavController().navigate(
            if(AppSettings(requireContext()).isShowPracticeResultsAsSeparate()){
                PracticeByTrackFragmentDirections.actionPracticeByTrackFragmentToSessionListFragment(
                    practiceTrack.id,
                    practiceTrack.displayTime,
                    practiceTrack.trackLength
                )
            }else{
                PracticeByTrackFragmentDirections.actionPracticeByTrackFragmentToPracticeResultFragment(
                    practiceTrack.id,
                    practiceTrack.displayTime,
                    practiceTrack.trackLength,
                    0
                )
            }
        )
    }

}