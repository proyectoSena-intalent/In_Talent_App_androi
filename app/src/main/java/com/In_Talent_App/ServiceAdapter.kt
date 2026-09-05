package com.In_Talent_App

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.In_Talent_App.model.ServiceResponse
import java.text.NumberFormat
import java.util.Locale

class ServiceAdapter(private val services: List<ServiceResponse>) :
    RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    class ServiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val containerGradient: LinearLayout = view.findViewById(R.id.containerGradient)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]

        holder.tvTitle.text = service.title ?: "Sin título"
        holder.tvCategory.text = service.category ?: "General"
        holder.tvDescription.text = service.description ?: "Sin descripción disponible"

        // Formato de moneda
        val format = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
        val precioFormateado = format.format(service.price ?: 0.0)
        holder.tvPrice.text = "COP $precioFormateado"

        // Asignación dinámica del fondo según la categoría
        val bgDrawable = when (service.category?.lowercase()?.trim()) {
            "tecnología", "tecnologia" -> R.drawable.bg_gradient_orange
            "negocios", "consultoría" -> R.drawable.bg_gradient_green
            else -> R.drawable.bg_gradient_blue
        }

        holder.containerGradient.setBackgroundResource(bgDrawable)
    }

    override fun getItemCount(): Int = services.size
}