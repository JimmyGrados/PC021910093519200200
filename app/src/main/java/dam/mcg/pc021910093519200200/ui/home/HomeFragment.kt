package dam.mcg.pc021910093519200200.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import dam.mcg.pc021910093519200200.databinding.FragmentRegistroBinding
import dam.mcg.pc021910093519200200.Registro

class RegistroFragment : Fragment() {

    private var _binding: FragmentRegistroBinding? = null
    private val binding get() = _binding!!

    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout usando ViewBinding
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        val view = binding.root

        // Inicializar Firebase Firestore
        firestore = FirebaseFirestore.getInstance()

        // Configurar el botón para guardar el registro al hacer clic
        binding.buttonSave.setOnClickListener {
            saveRecord()  // Llama a la función saveRecord cuando se hace clic en el botón
        }

        return view
    }

    private fun saveRecord() {
        val date = binding.editTextDate.text.toString()
        val description = binding.editTextDescription.text.toString()
        val amount = binding.editTextAmount.text.toString().toDoubleOrNull()

        if (date.isEmpty() || description.isEmpty() || amount == null) {
            Toast.makeText(context, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val registro = Registro(
            Fecha = date,
            Descripcion = description,
            Monto = amount
        )

        firestore.collection("clientes")
            .add(registro)
            .addOnSuccessListener {
                Toast.makeText(context, "Registro guardado con éxito", Toast.LENGTH_SHORT).show()
                clearFields()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun clearFields() {
        binding.editTextDate.text.clear()
        binding.editTextDescription.text.clear()
        binding.editTextAmount.text.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Liberar referencia al binding para evitar fugas de memoria
    }
}
