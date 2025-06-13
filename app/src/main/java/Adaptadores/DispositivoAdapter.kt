package Adaptadores

import Modelos.Dispositivo.Dispositivo
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aulas2025app.R

class DispositivoAdapter(
    private val context: Context,
    private val listaDispositivos: List<Dispositivo>,
    private val onItemClick: (Dispositivo) -> Unit,      // click normal
    private val onItemLongClick: (Dispositivo) -> Unit  // click largo
) : RecyclerView.Adapter<DispositivoAdapter.DispositivoViewHolder>() {

    inner class DispositivoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCodigo: TextView = itemView.findViewById(R.id.txtCodigo)
        val txtDescripcion: TextView = itemView.findViewById(R.id.txtDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DispositivoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_dispositivo, parent, false)
        return DispositivoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DispositivoViewHolder, position: Int) {
        val dispositivo = listaDispositivos[position]
        holder.txtCodigo.text = dispositivo.codigo
        holder.txtDescripcion.text = dispositivo.descripcion

        // Click normal
        holder.itemView.setOnClickListener {
            onItemClick(dispositivo)
        }

        // Click largo
        holder.itemView.setOnLongClickListener {
            onItemLongClick(dispositivo)
            true
        }
    }

    override fun getItemCount(): Int = listaDispositivos.size
}

