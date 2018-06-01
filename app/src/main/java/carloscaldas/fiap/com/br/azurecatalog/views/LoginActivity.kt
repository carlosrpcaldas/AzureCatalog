package carloscaldas.fiap.com.br.azurecatalog.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import carloscaldas.fiap.com.br.azurecatalog.R
import carloscaldas.fiap.com.br.azurecatalog.business.UserBusiness
import carloscaldas.fiap.com.br.azurecatalog.contants.TaskConstants
import carloscaldas.fiap.com.br.azurecatalog.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness
    private lateinit var mSecurityPreferences:  SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Instanciar as variaveis da class
        mUserBusiness = UserBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        setListeners()

        verifyLoggedUser()
    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.buttonLogin -> {
                handleLogin()
            }
        }
    }

    private fun verifyLoggedUser(){

        val userID = mSecurityPreferences.getStoredString(TaskConstants.Key.USER_ID)
        val name = mSecurityPreferences.getStoredString(TaskConstants.Key.USER_NAME)

        if (userID != "" && name != ""){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun handleLogin() {
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        if (mUserBusiness.login(email, password)){

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }else{
            Toast.makeText(this, getString(R.string.usuario_senha_incorretos), Toast.LENGTH_LONG).show()
        }

    }

    private fun setListeners(){
        buttonLogin.setOnClickListener(this)
    }
}
