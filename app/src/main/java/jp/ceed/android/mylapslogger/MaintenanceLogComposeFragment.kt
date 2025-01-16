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
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.compose.MaintenanceLogCompose
import jp.ceed.android.mylapslogger.viewModel.MaintenanceLogComposeFragmentViewModel

@AndroidEntryPoint
class MaintenanceLogComposeFragment: Fragment() {

    private val viewModel: MaintenanceLogComposeFragmentViewModel by viewModels()

    private val menuProvider: MenuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menu.forEach {
                it.isVisible = R.id.action_edit_maintenance_item == it.itemId
            }
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            if (R.id.action_edit_maintenance_item == menuItem.itemId) {
                navigateToEditMaintenanceItem()
                return true
            }
            return false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent { MaintenanceLogCompose(viewModel) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    fun navigateToEditMaintenanceItem() {
        findNavController().navigate(
            MaintenanceLogComposeFragmentDirections.goToEditMaintenanceItem()
        )
    }


}