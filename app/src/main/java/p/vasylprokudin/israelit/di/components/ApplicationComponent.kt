package p.vasylprokudin.israelit.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import p.vasylprokudin.israelit.base.App
import p.vasylprokudin.israelit.di.modules.ActivityBuildersModule
import p.vasylprokudin.israelit.di.modules.ApplicationModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuildersModule::class,
    ApplicationModule::class
])
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
