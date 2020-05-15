package p.vasylprokudin.israelit.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

@Suppress("unused")
inline fun <reified T : ViewModel> AppCompatActivity.obtainViewModel(
    viewModelFactory: ViewModelProvider.Factory
): T = ViewModelProviders.of(this, viewModelFactory).get(T::class.java)
