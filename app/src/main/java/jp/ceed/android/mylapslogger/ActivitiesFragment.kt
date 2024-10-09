package jp.ceed.android.mylapslogger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.compose.ActivitiesCompose
import jp.ceed.android.mylapslogger.entity.EventObserver
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.service.PracticeDataService
import jp.ceed.android.mylapslogger.util.AppSettings
import jp.ceed.android.mylapslogger.viewModel.ActivitiesFragmentViewModel
import jp.ceed.android.mylapslogger.viewModel.ActivitiesFragmentViewModel.EventState
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(DelicateCoroutinesApi::class)
@AndroidEntryPoint
class ActivitiesFragment : Fragment() {

    private val viewModel: ActivitiesFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent { ActivitiesCompose() }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }


    private fun initLayout() {
        viewModel.event.observe(viewLifecycleOwner, EventObserver { eventState ->
            when(eventState){
                EventState.GO_TO_LOGIN ->
                    navigateToLogin()
                EventState.START_PRACTICE_SERVICE ->
                    startPracticeService(viewModel.activities.value)
                EventState.GO_TO_PRACTICE_RESULT -> {
                    viewModel.selectedActivity?.let {
                        navigateToPracticeResults(it)
                    }
                }
                EventState.NONE -> {}
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