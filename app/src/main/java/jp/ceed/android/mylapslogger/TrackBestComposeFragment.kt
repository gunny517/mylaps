package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.compose.TrackBestCompose
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.viewModel.TrackBestComposeFragmentViewModel

@AndroidEntryPoint
class TrackBestComposeFragment: Fragment() {

    private val viewModel: TrackBestComposeFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent { TrackBestCompose(viewModel = viewModel) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout(){
        viewModel.event.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { item ->
                navigateToPracticeResult(item)
            }
        }
    }

    private fun navigateToPracticeResult(item: PracticeTrack){
        findNavController().navigate(
            TrackBestFragmentDirections.goToPracticeResultFragment(
                item.activityId,
                item.displayTime,
                item.trackId,
                item.trackLength,
                0
            )
        )
    }

}