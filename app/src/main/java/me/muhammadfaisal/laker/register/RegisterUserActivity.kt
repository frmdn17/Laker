package me.muhammadfaisal.laker.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import me.muhammadfaisal.laker.R
import me.muhammadfaisal.laker.helper.UniversalHelper
import me.muhammadfaisal.laker.model.Users

class RegisterUserActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        if (p0 == buttonRegister){
            registerNewJobSeeker()
        }
    }

    private fun registerNewJobSeeker() {
        if(inputEmail.text.isEmpty() || inputName.text.isEmpty() || inputPassword.text.isEmpty()){

            setInputRequiredError()

        }else if(inputPassword.text.length < 6){

            setInputLengthError()

        }else {
            var email = inputEmail.text.toString()
            var password = inputPassword.text.toString()

            helper.madeNoTouchable(this)

            lottieLoading.visibility = View.VISIBLE


            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task->
                if (task.isSuccessful){

                    helper.clearNoTouchable(this@RegisterUserActivity)

                    lottieLoading.visibility = View.GONE

                    methodSuccessRegister()

                }else{

                    methodFailedRegister(task)

                }
            }
        }
    }

    private fun setInputLengthError() {
        inputPassword.error = getString(R.string.error_password_length)
    }

    private fun setInputRequiredError() {
        inputEmail.error = getString(R.string.field_is_required)
        inputPassword.error = getString(R.string.field_is_required)
        inputName.error = getString(R.string.field_is_required)
    }

    private fun methodFailedRegister(task: Task<AuthResult>) {

        val exceptionOriginal = task.exception.toString().replace("com.google.firebase.auth.FirebaseAuthUserCollisionException:", "", false)

        Toast.makeText(this, getString(R.string.failed_messages) + exceptionOriginal, Toast.LENGTH_SHORT).show()
    }

    private fun methodSuccessRegister() {
        var userId = auth.currentUser!!.uid

        var name = inputName.text.toString()
        var email = inputEmail.text.toString()

        val users = Users(name, email, userId, "", "", "",1,0)

        reference.child(userId).setValue(users).addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }else{
                val exceptionOriginal = task.exception.toString().replace("com.google.firebase.auth.FirebaseAuthUserCollisionException:", "", false)

                Toast.makeText(this, getString(R.string.failed_messages) + exceptionOriginal, Toast.LENGTH_SHORT).show()
            }
        })
    }

    lateinit var auth : FirebaseAuth
    lateinit var reference : DatabaseReference
    lateinit var inputName : EditText
    lateinit var inputPassword : EditText
    lateinit var inputEmail : EditText
    lateinit var buttonRegister : ImageView
    lateinit var lottieLoading : LottieAnimationView
    lateinit var helper : UniversalHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        supportActionBar!!.hide()

        initWidgets()

    }

    private fun initWidgets() {
        auth = FirebaseAuth.getInstance()
        reference = FirebaseDatabase.getInstance().getReference("users")

        inputName = findViewById(R.id.inputName)
        inputPassword = findViewById(R.id.inputPassword)
        inputEmail = findViewById(R.id.inputEmail)
        buttonRegister = findViewById(R.id.buttonRegister)

        lottieLoading = findViewById(R.id.lottie)

        helper = UniversalHelper()

        buttonRegister.setOnClickListener(this)
    }

}
