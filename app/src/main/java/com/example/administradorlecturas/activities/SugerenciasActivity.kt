package com.example.administradorlecturas.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.administradorlecturas.adapters.GridItemAdapter
import com.example.administradorlecturas.R
import com.example.administradorlecturas.databinding.ActivitySugerenciasBinding
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import kotlinx.android.synthetic.main.activity_sugerencias.*

//Activity para mostrar sugerencias de lectura
class SugerenciasActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySugerenciasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySugerenciasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Se crean las variables para obtener los recursos de los array cardTitulos y  cardImagenes
        val cardTitulos : Array<String> = resources.getStringArray(R.array.cardTitulos)
        val cardImagenes : Array<String> = resources.getStringArray(R.array.cardImagenes)

        //Se crea el Adapter
        val adapter = GridItemAdapter(cardTitulos, cardImagenes)
        //Se mostrarán dos items por fila
        val gridLayout = GridLayoutManager(this,2)
        gridItem.layoutManager = gridLayout
        gridItem.adapter = adapter

        binding.bottomNavigation.findViewById<BottomNavigationMenuView>(R.id.bottom_navigation)
        //Para idicar que el item seleccionado es el de sugerencias
        binding.bottomNavigation.setSelectedItemId(R.id.sugerencias)

        //Acceder a la barra de navegación inferior
        binding.bottomNavigation.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.inicio -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }
                R.id.leidos -> {
                    val intent = Intent(this, LeidosActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }
                R.id.leyendo -> {
                    val intent = Intent(this, LeyendoActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }

            }//fin del when
        }//fin del setOnNavigationItemReselectedListener
    }//fin del onCreate
}//fin de la clase