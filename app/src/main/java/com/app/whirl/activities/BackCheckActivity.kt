package com.app.whirl.activities

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.whirl.R
import com.app.whirl.activities.profile.ProfileModels.UpdateProfileResponse
import com.app.whirl.network.ApiInterface
import com.metaled.Utils.ConstantUtils
import com.metaled.Utils.SharedPreferenceUtils
import kotlinx.android.synthetic.main.activity_back_check.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class BackCheckActivity : AppCompatActivity() {
    var userId: String? = ""
    var fName: String = ""
    var lName: String = ""
    var email: String = ""
    var earntype = "car owner"

    var number: String = ""

    private var isProfileUri: File? = null
    private var isDrivingLicenceFrontUri: File? = null
    private var isPanCardUri: File? = null
    private var isVehicleInsuranceUri: File? = null
    private var isRegistrationCertficateUri: File? = null
    private var isVehiclePermitUri: File? = null

    var isRegistrationCertficate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_back_check)



        Log.e("jiijijop", intent.getStringExtra("imgdrvrLcnsFrnt").toString())



        getData()
        userId = SharedPreferenceUtils.getInstance(this)?.getStringValue(ConstantUtils.USER_ID, "")
        number = SharedPreferenceUtils.getInstance(this)
            ?.getStringValue(ConstantUtils.USER_MOBILE_No, "")!!

        btnNext.setOnClickListener {
            uploadDocument()

            //showDialog()
        }

        imageViewBackDocument.setOnClickListener {
            onBackPressed()
        }


    }

    private fun getData() {
        fName = intent.getStringExtra("fname").toString()
        lName = intent.getStringExtra("lname").toString()
        email = intent.getStringExtra("email").toString()
        earntype = intent.getStringExtra("vehicleType").toString()
    }

    private fun uploadDocument() {
        val progressDialog = ProgressDialog(this)
        //    progressDialog.setTitle("Kotlin Progress Bar")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        val multiPartRepeatString = "application/image"

        //val apiCall = ApiInterface.create().uploadDocument2

//        Log.d("uri", "onActivityResult: ===>" + doc1.toString())

        val userId: RequestBody? = userId?.let {
            RequestBody.create(
                MultipartBody.FORM, it
            )
        }

        val tag_no: RequestBody? = intent.getStringExtra("tag_number").toString().let {
            RequestBody.create(MultipartBody.FORM, it)
        }

        val fName: RequestBody? = fName?.let {
            RequestBody.create(
                MultipartBody.FORM, it
            )
        }
        val lName: RequestBody? = lName?.let {
            RequestBody.create(
                MultipartBody.FORM, it
            )
        }
        val email: RequestBody? = email?.let {
            RequestBody.create(
                MultipartBody.FORM, it
            )
        }
        val earnType: RequestBody? = earntype?.let {
            RequestBody.create(
                MultipartBody.FORM, it
            )
        }

        var profile_image: MultipartBody.Part? = null
        if (intent.getStringExtra("imgdrvrLcnsFrnt") != null) {
            //  val file = File(isProfileUri!!.path)
            val file = File(intent.getStringExtra("imgdrvrLcnsFrnt"))
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            profile_image =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "social_photo", file.name,
                        it
                    )
                }
        }


        var pancard_image: MultipartBody.Part? = null
        if (intent.getStringExtra("imgLicenceBack") != null) {
            //  val file = File(isProfileUri!!.path)
            val file = File(intent.getStringExtra("imgLicenceBack"))
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            pancard_image =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "driving_license", file.name,
                        it
                    )
                }
        }


        var vehicleIns_image: MultipartBody.Part? = null
        if (intent.getStringExtra("imgSoclScrtyFrnt") != null) {
            //  val file = File(isProfileUri!!.path)
            val file = File(intent.getStringExtra("imgSoclScrtyFrnt"))
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            vehicleIns_image =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "pan_card", file.name,
                        it
                    )
                }
        }


        var vehiclePermit_image: MultipartBody.Part? = null
        if (intent.getStringExtra("imgSclBack") != null) {
            //  val file = File(isProfileUri!!.path)
            val file = File(intent.getStringExtra("imgSclBack"))
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            vehiclePermit_image =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "vehcile_insurence", file.name,
                        it
                    )
                }
        }


        var regCertif_image: MultipartBody.Part? = null
        if (intent.getStringExtra("imgVehiclRegistration") != null) {
            //  val file = File(isProfileUri!!.path)
            val file = File(intent.getStringExtra("imgVehiclRegistration"))
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            regCertif_image =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "reistration_certificate", file.name,
                        it
                    )
                }
        }


        var licence_image: MultipartBody.Part? = null
        if (intent.getStringExtra("imgVehcileInsurance") != null) {
            //  val file = File(isProfileUri!!.path)
            val file = File(intent.getStringExtra("imgVehcileInsurance"))
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            licence_image =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "vehicle_permit", file.name,
                        it
                    )
                }
        }

        val apiCall = ApiInterface.create().uploadDocument2(
            userId,
            fName,
            lName,
            email,
            earnType,
            tag_no,
            profile_image,
            licence_image,
            pancard_image,
            vehicleIns_image,
            vehiclePermit_image,
            regCertif_image
        )


        intent.getStringExtra("imgVehcileInsurance").toString()
        apiCall.enqueue(object : Callback<UpdateProfileResponse> {
            override fun onResponse(
                call: Call<UpdateProfileResponse>,
                response: Response<UpdateProfileResponse>
            ) {

                if (response.body()!!.success.equals("true")) {

                    progressDialog.dismiss()
                    showDialog()

                    Toast.makeText(
                        applicationContext,
                        response.body()!!.msg,
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(
                        applicationContext,
                        response.body()!!.msg,
                        Toast.LENGTH_SHORT
                    ).show()

                    progressDialog.dismiss()
                }


            }

            override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                rlLoader.visibility = View.GONE
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()

            }


        })


    }


    fun showDialog() {
        var dialog = Dialog(this@BackCheckActivity)
        dialog.getWindow()!!
            .setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            );
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.account_request_popup)
        dialog.setCancelable(false)
        var btnok: Button = dialog.findViewById(R.id.btnok)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        //dialog.window?.setLayout(9050, 1300)
        btnok.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this@BackCheckActivity, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

}
