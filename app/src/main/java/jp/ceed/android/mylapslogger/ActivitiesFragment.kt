package jp.ceed.android.mylapslogger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
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
import jp.ceed.android.mylapslogger.adatpter.ActivitiesAdapter
import jp.ceed.android.mylapslogger.databinding.FragmentActivitiesBinding
import jp.ceed.android.mylapslogger.entity.EventObserver
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.service.PracticeDataService
import jp.ceed.android.mylapslogger.util.AppSettings
import jp.ceed.android.mylapslogger.viewModel.ActivitiesFragmentViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(DelicateCoroutinesApi::class)
@AndroidEntryPoint
class ActivitiesFragment : Fragment() {

    private var _binding: FragmentActivitiesBinding? = null

    private val binding get() = _binding!!

    private val viewModel: ActivitiesFragmentViewModel by viewModels()

    private val menuProvider = object : MenuProvider{

        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            val useMenus = listOf(
                R.id.action_user_info,
                R.id.action_app_info,
                R.id.action_track_best,
                R.id.action_total_distance,
                R.id.action_fuel_consumption_list
            )
            menu.forEach {
                it.isVisible = useMenus.contains(it.itemId)
            }
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.action_user_info -> {
                    navigateToUserInfo()
                    true
                }
                R.id.action_app_info -> {
                    navigateToAppInfo()
                    true
                }
                R.id.action_track_best -> {
                    navigateToTrackBest()
                    true
                }
                R.id.action_total_distance -> {
                    navigateToTotalDistance()
                    true
                }
                R.id.action_fuel_consumption_list -> {
                    navigateToFuelConsumptionList()
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activities, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        requireActivity().addMenuProvider(menuProvider ,viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToUserInfo() {
        findNavController().navigate(R.id.goToUserInfoFragment)
    }

    private fun navigateToAppInfo(){
        findNavController().navigate(R.id.goToAppInfoFragment)
    }

    private fun navigateToFuelConsumptionList(){
        findNavController().navigate(R.id.goToFuelConsumptionListFragment)
    }

    private fun initLayout() {
        val adapter = ActivitiesAdapter(requireContext(), object : ActivitiesAdapter.OnClickListener {
            override fun onClick(activitiesItem: ActivitiesItem) {
                navigateToPracticeResults(activitiesItem)
            }
        })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        viewModel.activities.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.event.observe(viewLifecycleOwner, EventObserver { eventState ->
            when(eventState){
                ActivitiesFragmentViewModel.EventState.GO_TO_LOGIN ->
                    navigateToLogin()
                ActivitiesFragmentViewModel.EventState.START_PRACTICE_SERVICE ->
                    startPracticeService(viewModel.activities.value)
                ActivitiesFragmentViewModel.EventState.NONE -> {}
            }
        })
    }

    private fun navigateToPracticeResults(activitiesItem: ActivitiesItem) {
        findNavController().navigate(
            if(AppSettings(requireContext()).isShowPracticeResultsAsSeparate()){
                ActivitiesFragmentDirections.goToSessionListFragment(
                    activitiesItem.id,
                    activitiesItem.displayTime,
                    activitiesItem.locationId,
                    activitiesItem.trackLength
                )
            }else{
                ActivitiesFragmentDirections.goToPracticeResultsFragment(
                    activitiesItem.id,
                    activitiesItem.displayTime,
                    activitiesItem.locationId,
                    activitiesItem.trackLength,
                    0
                )
            }
        )
    }

    private fun navigateToTrackBest(){
        findNavController().navigate(R.id.goToTrackBestFragment)
    }

    private fun navigateToTotalDistance(){
        findNavController().navigate(R.id.goToTotalDistanceFragment)
    }

    private fun navigateToLogin(){
        findNavController().navigate(R.id.goToLoginFragment)
    }

    private fun startPracticeService(activities: List<ActivitiesItem>?){
        activities?.let {
            val list = ArrayList(it)
            val context: Context = requireContext()
            val intent = Intent(context, PracticeDataService::class.java)
            intent.putParcelableArrayListExtra(PracticeDataService.PARAM_ACTIVITIES, list)
            context.startService(intent)
        }
    }
}