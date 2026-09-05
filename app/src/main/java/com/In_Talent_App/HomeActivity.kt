package com.In_Talent_App

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.In_Talent_App.api.RetrofitClient
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var rvServices: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnLogout = findViewById<Button>(R.id.btnLogout)
        rvServices = findViewById(R.id.rvServices)
        progressBar = findViewById(R.id.progressBar)

        rvServices.layoutManager = LinearLayoutManager(this)

        // Lógica para cerrar sesión
        btnLogout.setOnClickListener {
            val sharedPref = getSharedPreferences("InTalentPrefs", MODE_PRIVATE)
            sharedPref.edit().clear().apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Cargar los servicios desde la API
        fetchServices()
    }

    private fun fetchServices() {
        val sharedPref = getSharedPreferences("InTalentPrefs", MODE_PRIVATE)
        val token = sharedPref.getString("JWT_TOKEN", "") ?: ""

        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getServices("Bearer $token")
                progressBar.visibility = View.GONE

                if (response.isSuccessful && response.body() != null) {
                    val services = response.body()!!
                    rvServices.adapter = ServiceAdapter(services)
                } else {
                    Toast.makeText(this@HomeActivity, "Error al cargar servicios", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@HomeActivity, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}