package service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjQsImlhdCI6MTczMTE2Mjg3OSwiZXhwIjoxNzMxMjQ5Mjc5fQ.HGTZhk0y0ZMKFOhs4DIJs8p8FU7Pr1nFphp-rDTqzVE"
    private val BASE_URL = "https://0911-132-191-2-41.ngrok-free.app/node-api/"
    private val client = OkHttpClient
                        .Builder()
                        .addInterceptor(AuthInterceptor(TOKEN))
                        .build()
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

}