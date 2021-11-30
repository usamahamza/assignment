package pack.program.nutritional.ui.nutritional

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pack.program.nutritional.repostories.NutritionalRepository

class NutritionalViewModelProviderFactory(
    private val repository: NutritionalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NutritionalViewModel::class.java)) {
            return NutritionalViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}