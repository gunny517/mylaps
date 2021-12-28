package jp.ceed.android.mylapslogger

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import jp.ceed.android.mylapslogger.databinding.FragmentSessionInfoBinding
import jp.ceed.android.mylapslogger.viewModel.SessionInfoFragmentViewModel

class SessionInfoFragment: Fragment() {

    private var _binding: FragmentSessionInfoBinding? = null

    private val binding get() = _binding!!

    private val viewModel: SessionInfoFragmentViewModel by viewModels(factoryProducer = ::viewModelFactoryProducer)

    private val args: SessionInfoFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_session_info, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    override fun onResume() {
        super.onResume()
        viewModel.startSensor()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopSensor()
    }

    private fun initLayout(){
        viewModel.onSaved.observe(viewLifecycleOwner){
            findNavController().navigateUp()
        }
    }

    private fun viewModelFactoryProducer(): SessionInfoFragmentViewModel.Factory {
        return SessionInfoFragmentViewModel.Factory(args.sessionId, requireContext().applicationContext as Application)
    }
}