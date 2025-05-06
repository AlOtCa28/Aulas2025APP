package Adaptadores


import Modelos.Aulas.Aula
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.aulas2025app.R

class AdaptadorProfesoresYEncargados(
    private var context: Context,
    private var datos: ArrayList<Aula>
) : RecyclerView.Adapter<AdaptadorProfesoresYEncargados.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_card_aula_profesor, parent, false)
        return MyViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val aula = datos[position]
        holder.descr.text = aula.nombreAula

        if (Parametros.Parametros.rolUsuarioLogeado == 3) {
            holder.btnEditarAula.isEnabled = false
            holder.btnEditarAula.setOnClickListener {
                Toast.makeText(context, "No tienes permiso para editar esta aula.", Toast.LENGTH_SHORT).show()
            }
        } else {
            holder.btnEditarAula.isEnabled = true
            holder.btnEditarAula.setOnClickListener {
                Toast.makeText(context, "Tienes permiso para editar esta aula.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var descr: TextView = itemView.findViewById<View>(R.id.txtNombre) as TextView
        var btnEditarAula: Button = itemView.findViewById<View>(R.id.btnEditarAula) as Button
    }

}