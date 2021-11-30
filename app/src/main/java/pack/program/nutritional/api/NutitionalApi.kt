package pack.program.nutritional.api

import com.androiddevs.mvvmnewsapp.model.NewsResponse
import pack.program.nutritional.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.*

interface NutitionalApi {

    @GET(value = "v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode : String = "us",
        @Query("page")
        pageNumber : Int = 1,
        @Query("apiKey")
        apiKey : String = API_KEY
    ) : Response<NewsResponse>

}