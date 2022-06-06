package com.example.administradorlecturas.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.administradorlecturas.data.Libro
import com.example.administradorlecturas.databinding.ActivityNuevaLecturaBinding
import com.example.administradorlecturas.data.miSQLiteHelper

//Activity para agregar una nueva lectura
class NuevaLecturaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNuevaLecturaBinding
    lateinit var lecturasDBHelper: miSQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNuevaLecturaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        lecturasDBHelper = miSQLiteHelper(this)

        //Mostrar la flecha para regresar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Si el botón para agregar una nueva lectura en presionado se agregan los datos a la base de datos
        binding.botonAgregar.setOnClickListener {

            //Revisar que los campos no estén vacíos
            if (binding.editTextTitulo.text.isNotBlank() &&
                binding.editTextAutor.text.isNotBlank() &&
                binding.editTextTipo.text.isNotBlank() &&
                binding.editTextPaginas.text.isNotBlank() &&
                binding.editTextLeidas.text.isNotBlank() &&
                (binding.radioButtonLeido.isChecked || binding.radioButtonLeyendo.isChecked)) {

                //Revisar el estado del libro para guardarlo
                var estado = ""
                if (binding.radioButtonLeyendo.isChecked) {
                    estado = "leyendo"
                } else estado = "leido"

                var imagen = ""
                //Si no se indica la url de la imagen, se agrega una de ejemplo
                if (binding.editTextImagen.text.isBlank()) {
                    imagen =
                        "https://media.istockphoto.com/vectors/vector-icon-closed-book-with-bookmark-notepad-with-a-bookmark-cartoon-vector-id1252950800?b=1&k=6&m=1252950800&s=170667a&w=0&h=OTFZVU6cZp1IVbDsBhWFlgqZ4AKAlPQNIdyx8IyH79o="
                }//fin del if
                else {
                    //Se agrega la url indicada por el usuario
                    imagen = binding.editTextImagen.text.toString()
                }//fin del else

                //Antes de guardar los datos valida el número de páginas para que el número de páginas
                //leídas no sea mayor al número de páginas totales
                if((binding.editTextPaginas.text.toString().toInt() >= binding.editTextLeidas.text.toString().toInt())){
                    //Pasar los datos de tipo Libro
                    var libro = Libro(
                        tituloLibro = binding.editTextTitulo.text.toString(),
                        autorLibro = binding.editTextAutor.text.toString(),
                        tipoLibro = binding.editTextTipo.text.toString(),
                        urlImagenLibro = imagen,
                        estadoLibro = estado,
                        totalPagsLibro = binding.editTextPaginas.text.toString(),
                        pagsLeidasLibro = binding.editTextLeidas.text.toString()
                    )
                    //Ingresar los datos a la base de datos
                    lecturasDBHelper.insertarDato(libro)

                    //Mandar mensaje si se guardaron los datos correctamente
                    Toast.makeText(
                        this, "Guardado",
                        Toast.LENGTH_SHORT
                    ).show()
                    //Para cerrar el activity y regresar al anterior
                    finish()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }//fin del segundo if
                else {
                    //Mandar mensaje para validar el número de páginas leídas
                    Toast.makeText(
                        this, "Páginas leídas es mayor al total de páginas",
                        Toast.LENGTH_LONG
                    ).show()
                }//fin del segundo else

            }//fin del primer if
            else {
                //Mostrar mensaje si hay campos vacíos
                Toast.makeText(
                    this, "Tienes que llenar todos los campos",
                    Toast.LENGTH_LONG
                ).show()
            }//fin del primer else

        }//fin del setOnClickListener
    }//fin del onCreate

    //Función para regresar al activity anterior
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }//fin de la función onSupportNavigateUp
}//fin de la clase