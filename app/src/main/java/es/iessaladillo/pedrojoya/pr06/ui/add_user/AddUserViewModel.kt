package es.iessaladillo.pedrojoya.pr06.ui.add_user

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.DataSource
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.utils.Event
import kotlinx.android.synthetic.main.user_activity.view.*
import java.util.*


// TODO:
//  Crear la clase EditUserViewModel. Ten en cuenta que la url de la photo
//  deberá ser preservada por si la actividad es destruida por falta de recursos.
const val URL = "URL"

class AddUserViewModel(private val dataSource: DataSource, val application: Application, savedStateHandle: SavedStateHandle) : ViewModel() {

    // Para obtener un URL de foto de forma aleatoria (tendrás que definir
    // e inicializar el random a nivel de clase.
    private val random: Random = Random()
    private var _randomUrl: MutableLiveData<String> = savedStateHandle.getLiveData(URL, getRandomPhotoUrl())
    val randomUrl: LiveData<String> get() = _randomUrl
    private var _message: MutableLiveData<Event<String>> = MutableLiveData()
    val message: LiveData<Event<String>> get() = _message

    private fun getRandomPhotoUrl(): String = "https://picsum.photos/id/${random.nextInt(100)}/400/300"


    fun saveUser(user: User) {
        dataSource.insertUser(user)
    }

    fun changeImage() {
        _randomUrl.value = getRandomPhotoUrl()
    }

    fun checkFieldForm(list: List<String>): Boolean {
        list.forEach {
            if (it.isBlank()) {
                _message.value = Event(application.getString(R.string.user_invalid_data))
                return false
            }

        }
        return true
    }


}
