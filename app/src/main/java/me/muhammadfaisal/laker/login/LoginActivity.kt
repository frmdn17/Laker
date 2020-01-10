package me.muhammadfaisal.laker.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.firebase.ui.auth.data.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import me.muhammadfaisal.laker.R
import me.muhammadfaisal.laker.helper.Loading
import me.muhammadfaisal.laker.model.Users
import me.muhammadfaisal.laker.register.bottom_sheet.BottomSheetSelectRole

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        if(p0 == textCreateAccount){
            callBottomSheet = BottomSheetSelectRole()
            callBottomSheet.show(supportFragmentManager, "SelectRole")
        }else{
            functionLogin()
        }
    }

    private fun functionLogin() {

        var loading: Loading = Loading(this@LoginActivity)

        loading.setCancelable(false)
        loading.show()

        var email = inputEmail.text.toString()
        var password = inputPassword.text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                loading.cancel()
                getRoleForLogin()
            }else{
                loading.cancel()
            }
        }
    }

    private fun getRoleForLogin() {

        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users : Users = dataSnapshot.getValue(Users::class.java)!!

                if (users.role == 1){
                    Toast.makeText(this@LoginActivity, "Kamu Pencari Kerja", Toast.LENGTH_SHORT).show()
                }else if(users.role == 0){
                    Toast.makeText(this@LoginActivity, "Kamu Punya Perusahaan Kerja", Toast.LENGTH_SHORT).show()
                }else if(users.role == 7){
                    Toast.makeText(this@LoginActivity, "Kamu Admin", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@LoginActivity, "Lu Siapa AJG?", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    lateinit var textCreateAccount : TextView
    lateinit var callBottomSheet : BottomSheetDialogFragment
    lateinit var inputEmail : EditText
    lateinit var inputPassword : EditText
    lateinit var auth : FirebaseAuth
    lateinit var reference : DatabaseReference
    lateinit var buttonLogin : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar!!.hide()

        initWidgets()
    }

    private fun initWidgets() {
        textCreateAccount = findViewById(R.id.textCreateAccount)
        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword)
        auth = FirebaseAuth.getInstance()
        reference = FirebaseDatabase.getInstance().getReference("users")
        buttonLogin = findViewById(R.id.buttonLogin)

        textCreateAccount.setOnClickListener(this)
        buttonLogin.setOnClickListener(this)
    }
}
