package com.example.administradorlecturas.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.example.administradorlecturas.data.Libro

//Base de datos
class miSQLiteHelper(context: Context) : SQLiteOpenHelper(context, "lecturas", null, 2) {

    override fun onCreate(db: SQLiteDatabase?) {
        //Crear la tabla
        val ordenCreacion = "CREATE TABLE libro " +
                "(_id INTEGER PRIMARY KEY, " +
                "libroTitulo VARCHAR(30) NOT NULL," +
                "libroAutor VARCHAR(30) NOT NULL," +
                "libroTipo VARCHAR(30) NOT NULL," +
                "libroImagen VARCHAR(200) NOT NULL," +
                "libroPaginas VARCHAR(10) NOT NULL," +
                "libroPaginasLeidas VARCHAR(10) NOT NULL," +
                "libroEstado VARCHAR(10) NOT NULL)"
        db!!.execSQL(ordenCreacion)
    }//fin del onCreate

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Para borrar la tabla libro
        val ordenBorrado = "DROP TABLE IF EXISTS libro"
        db!!.execSQL(ordenBorrado)
        onCreate(db)
    }//fin del onUpgrade

    //Función para añadir los datos
    fun insertarDato(libro: Libro) {
        val datos = contentValuesOf()
        //Añadir los datos mediante clave valor
        datos.put("_id", libro.idLibro)
        datos.put("libroTitulo", libro.tituloLibro)
        datos.put("libroAutor", libro.autorLibro)
        datos.put("LibroTipo", libro.tipoLibro)
        datos.put("libroImagen", libro.urlImagenLibro)
        datos.put("libroPaginas", libro.totalPagsLibro)
        datos.put("libroPaginasLeidas", libro.pagsLeidasLibro)
        datos.put("libroEstado", libro.estadoLibro)

        //Guardar los datos
        val db = this.writableDatabase
        db.insert("libro", null, datos)
        //Cerrar el acceso a la base de datos despues de guardar los datos
        db.close()

    }//fin de la función insertarDato

    /**Función para borrar lecturas**/

    fun borrarDato(_id: Int): Int {
        //La función espera un array de datos, por lo que se crea un array de un solo datos
        val args = arrayOf(_id.toString())

        //Se borra la lectura de acuerdo a su id
        val db = this.writableDatabase
        val borrado = db.delete("libro", "_id = ?", args)
        //Cerrar el acceso a la base de datos despues de borrar los datos
        db.close()
        return borrado
    }//fin de la funcion borrarDato

    /**Funcion para editar el estado del libro a leído**/

    fun editarEstado(_id: Int, estado: String, paginas: String) {
        val args = arrayOf(_id.toString())

        val datos = contentValuesOf()
        //Añadir los datos mediante clave valor
        datos.put("libroPaginasLeidas", paginas)
        datos.put("libroEstado", estado)

        //Se actualizan los datos
        val db = this.writableDatabase
        db.update("libro", datos,"_id = ?", args)
        //Cerrar el acceso a la base de datos despues de actualizar los datos
        db.close()
    }//fin de la funcion editarEstado

    /**Funcion para editar el número de páginas leídas**/

    fun editarPaginas(_id: Int, leidas: String) {
        val args = arrayOf(_id.toString())

        val datos = contentValuesOf()
        //Añadir los datos mediante clave valor
        datos.put("libroPaginasLeidas", leidas)

        //Se actualizan los datos
        val db = this.writableDatabase
        db.update("libro", datos,"_id = ?", args)
        //Cerrar el acceso a la base de datos despues de actualizar los datos
        db.close()
    }//fin de la función editarPaginas
}//fin de la clase