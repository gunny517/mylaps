package jp.ceed.android.mylapslogger

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

    private val viewModel: SessionInfoFragmentViewModel by viewModels()

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


    private fun initLayout(){
        viewModel.loadSessionInfo(args.sessionId)
        viewModel.onSaved.observe(viewLifecycleOwner){
            findNavController().navigateUp()
        }
    }
}