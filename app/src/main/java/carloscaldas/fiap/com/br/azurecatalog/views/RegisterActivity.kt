package carloscaldas.fiap.com.br.azurecatalog.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import carloscaldas.fiap.com.br.azurecatalog.R
import carloscaldas.fiap.com.br.azurecatalog.business.UserBusiness
import carloscaldas.fiap.com.br.azurecatalog.repository.UserRepository
import carloscaldas.fiap.com.br.azurecatalog.util.ValidationException
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Eventos
        setListeners()

        // Instanciar Variaveis da Class
        mUserBusiness = UserBusiness(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonSave -> {
                handleSave()
            }
        }
    }

    private fun setListeners() {
        buttonSave.setOnClickListener(this)
    }

    private fun handleSave() {

        try {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            // Faz a insercao do usuario
            mUserBusiness.insert(name, email, password)

        } catch (e: ValidationException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.erro_inesperado), Toast.LENGTH_LONG).show()
        }

    }

}
