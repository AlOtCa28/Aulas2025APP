package Adaptadores

import Modelo.Usuario.Usuario
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.aulas2025app.JEFE.JefeProfesores.DetallesUsuario.DetalleUsuarioActivity
import com.example.aulas2025app.JEFE.JefeProfesores.ProfesoresViewModel
import com.example.aulas2025app.R


class MiAdaptadorRV(
    private var context: Context,
    private var datos: ArrayList<Usuario>,
    private val onBorrarClick: (Usuario, Int) -> Unit,
    private val onVerDetallesClick: (Usuario) -> Unit  // nuevo callback
) : RecyclerView.Adapter<MiAdaptadorRV.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val usuario = datos[position]
        holder.nombre.text = usuario.nombre
        holder.email.text = usuario.email

        holder.itemView.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Opciones")
                .setMessage("¿Qué quieres hacer con ${usuario.nombre}?")
                .setPositiveButton("Ver Detalles") { dialog, _ ->
                    onVerDetallesClick(usuario)  // llamamos callback para abrir detalles
                    dialog.dismiss()
                }
                .setNegativeButton("Eliminar") { dialog, _ ->
                    onBorrarClick(usuario, position)
                    dialog.dismiss()
                }
                .setNeutralButton("Cancelar") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(context)
                .setTitle("Eliminar usuario")
                .setMessage("¿Quieres eliminar a ${usuario.nombre}?")
                .setPositiveButton("Sí") { dialog, _ ->
                    onBorrarClick(usuario, position)
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                .show()
            true
        }
    }

    override fun getItemCount(): Int = datos.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nombre: TextView = itemView.findViewById(R.id.txtNombre)
        var email: TextView = itemView.findViewById(R.id.txtNombreNuevo2)
    }
}
