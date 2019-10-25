package ittepic.edu.mx.tpdm_u3_practica1_15400776

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    var btnnuevoRecibo: Button?=null
    var btnverLista: Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnnuevoRecibo = findViewById(R.id.btnnuevoRecibo)
        btnverLista = findViewById(R.id.btnverLista)

        btnnuevoRecibo?.setOnClickListener {
            var VentanaInsertar = Intent(this, Main2Activity::class.java)
            startActivity(VentanaInsertar)

        }
        btnverLista?.setOnClickListener {
            var Ventanalista = Intent(this, Main3Activity::class.java)
            startActivity(Ventanalista)
}



    }

}