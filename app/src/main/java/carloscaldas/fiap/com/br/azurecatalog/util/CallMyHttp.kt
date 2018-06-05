package carloscaldas.fiap.com.br.azurecatalog.util

import android.os.Build
import android.util.Log
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.xml.datatype.DatatypeConstants.SECONDS


class CallMyHttp() {


    fun enableTls12OnPreLollipop(client: OkHttpClient.Builder): OkHttpClient.Builder {
        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
            try {
                val sc = SSLContext.getInstance("TLSv1.2")
                sc.init(null, null, null)
                client.sslSocketFactory(Tls12SocketFactory(sc.getSocketFactory()))

                val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build()

                val specs = ArrayList<ConnectionSpec>()
                specs.add(cs)
                specs.add(ConnectionSpec.COMPATIBLE_TLS)
                specs.add(ConnectionSpec.CLEARTEXT)

                client.connectionSpecs(specs)
            } catch (exc: Exception) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc)
            }

        }

        return client
    }


    private fun getNewHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .cache(null)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)

        return enableTls12OnPreLollipop(client).build()
    }


    val client = getNewHttpClient()

    fun executeReq(url: String) {

        val request = Request.Builder()
                .url(url)
                //.useTLS12()
                .build();
        println("------------------> Comeco da chamada RUN do Rest!  <------------------")


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("------------------> Deu erro no Rest!")
                println(e.message)
            }

            override fun onResponse(call: Call, response: Response) = println(response.body()?.string())
        })
    }
}


/*    public fun executereq(url: String) {
println("------------------> Comeco da chamada RUN do Rest!  <------------------")
val request = Request.Builder()
        .url(url)
        //.useTLS12()
        .build();

val response: Response = client.newCall(request).execute()
println(response.body()?.string())
println("------------------> Deu erro no Rest! <------------------")
*/
