package Adaptadores

import Modelo.Usuario.Usuario
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.aulas2025app.JEFE.JefeProfesores.ProfesoresViewModel
import com.example.aulas2025app.R


class MiAdaptadorRV(
    private var context: Context,
    private var datos: ArrayList<Usuario>
) : RecyclerView.Adapter<MiAdaptadorRV.MyViewHolder>() {

    private val selectedItems = mutableListOf<Usuario>()
    private val viewModel = ViewModelProvider(context as AppCompatActivity).get(
        ProfesoresViewModel::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val usuario = datos[position]
        holder.nombre.text = usuario.nombre
        holder.email.text = usuario.email
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nombre: TextView = itemView.findViewById<View>(R.id.txtNombre) as TextView
        var email: TextView = itemView.findViewById<View>(R.id.txtNombreNuevo2) as TextView
    }
}