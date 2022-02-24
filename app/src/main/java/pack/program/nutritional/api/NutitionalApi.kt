package pack.program.nutritional.api

import com.androiddevs.mvvmnewsapp.model.NewsResponse
import pack.program.nutritional.model.NutritionalModel
import pack.program.nutritional.utils.Constants.Companion.API_KEY
import pack.program.nutritional.utils.Constants.Companion.APP_ID
import pack.program.nutritional.utils.Constants.Companion.APP_KEY
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

    @Headers("Content-Type: application/json")
//    @FormUrlEncoded
    @POST(value = "/v1_1/search")
    suspend fun getNutiritionData(
//        @Field("fields") fields: ArrayList<String>,
//        fields : Array = ["item_name", "brand_name"]
        @Query("queries",)
        params : HashMap<String, String>,
        @Query("appId")
        appID : String = APP_ID,
        @Query("appKey")
        appKey : String = APP_KEY,
    ) : Response<NutritionalModel>

}