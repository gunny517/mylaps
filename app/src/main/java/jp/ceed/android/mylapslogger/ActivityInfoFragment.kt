package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.databinding.FragmentActivityInfoBinding
import jp.ceed.android.mylapslogger.entity.EventObserver
import jp.ceed.android.mylapslogger.viewModel.ActivityInfoFragmentViewModel

@AndroidEntryPoint
class ActivityInfoFragment : Fragment() {

    private var _binding: FragmentActivityInfoBinding? = null

    private val binding get() = _binding!!

    private val viewModel: ActivityInfoFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity_info, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }


    private fun initViewModel() {
        viewModel.onSaved.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigateUp()
        })
    }
}