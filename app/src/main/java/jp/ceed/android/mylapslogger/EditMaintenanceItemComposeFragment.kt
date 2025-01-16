package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.compose.EditMaintenanceItemCompose
import jp.ceed.android.mylapslogger.viewModel.EditMaintenanceItemComposeViewModel

@AndroidEntryPoint
class EditMaintenanceItemComposeFragment : Fragment() {
    
    private val viewModel: EditMaintenanceItemComposeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply { 
            setContent { EditMaintenanceItemCompose(viewModel) }
        }
    }
}