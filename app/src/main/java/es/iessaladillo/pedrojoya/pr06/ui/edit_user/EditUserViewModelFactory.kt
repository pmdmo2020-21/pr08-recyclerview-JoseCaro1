package es.iessaladillo.pedrojoya.pr06.ui.edit_user

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import es.iessaladillo.pedrojoya.pr06.data.DataSource

class EditUserViewModelFactory(private val dataSource: DataSource,
                               owner: SavedStateRegistryOwner,
                               defaultArgs: Bundle? = null) :
        AbstractSavedStateViewModelFactory(owner, defaultArgs) {


    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        return EditUserViewModel(dataSource, handle) as T
    }

}
