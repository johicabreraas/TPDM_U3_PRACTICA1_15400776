package ittepic.edu.mx.tpdm_u3_practica1_15400776

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class Main2Activity : AppCompatActivity() {

    var Descripcion: EditText? = null
    var Monto: EditText? = null
    var FechaVencimiento: EditText? = null
    var Pagado: RadioButton?=null

    // dclarando objeto FIREBASE FIRESTONE
    var baseRemota = FirebaseFirestore.getInstance()
    var registros = ArrayList<String>()
    var Keys = ArrayList<String>()

    var btInsertar: Button?=null
    var btnregresar: Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        Descripcion = findViewById(R.id.descripcion)
        Monto = findViewById(R.id.monto)
        FechaVencimiento = findViewById(R.id.fechaVencimiento)
        Pagado = findViewById(R.id.pagado)

        btInsertar = findViewById(R.id.btninsertar)
        btnregresar = findViewById(R.id.btnregresar)



        btInsertar?.setOnClickListener {
            var insertar = hashMapOf(
                "descripcion" to Descripcion?.text.toString(),
                "monto" to Monto?.text.toString().toDouble(),
                "fechaVencimiento" to FechaVencimiento?.text.toString(),
                "pagado" to Pagado?.isChecked.toString().toBoolean()
            )
            baseRemota.collection("recibos").add(insertar as Map<String,Any>)

                .addOnSuccessListener {
                    Toast.makeText(this,"Se inserto correctamente", Toast.LENGTH_LONG).show()
                    limpiarCampos()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Error!. No se inserto"+it.message, Toast.LENGTH_LONG).show()
                }

        }

        btnregresar?.setOnClickListener {
            /*var VentanaMenu = Intent(this, MainActivity::class.java)
            startActivity(VentanaMenu)*/
            finish()
        }

    }
    fun limpiarCampos(){
        Descripcion?.setText("")
        Monto?.setText("")
        FechaVencimiento?.setText("")
        Pagado?.isChecked()
    }
}