package Adaptadores


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_card_aula, parent, false)
        return MyViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val aula = datos[position]
        holder.descr.text = aula.nombreAula
        holder.profesor.text = aula.idEncargado.toString()

    }
    override fun getItemCount(): Int {
        return datos.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var descr: TextView = itemView.findViewById<View>(R.id.txtNombre) as TextView
        var profesor: TextView = itemView.findViewById<View>(R.id.txtProfe) as TextView
    }

}