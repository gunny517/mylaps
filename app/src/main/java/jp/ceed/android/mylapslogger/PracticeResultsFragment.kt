package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import jp.ceed.android.mylapslogger.databinding.FragmentPracticeResultBinding
import jp.ceed.android.mylapslogger.viewModel.PracticeResultFragmentViewModel

class PracticeResultsFragment: Fragment() {

	private var _binding: FragmentPracticeResultBinding? = null

	private val binding get() = _binding!!

	private val viewModel: PracticeResultFragmentViewModel by viewModels()

	private val args: PracticeResultsFragmentArgs by navArgs()


	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_binding = DataBindingUtil.inflate(inflater, R.layout.fragment_practice_result, container, false)
		binding.viewModel = viewModel
		binding.lifecycleOwner = viewLifecycleOwner
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.getPracticeResult(args.sessionId)
	}

	private fun initLayout(){

	}



}