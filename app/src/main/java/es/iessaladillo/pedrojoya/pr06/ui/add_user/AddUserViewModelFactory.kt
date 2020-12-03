package es.iessaladillo.pedrojoya.pr06.ui.add_user


import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import es.iessaladillo.pedrojoya.pr06.data.DataSource

class AddUserViewModelFactory(
        private val dataSource: DataSource,
        owner: SavedStateRegistryOwner,
        private val application: Application,
        defaultArgs: Bundle? = null,

        ) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        return AddUserViewModel(dataSource, application, handle) as T
    }


}