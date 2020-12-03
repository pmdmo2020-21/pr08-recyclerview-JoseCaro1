package es.iessaladillo.pedrojoya.pr06.ui.users

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UsersActivityBinding
import es.iessaladillo.pedrojoya.pr06.ui.add_user.AddUserActivity
import es.iessaladillo.pedrojoya.pr06.ui.edit_user.EditUserActivity
import es.iessaladillo.pedrojoya.pr06.utils.doOnSwiped
import kotlinx.android.synthetic.main.users_activity_item.*


class UsersActivity : AppCompatActivity() {

    // TODO: Código de la actividad.
    //  Ten en cuenta que el recyclerview se muestra en forma de grid,
    //  donde el número de columnas está gestionado
    //  por R.integer.users_grid_columns
    //  ...

    // NO TOCAR: Estos métodos gestionan el menú y su gestión


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.users, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuAdd) {
            onAddUser()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // FIN NO TOCAR

    private fun onAddUser() {
        addUserActivityLayout()
    }

    private val binding: UsersActivityBinding by lazy {
        UsersActivityBinding.inflate(layoutInflater)
    }

    private val viewModel: UsersActivityViewModel by viewModels {
        UsersActivityViewModelFactory(Database)
    }

    private val listAdapter: UsersActivityAdapter = UsersActivityAdapter().apply {
        onEditUserClickListener = { navigateEditUser(it) }
        onDeleteUserClickListener = { deleteUser(it) }

    }

    private fun navigateEditUser(position: Int) {
        val user: User = listAdapter.currentList[position]
        editUserActivityLayout(user)
    }

    private fun deleteUser(position: Int) {
        val user: User = listAdapter.currentList[position]
        viewModel.deleteUser(user)
        Snackbar.make(binding.root, getString(R.string.user_del,user.nombre), Snackbar.LENGTH_LONG)
                .setAction(R.string.undo) { viewModel.insertUser(user) }
                .show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
        observeUser()

    }

    private fun setupViews() {
        setupRecyclerView()
        binding.imgEmptyView.setOnClickListener { addUserActivityLayout() }

    }

    private fun observeUser() {
        viewModel.users.observe(this, {
            updateList(it)
        })

    }

    private fun updateList(newList: List<User>) {
        listAdapter.submitList(newList)
        binding.run {
            imgEmptyView.visibility = if (newList.isEmpty()) View.VISIBLE else View.GONE
            lblEmptyView.visibility = if (newList.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun setupRecyclerView() {
        val columns = resources.getInteger(R.integer.users_grid_columns)
        binding.lstContact.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, columns)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = listAdapter
            doOnSwiped { viewHolder, _ -> deleteUser(viewHolder.absoluteAdapterPosition) }
            doOnSwiped(ItemTouchHelper.RIGHT)
        }
    }

    private fun addUserActivityLayout() {
        val intent: Intent = AddUserActivity.newIntent(this)
        startActivity(intent)
    }

    private fun editUserActivityLayout(user: User) {
        val intent: Intent = EditUserActivity.newIntent(this, user)
        startActivity(intent)
    }

}


