package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.compose.EditMaintenanceLogCompose
import jp.ceed.android.mylapslogger.viewModel.EditMaintenanceLogComposeFragmentViewModel

@AndroidEntryPoint
class EditMaintenanceLogComposeFragment: Fragment() {

    val viewModel: EditMaintenanceLogComposeFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent { EditMaintenanceLogCompose(viewModel = viewModel) }
        }
    }
}