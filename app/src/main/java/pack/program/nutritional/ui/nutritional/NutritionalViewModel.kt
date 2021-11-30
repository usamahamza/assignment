package pack.program.nutritional.ui.nutritional

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.model.NewsResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import pack.program.nutritional.repostories.NutritionalRepository
import pack.program.nutritional.utils.Resource
import retrofit2.Response

class NutritionalViewModel(
    val nutritionalRepository : NutritionalRepository
) : ViewModel() {

    val breakingnews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakinNewsPage = 1

    var job: Job? = null

    /**
     * Get the CoroutineExceptionHandler
     */
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("TAG", "Exception handled: ${throwable.localizedMessage}")
    }

    fun getBreakingNews(){
        job = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            breakingnews.postValue(Resource.Loading())
            val response = nutritionalRepository.getBreakingNews("us", breakinNewsPage)
            breakingnews.postValue(handleBreakingNewsResponse(response))
        }
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}