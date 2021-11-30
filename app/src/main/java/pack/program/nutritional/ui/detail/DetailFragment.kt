package pack.program.nutritional.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.detail_fragment.*
import pack.program.nutritional.R
import pack.program.nutritional.databinding.DetailFragmentBinding
import pack.program.nutritional.utils.BindingFragmentBase

class DetailFragment : BindingFragmentBase<DetailFragmentBinding, DetailViewModel>() {
    val agr : DetailFragmentArgs by navArgs()
    override fun onCreateViewModel(): DetailViewModel {
        return ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.detail_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detail = agr.detail
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(detail.url)
        }
    }

}