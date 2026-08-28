package com.In_Talent_App

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.In_Talent_App.api.RetrofitClient
import com.In_Talent_App.model.LoginRequest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tvIrARegistro = findViewById<TextView>(R.id.tvIrARegistro)
        tvIrARegistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (email.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            btnLogin.isEnabled = false

            lifecycleScope.launch {
                try {
                    val request = LoginRequest(email, contrasena)
                    val response = RetrofitClient.instance.login(request)

                    progressBar.visibility = View.GONE
                    btnLogin.isEnabled = true

                    if (response.isSuccessful && response.body() != null) {
                        val authData = response.body()!!
                        val token = authData.token

                        // 1. Guardar el token localmente
                        val sharedPref = getSharedPreferences("InTalentPrefs", MODE_PRIVATE)
                        sharedPref.edit().putString("JWT_TOKEN", token).apply()

                        Toast.makeText(this@MainActivity, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()

                        // 2. ORDEN DE NAVEGACIÓN: Abrir HomeActivity
                        val intent = Intent(this@MainActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish() // Cierra el Login para no regresar al presionar Atrás

                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Error: Credenciales incorrectas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    progressBar.visibility = View.GONE
                    btnLogin.isEnabled = true
                    Toast.makeText(
                        this@MainActivity,
                        "Error de conexión: ${e.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}