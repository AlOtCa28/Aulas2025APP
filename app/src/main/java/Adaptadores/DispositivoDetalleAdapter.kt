package Adaptadores

import Modelos.Dispositivo.Dispositivo
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aulas2025app.R

class DispositivoDetalleAdapter(
    private val onClick: (Dispositivo) -> Unit
) : ListAdapter<Dispositivo, DispositivoDetalleAdapter.DispositivoViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DispositivoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dispositivo_card, parent, false)
        return DispositivoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DispositivoViewHolder, position: Int) {
        val dispositivo = getItem(position)
        Log.d("DEBUG_AULA", "Bind en posición $position: ${dispositivo.codigo} - ${dispositivo.descripcion}")
        holder.bind(dispositivo)
    }

    inner class DispositivoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: CardView = itemView.findViewById(R.id.cardViewDispositivo)
        private val tvCodigo: TextView = itemView.findViewById(R.id.tvCodigoDispositivo)
        private val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcionDispositivo)

        fun bind(dispositivo: Dispositivo) {
            Log.d("DEBUG_AULA", "Bind dispositivo: ${dispositivo.codigo}")
            tvCodigo.text = dispositivo.codigo
            tvDescripcion.text = dispositivo.descripcion

            cardView.setOnClickListener {
                onClick(dispositivo)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Dispositivo>() {
        override fun areItemsTheSame(oldItem: Dispositivo, newItem: Dispositivo): Boolean {
            return oldItem.codigo == newItem.codigo
        }

        override fun areContentsTheSame(oldItem: Dispositivo, newItem: Dispositivo): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemCount(): Int {
        val count = super.getItemCount()
        Log.d("DEBUG_AULA", "Adapter getItemCount: $count")
        return count
    }
}

