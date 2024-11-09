package dam.mcg.pc021910093519200200

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import model.UserModel

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val btSaveRegister: Button = findViewById(R.id.btSaveRegister)
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        btSaveRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            //Generar el usuario en FirebaseAuth
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task->
                    if(task.isSuccessful){
                        val user: FirebaseUser? = auth.currentUser
                        val uid = user?.uid
                        val userModel = UserModel( email, uid)

                        db.collection("users")
                            .add(userModel)
                            .addOnCompleteListener{
                                Snackbar
                                    .make(findViewById(android.R.id.content),
                                        "Registro exitoso",
                                        Snackbar.LENGTH_SHORT).show()
                                finish()
                            }.addOnFailureListener{error->
                                Snackbar
                                    .make(findViewById(android.R.id.content),
                                        "Ocurrió un error al registrar el usuario: ${error.message}",
                                        Snackbar.LENGTH_SHORT).show()
                            }
                    }
                }.addOnFailureListener{ error->
                    Snackbar
                        .make(findViewById(android.R.id.content),
                            "Ocurrió un error al registrar el usuario: ${error.message}",
                            Snackbar.LENGTH_SHORT).show()
                }
        }



    }
    }
