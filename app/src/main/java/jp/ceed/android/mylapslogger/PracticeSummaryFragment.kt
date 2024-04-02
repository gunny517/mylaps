package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.adatpter.PracticeResultsAdapter
import jp.ceed.android.mylapslogger.databinding.FragmentPracticeSummaryBinding
import jp.ceed.android.mylapslogger.viewModel.PracticeSummaryFragmentViewModel

@AndroidEntryPoint
class PracticeSummaryFragment : Fragment() {

    private var _binding: FragmentPracticeSummaryBinding? = null

    private val binding get() = _binding!!

    private val viewModel: PracticeSummaryFragmentViewModel by viewModels()

    private val args: PracticeSummaryFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_practice_summary, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PracticeResultsAdapter(requireContext(), null)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        viewModel.recyclerViewItem.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.recyclerViewItem.value = args.params.sessionSummary
    }

}