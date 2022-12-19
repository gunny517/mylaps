package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.adatpter.PracticeByTrackAdapter
import jp.ceed.android.mylapslogger.databinding.FragmentPracticeByTrackBinding
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.util.AppSettings
import jp.ceed.android.mylapslogger.viewModel.PracticeByTrackFragmentViewModel

@AndroidEntryPoint
class PracticeByTrackFragment: Fragment() {

    var _binding: FragmentPracticeByTrackBinding? = null;

    val binding get() = _binding!!

    val viewModel: PracticeByTrackFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_practice_by_track, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_sort_by_best_time).isVisible = true
        menu.findItem(R.id.action_sort_by_date).isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_sort_by_best_time -> {
                viewModel.loadValues(true)
                true
            }
            R.id.action_sort_by_date -> {
                viewModel.loadValues(false)
                true
            }
            else -> false
        }
    }

    private fun initLayout(){
        val adapter = PracticeByTrackAdapter(requireContext(), ::navigateToPracticeResults)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        viewModel.practiceTrackList.observe(viewLifecycleOwner){
            adapter.submitList(it)
            setActionbarTitle(viewModel.practiceTrackList.value?.get(0))
        }
    }

    private fun setActionbarTitle(practiceTrack: PracticeTrack?) {
        practiceTrack?.let {
            (requireActivity() as MainActivity).setToolbarTitle(practiceTrack.trackName)
        }
    }

    private fun navigateToPracticeResults(practiceTrack: PracticeTrack) {
        findNavController().navigate(
            if(AppSettings(requireContext()).isShowPracticeResultsAsSeparate()){
                PracticeByTrackFragmentDirections.goToSessionListFragment(
                    practiceTrack.id,
                    practiceTrack.displayTime,
                    practiceTrack.trackId,
                    practiceTrack.trackLength
                )
            }else{
                PracticeByTrackFragmentDirections.goToPracticeResultFragment(
                    practiceTrack.id,
                    practiceTrack.displayTime,
                    practiceTrack.trackId,
                    practiceTrack.trackLength,
                    0
                )
            }
        )
    }

}