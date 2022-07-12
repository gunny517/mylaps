package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.adatpter.ActivitiesAdapter
import jp.ceed.android.mylapslogger.databinding.FragmentActivitiesBinding
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import jp.ceed.android.mylapslogger.util.AppSettings
import jp.ceed.android.mylapslogger.viewModel.ActivitiesFragmentViewModel

@AndroidEntryPoint
class ActivitiesFragment : Fragment() {

    private var _binding: FragmentActivitiesBinding? = null

    private val binding get() = _binding!!

    private val viewModel: ActivitiesFragmentViewModel by viewModels()

    lateinit var userAccountRepository: UserAccountRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activities, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAccountRepository = UserAccountRepository(view.context)
        initLayout()
    }


    override fun onResume() {
        super.onResume()
        checkAccount()
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_session_info).isVisible = false
        menu.findItem(R.id.action_session_summary).isVisible = false
        menu.findItem(R.id.action_user_info).isVisible = true
        menu.findItem(R.id.action_app_info).isVisible = true
        menu.findItem(R.id.action_track_best).isVisible = true
        menu.findItem(R.id.action_total_distance).isVisible = true
        menu.findItem(R.id.fuel_consumption_list).isVisible = true
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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
            R.id.fuel_consumption_list -> {
                navigateToFuelConsumptionList()
                true
            }
            else -> false
        }
    }


    private fun navigateToUserInfo() {
        findNavController().navigate(R.id.action_ActivitiesFragment_to_UserInfoFragment)
    }


    private fun navigateToAppInfo(){
        findNavController().navigate(R.id.action_ActivitiesFragment_to_AppInfoFragment)
    }

    private fun navigateToFuelConsumptionList(){
        findNavController().navigate(R.id.action_ActivitiesFragment_to_FuelConsumptionListFragment)
    }


    private fun checkAccount() {
        val token: String? = userAccountRepository.getAccessToken();
        val userId: String? = userAccountRepository.getUserId()
        if (token == null || userId == null) {
            findNavController().navigate(R.id.action_ActivitiesFragment_to_LoginFragment)
        } else {
            viewModel.callActivitiesRequest()
        }
    }

    private fun initLayout() {
        context?.let { it ->
            val adapter = ActivitiesAdapter(it, mutableListOf(), object : ActivitiesAdapter.OnClickListener {
                override fun onClick(activitiesItem: ActivitiesItem) {
                    navigateToPracticeResults(activitiesItem)
                }
            })
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            viewModel.activities.observe(viewLifecycleOwner, Observer {
                adapter.setItems(it)
                adapter.notifyDataSetChanged()
            })
        }
    }

    private fun navigateToPracticeResults(activitiesItem: ActivitiesItem) {
        findNavController().navigate(
            if(AppSettings(requireContext()).isShowPracticeResultsAsSeparate()){
                ActivitiesFragmentDirections.actionActivitiesFragmentToSessionListFragment(
                    activitiesItem.id,
                    activitiesItem.displayTime,
                    activitiesItem.locationId,
                    activitiesItem.trackLength
                )
            }else{
                ActivitiesFragmentDirections.actionActivitiesFragmentToPracticeResultsFragment(
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
        findNavController().navigate(R.id.action_ActivitiesFragment_to_TrackBestFragment)
    }

    private fun navigateToTotalDistance(){
        findNavController().navigate(R.id.action_ActivitiesFragment_to_TotalDistanceFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}