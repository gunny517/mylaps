package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
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

    private var _binding: FragmentPracticeByTrackBinding? = null;

    private val binding get() = _binding!!

    private val viewModel: PracticeByTrackFragmentViewModel by viewModels()

    private val menuProvider: MenuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            val useMenus = listOf(
                R.id.action_sort_by_best_time,
                R.id.action_sort_by_date
            )
            menu.forEach {
                it.isVisible = useMenus.contains(it.itemId)
            }
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when(menuItem.itemId) {
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

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_practice_by_track, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
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