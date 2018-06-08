package carloscaldas.fiap.com.br.azurecatalog.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import carloscaldas.fiap.com.br.azurecatalog.R

class SplashActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 2000 //2 seconds


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        loadAnim()
        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    fun loadAnim(){

        val anim = AnimationUtils.loadAnimation(this,

                R.anim.animacao_splash)
        anim.reset()

        //Pegando o nosso objeto criado no layout
        val iv = findViewById<ImageView>(R.id.splash)
        if (iv != null) {
            iv.clearAnimation()
            iv.startAnimation(anim)
        }


    }

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}
