package es.elemer.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.URL
import java.net.URLConnection


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val thread = Thread {
                try {

                    val url = URL("https://" + getString(R.string.host) + "/carmesyes/wakeup")
                    val userPassword: String = getString(R.string.user) + ":" + getString(R.string.password)
                    val encoding : String = Base64.encodeToString(userPassword.toByteArray(), Base64.DEFAULT)
                    val uc: URLConnection = url.openConnection()
                    uc.setRequestProperty("Authorization", "Basic $encoding")
                    uc.connect()
                    val response = uc.inputStream.bufferedReader().readText()
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(applicationContext,response,Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            thread.start()
        }
    }
}