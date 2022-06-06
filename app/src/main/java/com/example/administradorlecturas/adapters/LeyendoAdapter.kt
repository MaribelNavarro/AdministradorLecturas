package com.example.administradorlecturas.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.administradorlecturas.data.Libro
import com.example.administradorlecturas.R
import com.example.administradorlecturas.activities.LecturaInformacionActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_leyendo.view.*

//Adaptador para mostrar los datos de los libro que se están leyendo
class LeyendoAdapter(private var listaLecturas : ArrayList<Libro>, private var contexto : Context) : RecyclerView.Adapter<LeyendoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeyendoAdapter.ViewHolder {
        //Para que cree el diseño a partir del item_view_leyendo
        return LeyendoAdapter.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_leyendo, parent, false), contexto)
    }//fin del onCreateViewHolder

    override fun getItemCount(): Int {
        //Retornamos el tamaño de la lista para decirle a la clase cuantos elemento va a tener la lista
        return listaLecturas.size
    }//fin del getItemCount

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Se llama a la función bind y se pasa la lista de lecturas
        holder.bind(listaLecturas[position])
    }//fin del onBindViewHolder

    //Se crea la clase ViewHolder
    class ViewHolder(var vista: View, var contexto : Context) : RecyclerView.ViewHolder(vista){
        fun bind(libro : Libro){
            //Se pasan los datos a los campos de la vista
            vista.textTitulo.text = libro.tituloLibro
            vista.textAutor.text = libro.autorLibro
            Picasso.get().load(libro.urlImagenLibro).into(vista.imagenLibro)

            //mostrar el progreso de lectura en el progressBar
            vista.progressBarLeyendo.setProgress(obtenerProgreso(libro.totalPagsLibro,libro.pagsLeidasLibro))

            //Cuando se haga clic en la vista, se mandan todos los datos de la lectura
            vista.setOnClickListener {
                //Se hace el intent al LecturaInformacionActivity
                contexto.startActivity(Intent(contexto, LecturaInformacionActivity::class.java).putExtra("libro" , libro))
            }//fin del setOnClicListener
        }//fin de la función bind


        //Función para calcular el progreso de lectura
        private fun obtenerProgreso(totalPags: String, paginasLeidas: String): Int {

            val tpaginas = totalPags.toInt()
            val pagsleidas = paginasLeidas.toInt()
            var progreso = (pagsleidas * 100) / tpaginas

            return progreso
        }//fin de la función obtenerProgreso

    }//fin de la clase ViewHolder

}//fin de la clase