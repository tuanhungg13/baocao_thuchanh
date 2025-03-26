package com.dev.tlucontactkotlin.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dev.tlucontactkotlin.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class RegisterFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var edtEmail: TextInputEditText
    private lateinit var edtPassword: TextInputEditText
    private lateinit var edtConfirmPassword: TextInputEditText
    private lateinit var edtName: TextInputEditText
    private lateinit var edtLayoutEmail: TextInputLayout
    private lateinit var edtLayoutPassword: TextInputLayout
    private lateinit var edtLayoutConfirmPassword: TextInputLayout
    private lateinit var edtLayoutName: TextInputLayout
    private lateinit var btnRegister: Button
    private lateinit var txtLogin: TextView
    private lateinit var progressBar: ProgressBar

    private var isLoading = false

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        auth = FirebaseAuth.getInstance()

        edtEmail = view.findViewById(R.id.edt_email)
        edtPassword = view.findViewById(R.id.edt_password)
        edtConfirmPassword = view.findViewById(R.id.edt_confirm_pw)
        edtLayoutName = view.findViewById(R.id.edt_layout_name)
        edtLayoutEmail = view.findViewById(R.id.edt_layout_email)
        edtLayoutPassword = view.findViewById(R.id.edt_layout_pw)
        edtLayoutConfirmPassword = view.findViewById(R.id.edt_layout_confirm_pw)
        edtName = view.findViewById(R.id.edt_name)
        btnRegister = view.findViewById(R.id.btn_register)
        txtLogin = view.findViewById(R.id.txt_link_login)
        progressBar = activity?.findViewById<ProgressBar>(R.id.prg_loading)!!

        btnRegister.setOnClickListener { registerUser() }
        txtLogin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.authContainer, LoginFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun setLoadingState(loading: Boolean) {
        isLoading = loading
        btnRegister.isEnabled = !loading
        progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun registerUser() {
        val email = edtEmail.text.toString().trim()
        val password = edtPassword.text.toString().trim()
        val confirmPassword = edtConfirmPassword.text.toString().trim()
        val fullName = edtName.text.toString().trim()

        if (validateInputs(email, fullName, password, confirmPassword)) {
            setLoadingState(true)

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        user?.sendEmailVerification()
                            ?.addOnCompleteListener { emailTask ->
                                setLoadingState(false)
                                if (emailTask.isSuccessful) {
                                    edtEmail.setText("")
                                    edtName.setText("")
                                    edtPassword.setText("")
                                    edtConfirmPassword.setText("")
                                    Log.d("Email", "Email xác thực đã được gửi.")
                                    Toast.makeText(
                                        requireContext(),
                                        "Vui lòng kiểm tra email để xác nhận tài khoản.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    // 👉 Chuyển sang màn hình đăng nhập
                                    parentFragmentManager.beginTransaction()
                                        .replace(R.id.authContainer, LoginFragment())
                                        .addToBackStack(null)
                                        .commit()
                                    setLoadingState(false)
                                } else {
                                    Log.e(
                                        "Email",
                                        "Lỗi khi gửi email xác thực.",
                                        emailTask.exception
                                    )
                                    Toast.makeText(
                                        requireContext(),
                                        "Gửi email xác thực thất bại.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    setLoadingState(false)
                                }
                            }
                    } else {
                        setLoadingState(false)
                        if (task.exception is FirebaseAuthUserCollisionException) {
                            Log.e("SignUp", "Email đã được sử dụng.")
                            Toast.makeText(
                                requireContext(),
                                "Email đã tồn tại, hãy thử đăng nhập.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Log.e("SignUp", "Lỗi khi tạo tài khoản: ${task.exception?.message}")
                            Toast.makeText(
                                requireContext(),
                                "Lỗi: ${task.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
        }
    }

    private fun validateInputs(
        email: String,
        fullName: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        var isValid = true

        edtLayoutEmail.error = null
        edtLayoutPassword.error = null
        edtLayoutConfirmPassword.error = null
        edtLayoutName.error = null

        val passwordRegex =
            Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")

        if (email.isEmpty()) {
            edtLayoutEmail.error = "Email không được để trống"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtLayoutEmail.error = "Email không hợp lệ"
            isValid = false
        }

        if (fullName.isEmpty()) {
            edtLayoutName.error = "Họ và tên không được để trống"
            isValid = false
        }

        if (password.isEmpty()) {
            edtLayoutPassword.error = "Mật khẩu không được để trống"
            isValid = false
        } else if (!password.matches(passwordRegex)) {
            edtLayoutPassword.error =
                "Mật khẩu cần ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt."
            isValid = false
        }

        if (confirmPassword.isEmpty()) {
            edtLayoutConfirmPassword.error = "Vui lòng nhập lại mật khẩu"
            isValid = false
        } else if (confirmPassword != password) {
            edtLayoutConfirmPassword.error = "Mật khẩu xác nhận không khớp"
            isValid = false
        }

        return isValid
    }
}
