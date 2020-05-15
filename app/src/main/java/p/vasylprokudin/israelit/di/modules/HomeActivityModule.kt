package p.vasylprokudin.israelit.di.modules

import dagger.Binds
import dagger.Module
import p.vasylprokudin.israelit.presentation.view.HomeActivity
import p.vasylprokudin.israelit.base.BaseActivity

@Module
abstract class HomeActivityModule {

    @Binds
    abstract fun providesHomeActivity(activity: HomeActivity): BaseActivity
}
