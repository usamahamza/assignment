package pack.program.nutritional.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import pack.program.nutritional.BR


/**
 * Base class for all fragments that bind to a view model.
 * The data binding object and view model are provided as a class type parameter.
 */
abstract class BindingFragmentBase<TBinding : ViewDataBinding, TViewModel : ViewModel> : Fragment() {
    /**
     * View model that this fragment binds to.
     */
    protected lateinit var viewModel: TViewModel

    /**
     * Binding object for this fragment
     */
    private var _binding: TBinding? = null
    protected val binding
        get() = _binding!!

    /**
     * See [Fragment.onViewCreated]
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = onCreateViewModel()

        _binding = DataBindingUtil.bind(requireView())!!
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
        binding.lifecycleOwner = this
    }

    /**
     * See [Fragment.onCreateView]
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    /**
     * Creates the view model that will bind to this fragment.
     * @return The view model object that is created
     */
    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    protected abstract fun onCreateViewModel(): TViewModel

    /**
     * Gets the layout ID that is used to inflate the view.
     * @return The layout ID
     */
    protected abstract fun getLayoutId(): Int
}