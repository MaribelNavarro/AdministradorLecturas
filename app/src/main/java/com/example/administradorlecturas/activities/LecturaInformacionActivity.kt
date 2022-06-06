package com.example.administradorlecturas.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.administradorlecturas.data.Libro
import com.example.administradorlecturas.R
import com.example.administradorlecturas.databinding.ActivityLecturaInformacionBinding
import com.example.administradorlecturas.data.miSQLiteHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_lectura_informacion.*

//Activity para mostrar la información del libro seleccionado.
class LecturaInformacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLecturaInformacionBinding
    lateinit var lecturasDBHelper: miSQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lectura_informacion)
        binding = ActivityLecturaInformacionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Para la base de datos
        lecturasDBHelper = miSQLiteHelper(this)

        //Mostrar la flecha para regresar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val paginasLeidas = view.findViewById<TextView>(R.id.editTextPaginasLeidas)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        //Se obtiene la información del libro seleccionado en el recyclerView
        val libro = intent.getSerializableExtra("libro") as Libro

        //Se pasan los datos a los campos del layout activity_lectura_informacion
        Picasso.get().load(libro.urlImagenLibro).into(imagenInfo)
        textTituloInfo.text = getString(R.string.titulo_libro, libro.tituloLibro)
        textAutorInfo.text = getString(R.string.autor_libro, libro.autorLibro)
        textTipoInfo.text = getString(R.string.tipo_libro, libro.tipoLibro)
        paginasLeidas.text = libro.pagsLeidasLibro
        textPaginasInfo.text = libro.totalPagsLibro

        //mostrar el progreso de lectura en el progressBar
        progressBar.setProgress(obtenerProgreso(libro.totalPagsLibro,libro.pagsLeidasLibro))

        /** PARA BORRAR **/

        //variable para indicar la cantidad de datos borrados
        var cantidad = 0
        //Borrar la lectura si se presiona el botón para borrar
        binding.botonBorrar.setOnClickListener{

            cantidad = lecturasDBHelper.borrarDato(libro.idLibro)

            //Mandar mensaje cuando se haya borrado
            Toast.makeText(this, "Lecturas Borradas $cantidad",
                Toast.LENGTH_SHORT).show()

            //se cierra el activity y se regresa al mainActivity
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }//fin del setOnClickListener

        /** PARA EDITAR EL ESTADO SI SE PRESIONA EL BOTÓN PARA MARCAR COMO LEÍDO*/
        //Cambiar el estado a libro leído si el checkBox está presionado
        binding.botonAgregarLeido.setOnClickListener {

            var estado = "leido"
            //se cambia el número de páginas leídas al total de páginas del libro
            var paginasLeidas = libro.totalPagsLibro
            lecturasDBHelper.editarEstado(libro.idLibro, estado, paginasLeidas)
            Toast.makeText(this, "Se cambió a leído", Toast.LENGTH_SHORT).show()

        }//fin del setOnClickListener

        /** PARA EDITAR EL ESTADO SI SE PRESIONA EL BOTÓN PARA MARCAR COMO LEYENDO **/
        //Cambiar el estado a libro leído si el checkBox está presionado
        binding.botonAgregarLeyendo.setOnClickListener {

            var estado = "leyendo"
            lecturasDBHelper.editarEstado(libro.idLibro, estado, libro.pagsLeidasLibro)
            Toast.makeText(this, "Se cambió a leyendo", Toast.LENGTH_SHORT).show()

        }//fin del setOnClickListener

        /** PARA EDITAR EL NÚMERO DE PÁGINAS LEÍDAS **/
        //Esperar al que el botón de editar se presione
        binding.botonEditar.setOnClickListener {
            //Revisar que haya un valor en el campo de páginas leídas y que no sea mayor al número total
            //de páginas
            if(binding.editTextPaginasLeidas.text.isNotBlank() &&
                (binding.editTextPaginasLeidas.text.toString().toInt() <= binding.textPaginasInfo.text.toString().toInt())){

                var paginasLeidas = binding.editTextPaginasLeidas.text.toString()

                //llamar a la funcion editarPaginas
                lecturasDBHelper.editarPaginas(libro.idLibro, paginasLeidas)
                Toast.makeText(this, "Guardado",
                    Toast.LENGTH_SHORT).show()
                finish()
            }//fin del if
            else {
                Toast.makeText(this, "Escribe un número de páginas leídas válido",
                    Toast.LENGTH_LONG).show()
            }//fin del else
        }//fin del setOnClickListener

    }//fin del onCreate

    //Función para calcular el progreso de lectura
    private fun obtenerProgreso(totalPags: String, paginasLeidas: String): Int {

        val tpaginas = totalPags.toInt()
        val pagsleidas = paginasLeidas.toInt()
        var progreso = (pagsleidas * 100) / tpaginas

        return progreso
    }//fin de la función obtenerProgreso

    //Función para regresar al activity anterior
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        this.finish()
        return true
    }//fin de la función onSupportNavigateUp

}//fin del la clase