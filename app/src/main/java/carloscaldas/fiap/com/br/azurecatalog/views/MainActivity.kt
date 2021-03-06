package carloscaldas.fiap.com.br.azurecatalog.views


import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView
import carloscaldas.fiap.com.br.azurecatalog.R
import carloscaldas.fiap.com.br.azurecatalog.business.PriorityBusiness
import carloscaldas.fiap.com.br.azurecatalog.contants.TaskConstants
import carloscaldas.fiap.com.br.azurecatalog.repository.PriorityCacheConstants
import carloscaldas.fiap.com.br.azurecatalog.util.CallMyHttp
import carloscaldas.fiap.com.br.azurecatalog.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import okhttp3.*
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mPriorityBusiness: PriorityBusiness


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        //drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        // Instancia variaveis
        mSecurityPreferences = SecurityPreferences(this)
        mPriorityBusiness = PriorityBusiness(this)

        loadPriorityCache()
        startDefaultFragment()
        formatUserName()
        formateDate()


        var mCallMyHttp = CallMyHttp()  //("https://api.github.com/users/Evin1-/repos")
        mCallMyHttp.executeReq("https://api.github.com/users/Evin1-/repos")
//        mCallMyHttp.executeReq("http://viacep.com.br")

    }


    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        var fragment: android.support.v4.app.Fragment? = null

        when (id) {
            R.id.nav_done -> fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.COMPLETE)
            R.id.nav_todo -> fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.TODO)
            R.id.nav_sobre -> {
                about()
                return true
            }
            R.id.nav_logout -> {
                handleLogout()
                return false
            }
        }

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun about() {
        try {
            startActivity(Intent(this, AboutActivity::class.java))
            finish()
        } catch (e: Exception) {
            println("--------********************************--------------->${e.message}")
        }
    }

    private fun startDefaultFragment() {
        val fragment: android.support.v4.app.Fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.COMPLETE)
        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }

    private fun loadPriorityCache() {
        PriorityCacheConstants.setCache(mPriorityBusiness.getList())
    }

    private fun handleLogout() {

        // Apagar os dados do usuario
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_ID)
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_NAME)
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_EMAIL)

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun formateDate() {
        val c = Calendar.getInstance()

        val days = arrayOf("Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sabado")
        val months = arrayOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro")

        val str = "${days[c.get(Calendar.DAY_OF_WEEK) - 1]}, ${c.get(Calendar.DAY_OF_MONTH)} de ${months[c.get(Calendar.MONTH)]}"
        textDateDescription.text = str
    }

    private fun formatUserName() {
        val str = "Hello, ${mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)}!"
        textHello.text = str

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        val header = navigationView.getHeaderView(0)

        val name = header.findViewById<TextView>(R.id.textName)
        val email = header.findViewById<TextView>(R.id.textEmail)
        name.text = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)
        email.text = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_EMAIL)
    }
}
