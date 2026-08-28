package com.In_Talent_App

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.In_Talent_App.api.RetrofitClient
import com.In_Talent_App.model.RegisterRequest
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etEmail = findViewById<EditText>(R.id.etEmailReg)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val etContrasena = findViewById<EditText>(R.id.etContrasenaReg)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val pbRegister = findViewById<ProgressBar>(R.id.pbRegister)

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor llena los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            pbRegister.visibility = View.VISIBLE
            btnRegistrar.isEnabled = false

            lifecycleScope.launch {
                try {
                    val request = RegisterRequest(
                        nombre = nombre,
                        email = email,
                        contrasena = contrasena,
                        telefono = if (telefono.isNotEmpty()) telefono else null
                    )

                    val response = RetrofitClient.instance.registrar(request)

                    pbRegister.visibility = View.GONE
                    btnRegistrar.isEnabled = true

                    if (response.isSuccessful && response.body() != null) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "¡Usuario registrado con éxito! Inicia sesión.",
                            Toast.LENGTH_LONG
                        ).show()

                        // Regresar a la pantalla de Login (MainActivity)
                        finish()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(
                            this@RegisterActivity,
                            "Código ${response.code()}: ${errorBody ?: "Error en el backend"}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: Exception) {
                    pbRegister.visibility = View.GONE
                    btnRegistrar.isEnabled = true
                    Toast.makeText(
                        this@RegisterActivity,
                        "Error de conexión: ${e.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}