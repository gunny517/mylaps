package jp.ceed.android.mylapslogger

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.compose.EditMaintenanceLogCompose
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.viewModel.EditMaintenanceLogComposeFragmentViewModel
import java.io.File
import java.util.UUID

@AndroidEntryPoint
class EditMaintenanceLogComposeFragment: Fragment() {

    val viewModel: EditMaintenanceLogComposeFragmentViewModel by viewModels()

    private var imageUri: Uri = Uri.EMPTY

    private val startCameraForResult = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { result -> onCameraResult(result) }

    private val requestCameraPermissionForResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { startCamera() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent { EditMaintenanceLogCompose(viewModel = viewModel) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.event.observe(viewLifecycleOwner) {
            viewModel.event.value?.getContentIfNotHandled()?.let {
                when (it.name) {
                    EventState.SAVED.name -> onSaved()
                    EventState.START_CAMERA.name -> startCamera()
                }
            }
        }
    }

    private fun createAndSetImageUri(): Uri {
        fun createImageFile(): File {
            return File.createTempFile(
                UUID.randomUUID().toString(),
                ".jpg",
                requireContext().getExternalFilesDir("MntLogImages")
            )
        }

        imageUri = FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.APPLICATION_ID}.image_provider",
            createImageFile()
        )
        return imageUri
    }


    private fun onSaved() {
        findNavController().popBackStack()
    }

    private fun hasCameraPermission(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        )
    }

    private fun startCamera() {
        if (hasCameraPermission()) {
            startCameraForResult.launch(createAndSetImageUri())
        } else {
            requestCameraPermissionForResult.launch(Manifest.permission.CAMERA)
        }
    }

    private fun onCameraResult(isSuccess: Boolean) {
        if (isSuccess) {
            viewModel.loadImage(imageUri)
        }
    }
}