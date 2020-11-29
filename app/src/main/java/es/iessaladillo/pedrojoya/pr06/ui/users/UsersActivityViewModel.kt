package es.iessaladillo.pedrojoya.pr06.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.DataSource

import es.iessaladillo.pedrojoya.pr06.data.Database.getAllUsersOrderedByName
import es.iessaladillo.pedrojoya.pr06.data.model.User

// TODO:
//  Crear clase UsersActivityViewModel
class UsersActivityViewModel(private val dataSource:DataSource): ViewModel() {

     val users: LiveData<List<User>> =dataSource.getAllUsersOrderedByName()


     fun deleteUser(user:User){
          dataSource.deleteUser(user)
     }



}
