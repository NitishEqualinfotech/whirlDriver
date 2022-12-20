package com.app.whirl.activities

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.app.whirl.R
import com.app.whirl.activities.otp.OTPActivity
import com.app.whirl.network.ApiInterface
import com.app.whirl.network.LoginApiResponse
import com.app.whirl.network.SignupApiResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        generatetoken()
        imageView.setOnClickListener {
            onBackPressed()
        }
    }

    fun click(view: View) {

    }

    fun clickNext(view: View) {

        if (isValidate()) {
            signupApiCall()
        }

    }


    fun onClickRegister(view: View) {

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        // Toast.makeText(this,"Under Development",Toast.LENGTH_SHORT).show()

    }

    fun generatetoken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            token = task.result
            Log.e(
                "TAG", token +
                        ".."
            )
        })
    }

    private fun isValidate(): Boolean {

        if (edtPhoneNo.text.toString().trim().length < 10) {
            Toast.makeText(applicationContext, "Please enter valid phone no", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        return true;
    }

    private fun signupApiCall() {
        val progressDialog = ProgressDialog(this)
        //    progressDialog.setTitle("Kotlin Progress Bar")
        progressDialog.setMessage("Loading...")
        progressDialog.show()


        var hashMap: HashMap<String, String> = HashMap<String, String>()
        hashMap.put("mobile", "+"+ccp.selectedCountryCode.toString()+edtPhoneNo.text.toString().trim())
        hashMap.put("device_tokan", token)
        val apiCall = ApiInterface.create().driverSignup(hashMap)

        apiCall.enqueue(object : Callback<SignupApiResponse> {
            override fun onResponse(
                call: Call<SignupApiResponse>,
                response: Response<SignupApiResponse>
            ) {
                progressDialog.dismiss()
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                     /*   Toast.makeText(
                            applicationContext,
                            response.body()!!.data.otp,
                            Toast.LENGTH_SHORT
                        ).show()*/
                        moveToActivity()

                    } else {
                        Toast.makeText(
                            applicationContext,
                            response.body()!!.msg,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    Toast.makeText(
                        applicationContext,
                        resources.getString(R.string.servererror),
                        Toast.LENGTH_SHORT
                    ).show()
                }


            }

            override fun onFailure(call: Call<SignupApiResponse>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    t.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                progressDialog.dismiss()
            }


        })

    }

    private fun moveToActivity() {

        val intent = Intent(this, OTPActivity::class.java)
        intent.putExtra("number", "+"+ccp.selectedCountryCode.toString()+edtPhoneNo.text.toString().trim())
        intent.putExtra("isLogin", false)
        intent.putExtra("type","Signup")
        startActivity(intent)


//        val intent = Intent(this, OTPActivity::class.java)
//        intent.putExtra("number", edtPhoneNo.text.toString().trim())
//        intent.putExtra("isLogin", false)
//        startActivity(intent)
    }
}