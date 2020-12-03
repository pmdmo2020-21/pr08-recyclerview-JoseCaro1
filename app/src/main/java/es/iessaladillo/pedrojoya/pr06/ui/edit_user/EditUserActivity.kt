package es.iessaladillo.pedrojoya.pr06.ui.edit_user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UserActivityBinding
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl
import es.iessaladillo.pedrojoya.pr06.utils.observeEvent

class EditUserActivity : AppCompatActivity() {

    // TODO: Código de la actividad.
    //  Ten en cuenta que la actividad debe recibir a través del intent
    //  con el que es llamado el objeto User corresondiente
    //  ...

    // NO TOCAR: Estos métodos gestionan el menú y su gestión

    companion object {

        private const val EXTRA_USER = "USER"

        fun newIntent(context: Context, user: User): Intent {
            return Intent(context, EditUserActivity::class.java).putExtras(bundleOf(EXTRA_USER to user))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.user, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuSave) {
            onSave()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // FIN NO TOCAR

    private val binding: UserActivityBinding by lazy {
        UserActivityBinding.inflate(layoutInflater)
    }

    private val viewModel: EditUserViewModel by viewModels() {
        EditUserViewModelFactory(Database, application, this)
    }

    private fun onSave(): Boolean {
        binding.run {
            if (viewModel.checkFieldForm(listOf(txtFormName.text.toString(), txtFormEmail.text.toString(), txtFormPhone.text.toString()))) {

                viewModel.userLiveData.value?.run {
                    nombre = txtFormName.text.toString()
                    email = txtFormEmail.text.toString()
                    phoneNumber = txtFormPhone.text.toString()
                    address = txtFormAddress.text.toString()
                    web = txtFormWeb.text.toString()
                    photoUrl = viewModel.randomUrl.value!!

                }
                viewModel.updateUser()
                finish()
            }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            getIntentData()
        }
        observeViewModel()
        setupViews()
    }

    private fun observeMessage() {
        viewModel.message.observeEvent(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT)
                    .show()
        }
    }

    private fun setImage() {
        viewModel.randomUrl.observe(this, {
            binding.imgForm.loadUrl(it)
        })
    }

    private fun observeViewModel() {
        setImage()
        observeMessage()
    }

    private fun setupViews() {
        binding.imgForm.setOnClickListener { viewModel.changeImage() }
        binding.txtFormWeb.setOnEditorActionListener { _, _, _ -> onSave() }
    }

    private fun getIntentData() {
        if (intent == null || !intent.hasExtra(EXTRA_USER)) {
            throw RuntimeException(
                    "Fail intent")
        }
        viewModel.saveUser(intent.getParcelableExtra(EXTRA_USER)!!)
        getPropertyUser(viewModel.userLiveData.value!!)

    }

    private fun getPropertyUser(user: User) {
        binding.run {
            txtFormName.text = changeEditableToString(user.nombre)
            txtFormEmail.text = changeEditableToString(user.email)
            txtFormPhone.text = changeEditableToString(user.phoneNumber)
            txtFormAddress.text = changeEditableToString(user.address)
            txtFormWeb.text = changeEditableToString(user.web)
            viewModel.getImage(user.photoUrl)

        }

    }

    private fun changeEditableToString(property: String) = Editable.Factory.getInstance().newEditable(property)


}