package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.compose.SessionListCompose
import jp.ceed.android.mylapslogger.viewModel.SessionListComposeFragmentViewModel

@AndroidEntryPoint
class SessionListComposeFragment: Fragment() {

    private val args: SessionListFragmentArgs by navArgs()

    private val viewModel: SessionListComposeFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent { SessionListCompose(viewModel) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout () {
        viewModel.onSessionClickEvent.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { sessionListItem ->
                navigateToPracticeResult(sessionListItem.no.toInt())
            }
        }
    }

    private fun navigateToPracticeResult(sessionNo: Int){
        findNavController().navigate(
            SessionListComposeFragmentDirections.goToPracticeResultsFragment(
                args.activityId,
                args.sessionDate,
                args.trackId,
                args.trackLength,
                sessionNo
            )
        )
    }
}
