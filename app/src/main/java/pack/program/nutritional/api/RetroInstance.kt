package pack.program.nutritional.api

import okhttp3.OkHttpClient
import pack.program.nutritional.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetroInstance {

    companion object {
        val clientSetup = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()

        fun getRetroInstance() : Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientSetup)
                .build()
        }

        val api by lazy {
            getRetroInstance().create(NutitionalApi::class.java)
        }
    }

}