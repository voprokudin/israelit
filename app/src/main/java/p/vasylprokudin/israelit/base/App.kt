package p.vasylprokudin.israelit.base

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import p.vasylprokudin.israelit.di.components.DaggerApplicationComponent
import javax.inject.Inject

class App
@Inject constructor() : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)

        return component
    }
}
