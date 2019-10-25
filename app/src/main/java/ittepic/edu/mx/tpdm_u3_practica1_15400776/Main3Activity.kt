package ittepic.edu.mx.tpdm_u3_practica1_15400776

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore

class Main3Activity : AppCompatActivity() {

    var DescripcionA: EditText? = null
    var MontoA: EditText? = null
    var FechaVencimientoA: EditText? = null
    var PagadoA: RadioButton?=null
    var lista: ListView?=null

    // dclarando objeto FIREBASE FIRESTONE
    var baseRemota = FirebaseFirestore.getInstance()
    var registros = ArrayList<String>()
    var keys = ArrayList<String>()

    var btnregresar: Button?=null

    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        btnregresar = findViewById(R.id.btnregresarLista)
        lista= findViewById(R.id.lista)
        btnregresar?.setOnClickListener {
            /*var VentanaMenu = Intent(this, MainActivity::class.java)
            startActivity(VentanaMenu)*/
            finish()
        }

        baseRemota.collection("recibos")
            .addSnapshotListener { querySnapshot, e ->
                if (e != null) {
                    Toast.makeText(this, "No hay acceso a los datos", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }
                registros.clear()
                keys.clear()
                for (documents in querySnapshot!!) {
                    var cadena =
                        "${"Descripcion: " + documents.getString("descripcion")}\n${"Fecha : " + documents.getString(
                            "fechaVencimiento"
                        )}\n${"Monto : " + documents.getDouble("monto")}" +
                                "\n${"Pagado: " + documents.getBoolean("pagado")}"
                    registros.add(cadena)
                    keys.add(documents.id)
                }
                if (registros.size == 0) {
                    registros.add("no hay datos Capturados")
                }
                var adapter =
                    ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, registros)
                lista?.adapter = adapter
            }
        lista?.setOnItemClickListener { parent, view, position, id ->
            AlertDialog.Builder(this).setTitle("Atencion")
                .setMessage("Que desea hacer con\n ${registros.get(position)}?")
                .setPositiveButton("Eliminar") { dialogInterface, wich ->
                    baseRemota.collection("recibos").document(keys.get(position)).delete()
                        .addOnSuccessListener {
                            Toast.makeText(this, "se pudo eliminar", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "No se pudo Eliminar", Toast.LENGTH_SHORT).show()
                        }
                }
                .setNegativeButton("Actualizar") { dialogInterface, wich ->
                    var nuevaVentana =
                        Intent(this, Main4Activity::class.java)
                    nuevaVentana.putExtra("id", keys.get(position))
                    startActivity(nuevaVentana)
                }
                .setNeutralButton("Cancelar") { dialogInterface, wich -> }
                .show()
        }

    }

    fun limpiarcampos() {
        DescripcionA?.setText("")
        MontoA?.setText("")
        FechaVencimientoA?.setText("")
    }
}