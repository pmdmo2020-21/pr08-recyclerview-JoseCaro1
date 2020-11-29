package es.iessaladillo.pedrojoya.pr06.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.iessaladillo.pedrojoya.pr06.data.model.User

// TODO:
//  Crear una singleton Database que implemente la interfaz DataSource.
//  Al insertar un usuario, se le asignará un id autonumérico
//  (primer valor válido será el 1) que deberá ser gestionado por la Database.
//  La base de datos debe ser reactiva, de manera que cuando se agrege,
//  actualice o borre un usuario, los observadores de la lista deberán
//  recibir la nueva lista.
//  Al consultar los usuario se deberá retornar un LiveData con la lista
//  de usuarios ordenada por nombre

object Database : DataSource {

    private val listUser: MutableList<User> = mutableListOf()
    private val mutableLiveDataListUser: MutableLiveData<List<User>> = MutableLiveData()
    private var id: Long = 0


    init {
        updateList()
    }

    fun updateList() {
        mutableLiveDataListUser.value = listUser.sortedBy { it.nombre }
    }

    override fun getAllUsersOrderedByName(): LiveData<List<User>> = mutableLiveDataListUser


    override fun insertUser(user: User) {
        user.id = ++id
        listUser.add(user)
        updateList()
    }

    override fun updateUser(user: User) {
        var index = listUser.indexOfFirst { it.id == user.id }
        if (index != -1) {
            listUser[index] = user
            updateList()
        }
    }

    override fun deleteUser(user: User) {
        var index = listUser.indexOfFirst { it.id == user.id }
        if (index != -1) {
            listUser.removeAt(index)
            updateList()
        }

    }
}

