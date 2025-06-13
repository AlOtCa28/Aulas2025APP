package Adaptadores


import Modelo.Usuario.Usuario
import Modelos.Aulas.Aula
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aulas2025app.R

class AdaptadorAulas(
    private var context: Context,
    private var datos: ArrayList<Aula>
) : RecyclerView.Adapter<AdaptadorAulas.MyViewHolder>() {

    private var listaProfesores: List<Usuario> = emptyList()

    private var onLongClickListener: ((Aula) -> Unit)? = null
    private var onClickListener: ((Aula) -> Unit)? = null

    fun setProfesores(lista: List<Usuario>) {
        this.listaProfesores = lista
    }

    fun setOnLongClickListener(listener: (Aula) -> Unit) {
        this.onLongClickListener = listener
    }

    fun setOnClickListener(listener: (Aula) -> Unit) {
        this.onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_card_aula, parent, false)
        return MyViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val aula = datos[position]
        holder.descr.text = aula.nombreAula

        val encargado = listaProfesores.firstOrNull { it.id == aula.idEncargado }
        holder.profesor.text = encargado?.nombre ?: "Sin asignar"

        holder.itemView.setOnClickListener {
            onClickListener?.invoke(aula)
        }

        holder.itemView.setOnLongClickListener {
            onLongClickListener?.invoke(aula)
            true
        }
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var descr: TextView = itemView.findViewById(R.id.txtNombre)
        var profesor: TextView = itemView.findViewById(R.id.txtProfe)
    }
}

