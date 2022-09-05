package com.navi.lakshayclosed.di

import com.navi.lakshayclosed.business.data.network.NetworkDataSource
import com.navi.lakshayclosed.business.data.network.NetworkDataSourceImpl
import com.navi.lakshayclosed.domain.models.PullRequest
import com.navi.lakshayclosed.datasource.network.PullRequestRetrofitService
import com.navi.lakshayclosed.datasource.network.PullRequestRetrofitServiceImpl
import com.navi.lakshayclosed.datasource.network.mappers.NetworkMapper
import com.navi.lakshayclosed.datasource.network.model.PullRequestNetworkEntity
import com.navi.lakshayclosed.datasource.network.retrofit.PullRequestApiService
import com.navi.lakshayclosed.util.EntityMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkMapper(): EntityMapper<PullRequestNetworkEntity, PullRequest> {
        return NetworkMapper()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Singleton
    @Provides
    fun provideHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("  https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
    }

    @Singleton
    @Provides
    fun providePullRequestService(retrofit: Retrofit.Builder): PullRequestApiService {
        return retrofit
            .build()
            .create(PullRequestApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitService(
        pullRequestApiService: PullRequestApiService
    ): PullRequestRetrofitService {
        return PullRequestRetrofitServiceImpl(pullRequestApiService)
    }

    @Singleton
    @Provides
    fun provideNetworkDataSource(
        pullRequestRetrofitService: PullRequestRetrofitService,
        networkMapper: NetworkMapper
    ): NetworkDataSource {
        return NetworkDataSourceImpl(pullRequestRetrofitService, networkMapper)
    }


}




















