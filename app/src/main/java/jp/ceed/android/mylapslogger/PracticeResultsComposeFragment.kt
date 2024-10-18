package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.MenuProvider
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.args.PracticeSummaryFragmentParams
import jp.ceed.android.mylapslogger.args.SessionInfoFragmentParams
import jp.ceed.android.mylapslogger.compose.PracticeResultsCompose
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import jp.ceed.android.mylapslogger.viewModel.PracticeResultComposeFragmentViewModel

@AndroidEntryPoint
class PracticeResultsComposeFragment : Fragment() {

    private val args: PracticeResultsFragmentArgs by navArgs()

    private val viewModel: PracticeResultComposeFragmentViewModel by viewModels()

    private val menuProvider: MenuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            val useMenus = listOf(
                R.id.action_session_summary,
                R.id.action_session_info,
                R.id.action_track_best
            )
            menu.forEach {
                it.isVisible = useMenus.contains(it.itemId)
            }
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.action_session_summary -> {
                    navigateToSessionSummary()
                    true
                }
                R.id.action_session_info -> {
                    navigateToActivityInfo()
                    true
                }
                R.id.action_track_best -> {
                    navigateToPracticeByTrackFragment()
                    true
                }
                else -> false
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent { PracticeResultsCompose() }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun navigateToSessionSummary() {
        viewModel.practiceResult.value?.let {
            val params = PracticeSummaryFragmentParams(
                it.sessionSummary as ArrayList<PracticeResultsItem>
            )
            findNavController().navigate(
                PracticeResultsFragmentDirections
                    .goPracticeSummaryFragment(params)
            )
        }
    }

    private fun navigateToActivityInfo() {
        viewModel.practiceResult.value?.let {
            findNavController().navigate(
                PracticeResultsFragmentDirections.goToActivityInfoFragment(
                    args.activityId,
                    args.trackId,
                    args.sessionDate,
                    it.bestLap,
                    it.totalLap,
                    it.totalTime,
                    it.totalDistance
                )
            )
        }
    }

    private fun navigateToSessionInfo(section: PracticeResultsItem.Section) {
        val titleText = getString(R.string.format_session_title, section.sectionTitle)
        val sessionInfoFragmentParams = SessionInfoFragmentParams(
            sessionId = section.sessionId,
            averageDuration = section.averageDuration,
            medianDuration = section.medianDuration
        )
        findNavController().navigate(
            PracticeResultsFragmentDirections
                .goToSessionInfoFragment(sessionInfoFragmentParams, args.sessionDate, titleText)
        )
    }

    private fun navigateToPracticeByTrackFragment(){
        findNavController().navigate(
            PracticeResultsFragmentDirections
                .goToPracticeByTrackFragment(args.trackId,)
        )
    }
}