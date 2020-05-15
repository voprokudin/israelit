package p.vasylprokudin.israelit.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import p.vasylprokudin.israelit.di.util.ViewModelKey
import p.vasylprokudin.israelit.presentation.viewmodel.HomeActivityViewModel
import p.vasylprokudin.israelit.presentation.viewmodel.factory.ViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeActivityViewModel::class)
    internal abstract fun bindHomeActivityViewModel(homeActivityViewModel: HomeActivityViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
