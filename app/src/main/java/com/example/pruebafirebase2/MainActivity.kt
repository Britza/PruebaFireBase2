package com.example.pruebafirebase2

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    //Declaramos la variable auth
    private lateinit var auth: FirebaseAuth

    //Declaramos las variables de email, contraseña, registrar e iniciar
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var registrar: Button
    lateinit var iniciar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicializamos las variables creadas anteriormente
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        registrar = findViewById(R.id.registrar)
        iniciar = findViewById(R.id.iniciar)


        //Inicializamos Fire base auth
        auth = Firebase.auth


        //Hacemos la llamada para el metodo de crear cuenta
        registrar.setOnClickListener {
            createAccount(email.text.toString(), password.text.toString())
        }


        //Hacemos la llamada para el metodo de iniciar sesión
        iniciar.setOnClickListener {
            signIn(email.text.toString(), password.text.toString())
        }

    }

    /**
     * Método que sirve para crear una cuenta a través de un email y contraseña
     */

    private fun createAccount(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Empezamos creando un usuario con email.
                    Log.d(TAG, "createUserWithEmail:success")
                    Log.d("estado", "usuario registrado")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // Si el inicio de sesión falla, enviamos un mensaje al usuario.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Log.d("estado", "usuario NO registrado")

                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }

    }

    private fun updateUI(user: FirebaseUser?) {
        Log.d("estado", "" + auth.currentUser?.uid)
    }

    /**
     * Método que sirve para iniciar sesión a través de un email y contraseña
     */
    private fun signIn(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Iniciamos sesion con la información del usuario.
                    Log.d(TAG, "signInWithEmail:success")
                    Log.d("estado", "inicio de sesión correcto")

                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    //Si los datos no son correctos, envia un mensaje al usuario,
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Log.d("estado", "No se puedo iniciar sesion")

                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

}