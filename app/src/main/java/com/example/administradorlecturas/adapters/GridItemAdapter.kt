package com.example.administradorlecturas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.administradorlecturas.R
import com.squareup.picasso.Picasso

//Adaptador para mostrar las lectura recomendas en forma de cuadricula
class GridItemAdapter(val cardTitulos : Array<String>, val cardImagenes: Array<String>): RecyclerView.Adapter<GridItemAdapter.ViewHolder>() {

    //Se crea la clase ViewHolder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //se crean las variables para los campos del grid_item_view
        val cardImagen : ImageView = itemView.findViewById(R.id.cardImagen)
        val cardTitulo : TextView = itemView.findViewById(R.id.cardTextTitulo)
    }//fin de la clase ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item_view,parent,false)
        return ViewHolder(view)
    }//fin del onCreateViewHolder

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Para pasar las imagenes al cardImage
        Picasso.get().load(cardImagenes[position]).into(holder.cardImagen)
        //Para pasar los titulos al cardTextTitulo
        holder.cardTitulo.text = cardTitulos[position]
    }//fin del onBindViewHolder

    override fun getItemCount(): Int {
        //Retornamos el tamaño del array utilizando cardTitulos
        return cardTitulos.size
    }//fin del getItemCount
}//fin de la clase