package me.muhammadfaisal.laker.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import me.muhammadfaisal.laker.R
import me.muhammadfaisal.laker.model.Users

class RegisterCompanyActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        if (p0 == buttonRegister){
            registerFunction()
        }
    }

    private fun registerFunction() {
        if (inputName.text.isEmpty() || inputAddress.text.isEmpty() || inputEmail.text.isEmpty() || inputPhone.text.isEmpty() || inputPassword.text.isEmpty() ){

            setFieldRequiredError()

        }else if(inputName.text.length < 3 || inputAddress.text.toString().toLowerCase() != "jakarta" || inputPassword.text.length < 6){

            setFieldUniversalError()

        }else{

            fromIsValid()

        }
    }

    private fun fromIsValid() {
        val email = inputEmail.text.toString()
        val password = inputPassword.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{task ->
            if (task.isSuccessful){
                registerSuccess()
            }else{
                registerFailed(task)
            }
        }
    }

    private fun registerFailed(task: Task<AuthResult>) {
        val exceptionOriginal = task.exception.toString().replace("com.google.firebase.auth.FirebaseAuthUserCollisionException:", "", false)

        Toast.makeText(this, getString(R.string.failed_messages) + exceptionOriginal, Toast.LENGTH_SHORT).show()
    }

    private fun registerSuccess() {

        val name = inputName.text.toString()
        val address =  inputAddress.text.toString()
        val email = inputEmail.text.toString()
        val phone =  inputPhone.text.toString()

        var users = Users(name , email , auth.currentUser!!.uid, "", "", phone, 1, 1)

        reference.child(auth.currentUser!!.uid).setValue(users).addOnCompleteListener{task ->
            if (task.isSuccessful){
                successAddNewCompany()
            }else{
                failedAddNewCompany()
            }
        }
    }

    private fun failedAddNewCompany() {
        Toast.makeText(this, "Failed Add Company", Toast.LENGTH_SHORT).show()
    }

    private fun successAddNewCompany() {
        Toast.makeText(this, "Success Add Company", Toast.LENGTH_SHORT).show()
    }

    private fun setFieldUniversalError() {
        inputAddress.error = getString(R.string.only_available_in_jakarta)
        inputName.error = getString(R.string.error_password_length)
        inputPassword.error = getString(R.string.error_password_length)
    }

    private fun setFieldRequiredError() {
        inputName.error = getString(R.string.field_is_required)
        inputAddress.error = getString(R.string.field_is_required)
        inputEmail.error = getString(R.string.field_is_required)
        inputPhone.error = getString(R.string.field_is_required)
        inputPassword.error = getString(R.string.field_is_required)
    }

    lateinit var inputName : EditText
    lateinit var inputAddress : EditText
    lateinit var inputEmail : EditText
    lateinit var inputPhone : EditText
    lateinit var inputPassword : EditText
    lateinit var buttonRegister : ImageView

    lateinit var auth : FirebaseAuth
    lateinit var reference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_company)

        supportActionBar!!.hide()

        initWidgets()
    }

    private fun initWidgets() {
        inputName = findViewById(R.id.inputName)
        inputAddress = findViewById(R.id.inputAddress)
        inputEmail = findViewById(R.id.inputEmail)
        inputPhone = findViewById(R.id.inputPhoneNumber)
        inputPassword  = findViewById(R.id.inputPassword)

        buttonRegister = findViewById(R.id.buttonRegisterCompany)

        auth = FirebaseAuth.getInstance()
        reference = FirebaseDatabase.getInstance().getReference("users")

        buttonRegister.setOnClickListener(this)
    }
}
