package p.vasylprokudin.israelit.di.modules

import dagger.Module
import dagger.Provides
import p.vasylprokudin.israelit.data.repository.EntriesRemoteRepositoryImpl
import p.vasylprokudin.israelit.data.rest.TopEntriesService
import p.vasylprokudin.israelit.domain.repository.EntriesRemoteRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [ViewModelModule::class])
class ApplicationModule {

    private val BASE_URL = "https://www.reddit.com/r/"

    @Provides
    internal fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    internal fun provideRetrofitTopEntriesService(retrofit: Retrofit): TopEntriesService {
        return retrofit.create<TopEntriesService>(TopEntriesService::class.java)
    }

    @Provides
    internal fun provideEntriesRemoteRepository(
        entriesRemoteRepositoryImpl: EntriesRemoteRepositoryImpl
    ): EntriesRemoteRepository = entriesRemoteRepositoryImpl
}
