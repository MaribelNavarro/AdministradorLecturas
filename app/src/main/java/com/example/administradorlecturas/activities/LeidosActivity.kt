package com.example.administradorlecturas.activities

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.administradorlecturas.data.Libro
import com.example.administradorlecturas.R
import com.example.administradorlecturas.adapters.LeidosAdapter
import com.example.administradorlecturas.databinding.ActivityLeidosBinding
import com.example.administradorlecturas.data.miSQLiteHelper
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import kotlinx.android.synthetic.main.activity_leidos.*

//Activity para mostrar todos los libro que ya se leyerón
class LeidosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeidosBinding
    lateinit var lecturasDBHelper: miSQLiteHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeidosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Para seleccionar las lecturas terminadas
        lecturasDBHelper = miSQLiteHelper(this)
        val db: SQLiteDatabase? = lecturasDBHelper.readableDatabase
        val cursor = db!!.rawQuery(
            "SELECT * FROM libro WHERE libroEstado LIKE 'leido'",
            null
        )

        binding.bottomNavigation.findViewById<BottomNavigationMenuView>(R.id.bottom_navigation)
        //Para idicar que el item seleccionado es el de leidos
        binding.bottomNavigation.setSelectedItemId(R.id.leidos)

        //Se mandan los datos de las lecturas al adaptador
        recycler_view_leidos.layoutManager = LinearLayoutManager(this)
        recycler_view_leidos.adapter = LeidosAdapter(generarDatosDeLecturas(cursor),this)

        //Acceder a la barra de navegación inferior
        binding.bottomNavigation.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.inicio -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }
                R.id.leyendo -> {
                    val intent = Intent(this, LeyendoActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }
                R.id.sugerencias -> {
                    val intent = Intent(this, SugerenciasActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }

            }//fin del when
        }//fin del setOnNavigationItemReselectedListener

    }//fin del onCreate

    //Función que regresa un ArrayList con los datos de las lecturas
    private fun generarDatosDeLecturas(cursor : Cursor) : ArrayList<Libro>{
        var listaLecturas = ArrayList<Libro>()
        //Se utiliza un cursor para moverse en la base de datos.
        //Se añaden los libros a la listaLibros mientras el cursor siga avanzando.
        if (cursor.moveToFirst()) {
            do {
                listaLecturas.add( Libro(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(7),
                    cursor.getString(5), cursor.getString(6)
                )
                )
            } while (cursor.moveToNext())
        }//fin del if
        else {
            //Mensaje si no se encuentra nada en la base de datos
            Toast.makeText(this, "No hay lecturas terminadas",
                Toast.LENGTH_LONG).show()
        }//fin del else

        return listaLecturas
    }//fin del la función generarDatosDeLecturas
}//fin de la clase