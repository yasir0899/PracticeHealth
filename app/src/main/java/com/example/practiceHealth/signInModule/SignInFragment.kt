package com.example.practiceHealth.signInModule


import android.os.Build
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.practiceHealth.activities.MainActivity
import com.example.practiceHealth.R
import com.example.practiceHealth.utils.AppUtils
import com.example.practiceHealth.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_sign_in.*


class SignInFragment : Fragment() {
    var setPType = 1
    private val signInVM: SignInVM by lazy {
        ViewModelProviders.of(this).get(SignInVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as MainActivity).toolBarsCenterTitle("")
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
        (activity as MainActivity).hideSignOutOption()
        etEmail.setText("administrator")
        etPassword.setText("admin")
        btnSignIn.setOnClickListener {

            //navigateToHome()
            if (isSubmitAble()) {
                pbSignIn.visibility = View.VISIBLE
                signInVM.loginIn(etEmail.text.toString(), etPassword.text.toString())?.observe(this, Observer<Any> {
                    pbSignIn.visibility = View.INVISIBLE
                    when {
                        (it.toString() == "true") -> {
                            navigateToHome()

                        }
                        (it.toString() == "false") -> {
                            ToastUtil.showShortToast(requireContext(), getString(R.string.incorrect_username_pin))

                        }

                    }
                })
            }
        }
        getDeviceName()

        etPassword.setOnTouchListener { view, motionEvent ->


            var DRAWABLE_LEFT = 0
            var DRAWABLE_TOP = 1
            var DRAWABLE_RIGHT = 2
            var DRAWABLE_BOTTOM = 3

            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (motionEvent.rawX >= (etPassword.right - etPassword.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                    if (setPType == 1) {
                        setPType = 0
                        etPassword.transformationMethod = null
                        if (etPassword.text!!.isNotEmpty()) {

                            etPassword.setSelection(etPassword.text!!.length)
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.open_eye_24, 0)
                        }
                    } else {
                        setPType = 1
                        etPassword.transformationMethod = PasswordTransformationMethod()
                        if (etPassword.text!!.isNotEmpty()) {

                            etPassword.setSelection(etPassword.text!!.length)
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.close_eye_24, 0)
                        }
                    }

                    true
                }
            }
            false
        }

    }

    private fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        val version = Build.VERSION.RELEASE
        Log.e("model", "$model")
        Log.e("manufacturer", "$manufacturer")
        Log.e("version", "$version")
        return if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            capitalize(model)

        } else {
            capitalize(manufacturer) + " " + model
        }
    }


    private fun capitalize(s: String?): String {
        if (s == null || s.isEmpty()) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first) + s.substring(1)
        }
    }

    private fun navigateToHome() {
        val navController = Navigation.findNavController(
            activity!!,
            R.id.nav_host_fragment
        )

        navController.navigate(R.id.action_signInFragment_to_homeFragment)
    }

    private fun isSubmitAble(): Boolean {
        var returnValue = true
        if (!validateUsername()) returnValue = false
        if (!validatePin()) returnValue = false
        return returnValue
    }

    private fun validateUsername(): Boolean {
        return when {
            (etEmail.text.toString().trim { it <= ' ' }.isEmpty()) -> {
                ToastUtil.showShortToast(requireContext(), getString(R.string.please_enter_username))
                AppUtils.requestFocus(requireActivity(), etEmail)
                false
            }

            else -> {

                true
            }
        }
    }

    private fun validatePin(): Boolean {
        return when {
            (etPassword.text.toString().trim { it <= ' ' }.isEmpty()) -> {
                ToastUtil.showShortToast(requireContext(), getString(R.string.please_enter_pin))
                AppUtils.requestFocus(requireActivity(), etPassword)
                false
            }

            else -> {

                true
            }
        }
    }
}


