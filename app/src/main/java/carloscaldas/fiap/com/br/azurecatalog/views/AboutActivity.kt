package carloscaldas.fiap.com.br.azurecatalog.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import carloscaldas.fiap.com.br.azurecatalog.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setListeners()


    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.buttonOk -> {
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
    }

    private fun setListeners(){
        buttonOk.setOnClickListener(this)
    }}
