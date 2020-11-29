package es.iessaladillo.pedrojoya.pr06.ui.add_user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.Database.insertUser
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UserActivityBinding
import es.iessaladillo.pedrojoya.pr06.utils.SoftInputUtils.hideSoftKeyboard
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl
import kotlinx.android.synthetic.main.user_activity.*
import kotlin.random.Random


class AddUserActivity : AppCompatActivity() {


    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, AddUserActivity::class.java)
        }
    }

    private val binding: UserActivityBinding by lazy {
        UserActivityBinding.inflate(layoutInflater)
    }
    private val viewModel: AddUserViewModel by viewModels() {
        AddUserViewModelFactory(Database,this)
    }

    // NO TOCAR: Estos métodos gestionan el menú y su gestión

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

    private fun onSave():Boolean {
        hideSoftKeyboard(binding.root)
        binding.run {
            if (viewModel.checkFieldForm(listOf(txtFormName.text.toString(), txtFormEmail.text.toString(), txtFormPhone.text.toString()))) {
                val user: User = User(0, txtFormName.text.toString(), txtFormEmail.text.toString(), txtFormPhone.text.toString(),
                        txtFormAddress.text.toString(), txtFormWeb.text.toString(), viewModel.randomUrl.value!!)
                viewModel.saveUser(user)
                finish()

            }
            Snackbar.make(root,R.string.user_invalid_data, Snackbar.LENGTH_SHORT).show()

        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setImage()
        setupView()

    }

    private fun setImage() {
        viewModel.randomUrl.observe(this, {
            binding.imgForm.loadUrl(it)
        })
    }

    private fun setupView() {
        binding.imgForm.setOnClickListener { viewModel.changeImage() }
        binding.txtFormWeb.setOnEditorActionListener { _,_,_ -> onSave() }
    }


}