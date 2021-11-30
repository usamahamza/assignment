package pack.program.nutritional.ui.nutritional

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.nutritional_fragment.*
import pack.program.nutritional.R
import pack.program.nutritional.databinding.NutritionalFragmentBinding
import pack.program.nutritional.repostories.NutritionalRepository
import pack.program.nutritional.ui.adapter.NutritionlAdapter
import pack.program.nutritional.utils.BindingFragmentBase
import pack.program.nutritional.utils.NetworkUtils
import pack.program.nutritional.utils.Resource
import pack.program.nutritional.utils.getColorRes

class NutritionalFragment : BindingFragmentBase<NutritionalFragmentBinding, NutritionalViewModel>() {
    lateinit var nutritionalAdapter: NutritionlAdapter
    val nutritionalrepositry = NutritionalRepository()

    override fun onCreateViewModel(): NutritionalViewModel {
        return ViewModelProvider(this, NutritionalViewModelProviderFactory(nutritionalrepositry)).get(NutritionalViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.nutritional_fragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecylerView()
        handleNetworkChanges()

        nutritionalAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("detail", it)
            }
            findNavController().navigate(
                R.id.action_nutiritionalFragment_to_detailFragment,
                bundle
            )
        }
    }

    /**
     * Observe network changes i.e. Internet Connectivity
     */
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(requireContext()).observe(viewLifecycleOwner) { isConnected ->
            if (!isConnected) {
                textViewNetworkStatus.text =
                    getString(R.string.text_no_connectivity)
                networkStatusLayout.apply {

                    visibility= View.VISIBLE
                    activity?.getColorRes(R.color.red)?.let { setBackgroundColor(it) }
                }
            } else {
                if (nutritionalAdapter.itemCount == 0) setUpRecylerView()
                textViewNetworkStatus.text = getString(R.string.text_connectivity)
                networkStatusLayout.apply {

                    activity?.getColorRes(R.color.teal_700)?.let { setBackgroundColor(it) }

                    animate()
                        .alpha(1f)
                        .setStartDelay(ANIMATION_DURATION.toLong())
                        .setDuration(ANIMATION_DURATION.toLong())
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                visibility = View.GONE
                            }
                        })
                }
            }
        }
    }

    private fun setUpRecylerView() {
        nutritionalAdapter = NutritionlAdapter()
        rvNutrational.apply {
            adapter = nutritionalAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        displayNutiritionalList()
        viewModel.getBreakingNews()
    }

    private fun displayNutiritionalList() {
        viewModel.breakingnews.observe(viewLifecycleOwner){ response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        nutritionalAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e("TAG", "An Error ocurred $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun hideProgressBar(){
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    companion object {
        const val ANIMATION_DURATION = 1000L
    }

}