package com.modulo.data.di.module

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.modulo.data.Constants.TIME_OUT
import com.modulo.data.database.deserializer.DeviceDeserializer
import com.modulo.data.network.api.DataApi
import com.modulo.data.network.response.DeviceResp
import com.modulo.data.preferences.SharedPreferencesManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal val restModule = module {
    single { provideGson() }

    // Shared Preferences
    single { SharedPreferencesManager(preferences = get(), gson = get()) }

    // Interceptor
    single { provideHttpLoggingInterceptor() }
    single { provideChuckerInterceptor(get()) }

    // Retrofit
    single { provideRetrofitBuilder(gson = get()) }

    // OkHttpClient
    single {
        providePublicOkHttpClient(
            chuckerLoggingInterceptor = get(),
            httpLoggingInterceptor = get()
        )
    }

    // Api
    single {
        provideAuthApi(
            retrofitBuilder = get(),
            okHttpClient = get()
        )
    }
}

private fun provideGson() =
    GsonBuilder().registerTypeAdapter(DeviceResp::class.java, DeviceDeserializer(Gson())).create()


private fun provideChuckerInterceptor(context: Context) = ChuckerInterceptor.Builder(context).build()

private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
    .apply { level = HttpLoggingInterceptor.Level.BODY }

private fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))


fun providePublicOkHttpClient(
    chuckerLoggingInterceptor: ChuckerInterceptor,
    httpLoggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)

    if (com.modulo.data.BuildConfig.DEBUG) {
        builder.addInterceptor(chuckerLoggingInterceptor)
        builder.addInterceptor(httpLoggingInterceptor)
    }
    return builder.build()
}

private fun provideAuthApi(
    retrofitBuilder: Retrofit.Builder,
    okHttpClient: OkHttpClient,
): DataApi = retrofitBuilder
    .baseUrl(com.modulo.data.BuildConfig.API_ROOT + com.modulo.data.BuildConfig.API_SUFFIX)
    .client(okHttpClient)
    .build()
    .create(DataApi::class.java)

