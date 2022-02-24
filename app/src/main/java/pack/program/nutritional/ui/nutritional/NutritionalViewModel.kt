package pack.program.nutritional.ui.nutritional

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.model.NewsResponse
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import pack.program.nutritional.model.NutritionalModel
import pack.program.nutritional.repostories.NutritionalRepository
import pack.program.nutritional.utils.Constants.Companion.APP_ID
import pack.program.nutritional.utils.Constants.Companion.APP_KEY
import pack.program.nutritional.utils.Resource
import retrofit2.Response

class NutritionalViewModel(
    val nutritionalRepository : NutritionalRepository
) : ViewModel() {

    val breakingnews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val nutritiolaLivedata: MutableLiveData<Resource<NutritionalModel>> = MutableLiveData()
    var breakinNewsPage = 1

    var job: Job? = null

    init {
        getNutiritionData()
    }

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

    fun getNutiritionData(){
         viewModelScope.launch(Dispatchers.IO) {
            val fields =  ArrayList<String>()
            fields.apply {
                add("item_name")
                add("brand_name")
                add("nf_protein")
            }

            val params = HashMap<String, String>()
             params["brand_name"] = "McDonalds"
             params["item_name"] = "Kids Fries"

             val queries = JSONObject()
             queries.put("brand_name", "McDonalds")
             queries.put("item_name", "Kids Fries")
             val jsonObject = JSONObject()
             jsonObject.put("appId", APP_ID)
             jsonObject.put("appKey", APP_KEY)
             jsonObject.put("queries", queries)

            Log.d("response", "response params $params   $jsonObject" )
            val response = nutritionalRepository.getNutiritionData(params)
            Log.d("response", "responsesssss...... $response    params $params")

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