package ittepic.edu.mx.tpdm_u3_practica1_15400776

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main4.*

class Main4Activity : AppCompatActivity() {

    var DescripcionA: EditText? = null
    var MontoA: EditText? = null
    var FechaVencimientoA: EditText? = null
    var PagadoA: RadioButton? = null
    var id = ""
    // dclarando objeto FIREBASE FIRESTONE
    var baseRemota = FirebaseFirestore.getInstance()
    var registros = ArrayList<String>()
    var keys = ArrayList<String>()

    var btActualizar: Button? = null
    var btnregresar: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        DescripcionA = findViewById(R.id.descripcionActualizar)
        MontoA = findViewById(R.id.montoActualizar)
        FechaVencimientoA = findViewById(R.id.fechaVencimientoActualizar)
        PagadoA = findViewById(R.id.pagadoActualizar)

        btActualizar = findViewById(R.id.btnactualizar)
        btnregresar = findViewById(R.id.btnregresarActualizar)

        id = intent.extras?.getString("id").toString()!!

        baseRemota.collection("recibos")
            .document(id)
            .get()
            .addOnSuccessListener {
                DescripcionA?.setText(it.getString("descripcion"))
                FechaVencimientoA?.setText(it.getString("fechaVencimiento"))
                MontoA?.setText(it.getDouble("monto").toString())
            }
            .addOnFailureListener {
                DescripcionA?.setText("NULL")
                FechaVencimientoA?.setText("NULL")
                MontoA?.setText("No se encontro dato ")

                DescripcionA?.isEnabled = false
                FechaVencimientoA?.isEnabled = false
                MontoA?.isEnabled = false
                btActualizar?.isEnabled = false
            }

        btActualizar?.setOnClickListener {
            var Actualizar = hashMapOf(
                "pagado" to PagadoA?.isChecked.toString().toBoolean(),
                "descripcion" to DescripcionA?.text.toString(),
                "monto" to MontoA?.text.toString().toDouble(),
                "fechaVencimiento" to FechaVencimientoA?.text.toString()
            )
            baseRemota.collection("recibos").document(id)
                .set(Actualizar)
                .addOnSuccessListener {
                    limpiarCampos()
                    Toast.makeText(this, "Se Actualizo", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error No Se Logro Actualizar", Toast.LENGTH_LONG).show()
                }
        }

        btnregresar?.setOnClickListener {
            finish()
        }
    }
    fun limpiarCampos() {
        descripcionActualizar?.setText("")
        montoActualizar?.setText("")
        fechaVencimientoActualizar?.setText("")
        pagadoActualizar?.isChecked()
    }
}

