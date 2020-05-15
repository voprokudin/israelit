package p.vasylprokudin.israelit.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import p.vasylprokudin.israelit.presentation.view.HomeActivity

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [
        HomeActivityModule::class,
        ApplicationModule::class
    ])
    abstract fun contributeHomeActivity(): HomeActivity
}
