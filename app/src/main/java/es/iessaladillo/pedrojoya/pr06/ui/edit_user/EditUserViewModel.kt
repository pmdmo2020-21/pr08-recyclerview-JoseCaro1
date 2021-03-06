package es.iessaladillo.pedrojoya.pr06.ui.edit_user

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.DataSource
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.utils.Event
import java.util.*

// TODO:
//  Crear la clase EditUserViewModel. Ten en cuenta que la url de la photo
//  deberá ser preservada por si la actividad es destruida por falta de recursos.
const val URL = "URL"
const val USER = "USER"

class EditUserViewModel(private val dataSource: DataSource,private val application: Application, savedStateHandle: SavedStateHandle) : ViewModel() {

    // Para obtener un URL de foto de forma aleatoria (tendrás que definir
    // e inicializar el random a nivel de clase.
    private val random = Random()
    private var _randomUrl: MutableLiveData<String> = savedStateHandle.getLiveData(URL)
    val randomUrl: LiveData<String> get() = _randomUrl
    private var _userMutableLiveData: MutableLiveData<User> = savedStateHandle.getLiveData(USER)
    val userLiveData: LiveData<User> get() = _userMutableLiveData
    private var _message: MutableLiveData<Event<String>> = MutableLiveData()
    val message: LiveData<Event<String>> get() = _message


    private fun getRandomPhotoUrl(): String = "https://picsum.photos/id/${random.nextInt(100)}/400/300"


    fun changeImage() {
        _randomUrl.value = getRandomPhotoUrl()
    }

    fun getImage(url: String) {
        _randomUrl.value = url
    }

    fun updateUser() = dataSource.updateUser(userLiveData.value!!)

    fun saveUser(user: User) {
        _userMutableLiveData.value = user
    }

    fun checkFieldForm(list: List<String>): Boolean {
        list.forEach {
            if (it.isBlank()) {
                _message.value=Event(application.getString(R.string.user_invalid_data))
                return false
            }

        }
        return true
    }


}
