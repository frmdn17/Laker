package me.muhammadfaisal.laker.register.bottom_sheet


import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import me.muhammadfaisal.laker.R
import me.muhammadfaisal.laker.register.RegisterCompanyActivity
import me.muhammadfaisal.laker.register.RegisterUserActivity

/**
 * A simple [Fragment] subclass.
 */
class BottomSheetSelectRole : BottomSheetDialogFragment(), View.OnClickListener {
    override fun onClick(p0: View?) {
        if (p0 == cardJobSeeker){
            dismiss()
            startActivity(Intent(activity, RegisterUserActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
        }else{
            dismiss()
            startActivity(Intent(activity, RegisterCompanyActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
        }
    }

    lateinit var cardJobSeeker : CardView
    lateinit var cardCompany : CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v : View =  inflater.inflate(R.layout.fragment_bottom_sheet_select_role, container, false)

        initWidgets(v)

        return v
    }

    private fun initWidgets(v: View) {
        cardJobSeeker = v.findViewById(R.id.cardJobSeeker)
        cardCompany = v.findViewById(R.id.cardCompany)

        cardJobSeeker.setOnClickListener(this)
        cardCompany.setOnClickListener(this)
    }


}
