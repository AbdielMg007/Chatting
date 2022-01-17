package com.mg.chatting

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }


    fun registrar(view: View?) {
        if (email.text.isNotEmpty() && pass.text.isNotEmpty()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(), pass.text.toString()).addOnCompleteListener {
                if (it.isSuccessful){
                    showhome(it.result?.user?.email?: "", ProviderType.BASIC)
                }else{
                    showAlert()
                }
            }
        } else {
            showMensaje()
        }
    }

    fun acceder(view: View?) {
        if (email.text.isNotEmpty() && pass.text.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(), pass.text.toString()).addOnCompleteListener {
                if (it.isSuccessful){
                    showhome(it.result?.user?.email?: "", ProviderType.BASIC)
                }else{
                    showAlert()
                }
            }
        } else {
            showMensaje()
        }
    }

    private fun showMensaje(){
        val men = AlertDialog.Builder(this)
        men.setTitle("Error")
        men.setMessage("Llene todos los campos")
        men.setPositiveButton("Aceptar", null)
        val dial: AlertDialog = men.create()
        dial.show()
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se produjo un error autentificando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showhome(email:String, provider: ProviderType){
        val homeIntent = Intent(this, Menu::class.java).apply{
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }
}
