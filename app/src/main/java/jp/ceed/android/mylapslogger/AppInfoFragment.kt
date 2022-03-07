package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import jp.ceed.android.mylapslogger.databinding.FragmentAppInfoBinding
import jp.ceed.android.mylapslogger.viewModel.AppInfoFragmentViewModel

class AppInfoFragment: Fragment() {

    private var _binding: FragmentAppInfoBinding? = null

    private val binding get() = _binding!!

    private val viewModel: AppInfoFragmentViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_app_info, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.showPracticeResultAsSeparate.observe(viewLifecycleOwner){
            viewModel.saveShowPracticeResultsAsSeparate()
        }
        viewModel.showSpeedBar.observe(viewLifecycleOwner){
            viewModel.saveShowSpeedBar()
        }
        viewModel.clickShowErrorLogEvent.observe(viewLifecycleOwner){
            navigateToErrorLog()
        }
    }

    private fun navigateToErrorLog(){
        findNavController().navigate(R.id.action_AppInfoFragment_to_ErrorLogFragment)
    }

}