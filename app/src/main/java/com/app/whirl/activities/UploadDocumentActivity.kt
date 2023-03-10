package com.app.whirl.activities

import android.R.attr.bitmap
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.whirl.HomeActivity
import com.app.whirl.R
import com.app.whirl.activities.profile.ProfileModels.UpdateProfileResponse
import com.app.whirl.network.ApiInterface
import com.app.whirl.network.UploadDocumentApiResponse
import com.app.whirl.utils.Camerautils.FileCompressor
import com.app.whirl.utils.NetworkUtils
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.metaled.Utils.ConstantUtils
import com.metaled.Utils.SharedPreferenceUtils
import kotlinx.android.synthetic.main.activity_upload_document.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File


class UploadDocumentActivity : AppCompatActivity() {


    private var isProfileUri: File? = null
    private var isDrivingLicenceFrontUri: File? = null
    private var isPanCardUri: File? = null
    private var isVehicleInsuranceUri: File? = null
    private var isRegistrationCertficateUri: File? = null
    private var isVehiclePermitUri: File? = null
    private val CAMERA = 100
    private val CAMERA_PERMISSION_CODE = 101
    var image_uri: Uri? = null

    var driverfront = ""
    var dirverback = ""
    var socialsecurityfront = ""
    var socialsecurityback = ""
    var vehicleregistration = ""
    var vehicleinsurance = ""


    var isProfile: Boolean = false
    var isDrivingLicenceFront: Boolean = false
    var isPanCard: Boolean = false
    var isVehicleInsurance: Boolean = false
    var isRegistrationCertficate: Boolean = false
    var isVehiclePermit: Boolean = false

    var status: String = ""

    var email: String = ""
    var isLogin: Boolean = false
    var number: String = ""

    var fName: String = ""

    var lName: String = ""

    var earntype = "car owner"
    var userId: String? = ""
    var mCompressor: FileCompressor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_document)
        mCompressor = FileCompressor(this)
        getData()
        userId = SharedPreferenceUtils.getInstance(this)?.getStringValue(ConstantUtils.USER_ID, "")
        number = SharedPreferenceUtils.getInstance(this)
            ?.getStringValue(ConstantUtils.USER_MOBILE_No, "")!!

        Log.d("userId", userId + "...")
        Log.d("number", number + "...")
        /* llProfilePhoto
         llDrivingLicence
         llPanCard
         llVehicleInsurance
         llRC
         llVehiclePermit*/
        btnNext.setOnClickListener {

            onClickNext()
        }
        imageViewBackDocument.setOnClickListener {
            onBackPressed()
        }


    }

    /* fun getStringImage(bmp: Bitmap): String {
         val baos = ByteArrayOutputStream()
         bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos)
         val imageBytes: ByteArray = baos.toByteArray()
         return Base64.encodeToString(imageBytes, Base64.DEFAULT)
     }*/

    private fun getData() {

        fName = intent.getStringExtra("fname").toString()
        lName = intent.getStringExtra("lname").toString()
        email = intent.getStringExtra("email").toString()
        earntype = intent.getStringExtra("vehicleType").toString()
    }


    fun onClickVehiclePermit(view: View) {

        when (view.id) {
            R.id.llProfilePhoto -> {

                isProfile = true
                isDrivingLicenceFront = false
                isPanCard = false
                isVehicleInsurance = false
                isRegistrationCertficate = false
                isVehiclePermit = false
                status = "profile_img"


            }

            R.id.llDrivingLicence -> {

                isProfile = false
                isDrivingLicenceFront = true
                isPanCard = false
                isVehicleInsurance = false
                isRegistrationCertficate = false
                isVehiclePermit = false
                status = "driving_licence"


            }

            R.id.llPanCard -> {

                isProfile = false
                isDrivingLicenceFront = false
                isPanCard = true
                isVehicleInsurance = false
                isRegistrationCertficate = false
                isVehiclePermit = false
                status = "psn_card"

            }
            R.id.llVehicleInsurance -> {

                isProfile = false
                isDrivingLicenceFront = false
                isPanCard = false
                isVehicleInsurance = true
                isRegistrationCertficate = false
                isVehiclePermit = false
                status = "vehicle_insurance"

            }
            R.id.llRC -> {

                isProfile = false
                isDrivingLicenceFront = false
                isPanCard = false
                isVehicleInsurance = false
                isRegistrationCertficate = true
                isVehiclePermit = false
                status = "vehicle_registration"

            }

            R.id.llVehiclePermit -> {

                isProfile = false
                isDrivingLicenceFront = false
                isPanCard = false
                isVehicleInsurance = false
                isRegistrationCertficate = false
                isVehiclePermit = true
                status = "vehicle_permit"

            }
        }
        /* if(isProfile) {

             val intent = Intent(this, UploadProfileActivity::class.java)
             startActivityForResult(intent, 122)
         }else{*/
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
        //    }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 122) {

            } else {

                //Image Uri will not be null for RESULT_OK
                val uri: Uri = data?.data!!

                // Use Uri object instead of File to avoid storage permissions
                //    imgProfile.setImageURI(fileUri)
                Log.d("uri", "onActivityResult: " + uri)

                setImageUri(uri)

            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setImageUri(uri: Uri) {
        if (isProfile) {
            isProfile = true
            isProfileUri = File(uri.path)
            isProfileUri = mCompressor!!.compressToFile(isProfileUri)
            profileTick.visibility = View.VISIBLE

            val bmImg = BitmapFactory.decodeFile(File(uri.path).toString())
            imgdrvrLcnsFrnt.setImageBitmap(bmImg)


         //    driverfront= getStringImage(bmImg)

        } else if (isDrivingLicenceFront) {
            isDrivingLicenceFront = true
            isDrivingLicenceFrontUri = File(uri.path)
            isDrivingLicenceFrontUri = mCompressor!!.compressToFile(isDrivingLicenceFrontUri)
            driverlicenceTick.visibility = View.VISIBLE
            val bmImg = BitmapFactory.decodeFile(File(uri.path).toString())
            imgLicenceBack.setImageBitmap(bmImg)

            //dirverback= getStringImage(bmImg)

        } else if (isPanCard) {
            isPanCard = true
            panTick.visibility = View.VISIBLE
            //isPanCardUri = uri
            isPanCardUri = File(uri.path)
            isPanCardUri = mCompressor!!.compressToFile(isPanCardUri)
            val bmImg = BitmapFactory.decodeFile(File(uri.path).toString())
            imgSoclScrtyFrnt.setImageBitmap(bmImg)

            // socialsecurityfront= getStringImage(bmImg)


        } else if (isRegistrationCertficate) {
            isRegistrationCertficate = true
            regTick.visibility = View.VISIBLE
            isRegistrationCertficateUri = File(uri.path)
            isRegistrationCertficateUri = mCompressor!!.compressToFile(isRegistrationCertficateUri)

            val bmImg = BitmapFactory.decodeFile(File(uri.path).toString())
            imgVehiclRegistration.setImageBitmap(bmImg)

            // socialsecurityback= getStringImage(bmImg)


        } else if (isVehicleInsurance) {
            isVehicleInsurance = true
            vehicleInsureTick.visibility = View.VISIBLE

            isVehicleInsuranceUri = File(uri.path)
            isVehicleInsuranceUri = mCompressor!!.compressToFile(isVehicleInsuranceUri)

            val bmImg = BitmapFactory.decodeFile(File(uri.path).toString())
            imgSclBack.setImageBitmap(bmImg)

            //vehicleregistration= getStringImage(bmImg)


        } else if (isVehiclePermit) {
            isVehiclePermit = true
            permitTick.visibility = View.VISIBLE
            regTick

            isVehiclePermitUri = File(uri.path)
            isVehiclePermitUri = mCompressor!!.compressToFile(isVehiclePermitUri)
            // createMultiPart(""+userId,uri,11,"vehiclepermit");

            val bmImg = BitmapFactory.decodeFile(File(uri.path).toString())
            imgVehcileInsurance.setImageBitmap(bmImg)

            // vehicleinsurance= getStringImage(bmImg)

        }


    }

    fun onClickNext() {

        if (isValidate()) {
            /*  var  userdto:UserDTO =AppPref.getInstance(applicationContext)!!.getPersonDetails()
              userdto.step=2*/
            SharedPreferenceUtils.getInstance(this)
                ?.setValue(ConstantUtils.REGISTRATION_STEP, 2)
            // AppPref.getInstance(applicationContext)!!.savePersonDetails(userdto)
            initViews()


        }
    }

    fun initViews() {


        if (tvTagno.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "Please Enter Vehicle Tag Number", Toast.LENGTH_SHORT).show()
        } else if (isProfileUri == null) {
            Toast.makeText(this, "Please Upload Driver's Licence-Front", Toast.LENGTH_SHORT)
                .show()
        } else if (isDrivingLicenceFrontUri == null) {
            Toast.makeText(this, "Please Upload Driver's Licence-Back", Toast.LENGTH_SHORT)
                .show()
        } else if (isPanCardUri == null) {
            Toast.makeText(this, "Please Upload Social Security-Front", Toast.LENGTH_SHORT)
                .show()
        } else if (isVehicleInsuranceUri == null) {
            Toast.makeText(this, "Please Upload Social Security-Back", Toast.LENGTH_SHORT)
                .show()
        } else if (isRegistrationCertficateUri == null) {
            Toast.makeText(this, "Please Upload Vehical Registration Photo", Toast.LENGTH_SHORT)
                .show()
        } else if (isVehiclePermitUri == null) {
            Toast.makeText(this, "Please Upload Vehical Insurance Photo", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (NetworkUtils.checkInternetConnection(this@UploadDocumentActivity)) {
                //Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show()
               // uploadDocument()

                val intent = Intent(this@UploadDocumentActivity, BackCheckActivity::class.java)
                val str: String = tvTagno.getText().toString()
                intent.putExtra("userId",userId)
                intent.putExtra("tag_number", str)
                intent.putExtra("imgdrvrLcnsFrnt", isProfileUri.toString())
                intent.putExtra("imgLicenceBack", isDrivingLicenceFrontUri.toString())
                intent.putExtra("imgSoclScrtyFrnt", isPanCardUri.toString())
                intent.putExtra("imgSclBack", isVehicleInsuranceUri.toString())
                intent.putExtra("imgVehiclRegistration", isRegistrationCertficateUri.toString())
                intent.putExtra("imgVehcileInsurance", isVehiclePermitUri.toString())
                intent.putExtra("fname", fName)
                intent.putExtra("lname", lName)
                intent.putExtra("email", email)
                intent.putExtra("vehicleType", earntype)

                startActivity(intent)
            }
        }


    }


    private fun isValidate(): Boolean {
        if (profileTick.visibility == View.VISIBLE && driverlicenceTick.visibility == View.VISIBLE && panTick.visibility == View.VISIBLE &&
            vehicleInsureTick.visibility == View.VISIBLE && regTick.visibility == View.VISIBLE && permitTick.visibility == View.VISIBLE
        ) {
            return true
        } else {
            Toast.makeText(applicationContext, "Please upload all documents", Toast.LENGTH_SHORT)
                .show()
            return false;
        }

    }


    private fun createMultiPart(userId: String, doc1: Uri, requestCode: Int, type: String) {

        val progressDialog = ProgressDialog(this)
        //  progressDialog.setTitle("Kotlin Progress Bar")
        progressDialog.setMessage("Loading...")
        progressDialog.show()

//        Log.d("uri", "onActivityResult: ===>" + doc1.toString())
        var requestBody: RequestBody? = null
        val file = File(doc1.toString())

        val userId: RequestBody = userId.toRequestBody("text/plain".toMediaType())

        val fName: RequestBody = fName.toRequestBody("text/plain".toMediaType())

        val lName: RequestBody = lName.toRequestBody("text/plain".toMediaType())
        val email: RequestBody = email.toRequestBody("text/plain".toMediaType())

        val earnType: RequestBody = "2".toRequestBody("text/plain".toMediaType())
        var type: RequestBody = type.toRequestBody("text/plain".toMediaType())
        requestBody = file.path.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        //  Log.d("uri", "onActivityResult: ========>" + doc1.toString()+" file "+file.name+" path"+file.path)

        var imagenPerfil: MultipartBody.Part
        imagenPerfil = MultipartBody.Part.createFormData("imagenPerfil", file.name, requestBody);

        //Log.d("uri", "onActivityResult: ========>after part" + doc1.toString())

        val apiCall = ApiInterface.create()
            .uploadDocument(userId, fName, lName, email, earnType, type, imagenPerfil)

        apiCall.enqueue(object : Callback<UploadDocumentApiResponse> {
            override fun onResponse(
                call: Call<UploadDocumentApiResponse>,
                response: Response<UploadDocumentApiResponse>
            ) {
                progressDialog.dismiss()
                if (response.isSuccessful) {

                    if (response.body()!!.success) {
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
                    }

                }


            }

            override fun onFailure(call: Call<UploadDocumentApiResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()
            }


        })


    }

    private fun uploadDocument() {

        rlLoader.visibility = View.VISIBLE
//        Log.d("uri", "onActivityResult: ===>" + doc1.toString())

        val userId: RequestBody? = userId?.let {
            RequestBody.create(
                MultipartBody.FORM, it
            )
        }
        val tag_no: RequestBody? = tvTagno.text.toString()?.let {
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
        // val userId: RequestBody? = userId?.toRequestBody("text/plain".toMediaType())

        //val fName: RequestBody =fName.toRequestBody("text/plain".toMediaType())

        //val lName: RequestBody =lName.toRequestBody("text/plain".toMediaType())
        // val email: RequestBody =email.toRequestBody("text/plain".toMediaType())

        //  val earnType: RequestBody ="2".toRequestBody("text/plain".toMediaType())

        val multiPartRepeatString = "application/image"

        var profile_image: MultipartBody.Part? = null
        if (isProfileUri != null) {
            //  val file = File(isProfileUri!!.path)
            val file = isProfileUri
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
        var licence_image: MultipartBody.Part? = null
        if (isDrivingLicenceFrontUri != null) {
            // val file = File(isDrivingLicenceFrontUri!!.path)
            val file = isDrivingLicenceFrontUri
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            licence_image =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "driving_license", file.name,
                        it
                    )
                }
        }

        var pancard_image: MultipartBody.Part? = null
        if (isPanCardUri != null) {
            //  val file = File(isPanCardUri!!.path)
            val file = isPanCardUri
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            pancard_image =
                signPicBody?.let { MultipartBody.Part.createFormData("pan_card", file.name, it) }
        }

        var vehicleIns_image: MultipartBody.Part? = null
        if (isVehicleInsuranceUri != null) {
            //val file = File(isVehicleInsuranceUri!!.path)
            val file = isVehicleInsuranceUri
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            vehicleIns_image =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "reistration_certificate", file.name,
                        it
                    )
                }
        }

        var vehiclePermit_image: MultipartBody.Part? = null
        if (isVehiclePermitUri != null) {
            // val file = File(isVehiclePermitUri!!.path)
            val file = isVehiclePermitUri
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            vehiclePermit_image =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "vehicle_permit", file.name,
                        it
                    )
                }
        }

        var regCertif_image: MultipartBody.Part? = null
        if (isRegistrationCertficateUri != null) {
            //val file = File(isRegistrationCertficateUri!!.path)
            val file = isRegistrationCertficateUri
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            regCertif_image =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "vehcile_insurence", file.name,
                        it
                    )
                }
        }
        //Log.d("uri", "onActivityResult: ========>after part" + doc1.toString())

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

        apiCall.enqueue(object : Callback<UpdateProfileResponse> {
            override fun onResponse(
                call: Call<UpdateProfileResponse>,
                response: Response<UpdateProfileResponse>
            ) {

                if (response.body()!!.success.equals("true")) {

                    rlLoader.visibility = View.GONE

                    val intent = Intent(this@UploadDocumentActivity, BackCheckActivity::class.java)
                    startActivity(intent)



                    finishAffinity()
                    /*       if(!response.body()!!.data.driving_license.isNullOrEmpty()) {
                               Glide.with(this@UploadDocumentActivity).load(response.body()!!.data.driving_license)
                                   //.placeholder(resources.getDrawable(R.drawable.profile))
                                   .into(imgdrvrLcnsFrnt)
                               profileTick!!.setOnClickListener {
                                   intent = Intent(this@UploadDocumentActivity, ProfilePhotoActivity::class.java)
                                   intent.putExtra("image", response.body()!!.data.driving_license)
                                   startActivity(intent)

                               }
                           }*/


                    /* registerUserFirebase(userId?.toString(),
                         fName?.toString(), lName?.toString(), email?.toString(),response.body()!!.data[0].profile_photo)

*/
                    /*   val intent = Intent(this@UploadDocumentActivity, LoginActivity::class.java)
                       startActivity(intent)
                       finishAffinity()*/


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

                    rlLoader.visibility = View.GONE
                }


            }

            override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                rlLoader.visibility = View.GONE
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()


                /*     val intent = Intent(this@UploadDocumentActivity, LoginActivity::class.java)
                     startActivity(intent)
                     finishAffinity()*/
            }


        })


    }



    private fun registerUserFirebase(
        userId: String?,
        firstname: String?,
        lastname: String?,
        emailid: String?,
        profilepic: String?,


        ) {
        Log.d("registerUserFirebase", "registerUserFirebase")
        firstname?.let { Log.d("registerUserFirebase", it) }
        lastname?.let { Log.d("registerUserFirebase", it) }
        //  try {


        val user = HashMap<String, String>()
        userId?.let { user.put("userId", it) }
        firstname?.let { user.put("first_name", it) }
        lastname?.let { user.put("last_name", it) }
        emailid?.let { user.put("email", it) }
        number?.let { user.put("mobileno", it) }
        //user.put("password", password_edt_text.text.toString())
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(number + "@gamil.com", number)
            .addOnCompleteListener {
                try {
                    Log.d("registerUserresult", it.result.toString())
                    Log.d("registerUserexception", it.exception.toString())

                } catch (e: Exception) {
                    Log.d("Exception", e.toString())
                }
                if (it.isSuccessful) {
                    val current_user = FirebaseAuth.getInstance().currentUser
                    val uid = current_user!!.uid
/* user["deviceToken"] = deviceToken
                user["fcmUserId"]=uid*/
                    FirebaseDatabase.getInstance().getReference().child(uid).setValue(user)
                        .addOnCompleteListener { it1 ->
                            if (it1.isSuccessful) {

                                SharedPreferenceUtils.getInstance(this@UploadDocumentActivity)
                                    ?.setStringValue(
                                        ConstantUtils.FIRSTNAME,
                                        this@UploadDocumentActivity.fName
                                    )

                                SharedPreferenceUtils.getInstance(this@UploadDocumentActivity)
                                    ?.setStringValue(
                                        ConstantUtils.LASTNAME,
                                        this@UploadDocumentActivity.lName
                                    )

                                SharedPreferenceUtils.getInstance(this@UploadDocumentActivity)
                                    ?.setStringValue(
                                        ConstantUtils.EMAIL_ID,
                                        this@UploadDocumentActivity.email
                                    )

                                SharedPreferenceUtils.getInstance(this@UploadDocumentActivity)
                                    ?.setStringValue(ConstantUtils.IS_LOGIN, "true")

                                SharedPreferenceUtils.getInstance(this@UploadDocumentActivity)
                                    ?.setStringValue(ConstantUtils.IS_DOCUMENTATION, "true")
                                SharedPreferenceUtils.getInstance(this@UploadDocumentActivity)
                                    ?.setStringValue(
                                        ConstantUtils.PROFILE_IMAGE,
                                        profilepic
                                    )
                                val intent =
                                    Intent(this@UploadDocumentActivity, HomeActivity::class.java)
                                startActivity(intent)
                                finishAffinity()

                            } else {

                                Toast.makeText(
                                    this,
                                    "YOU ARE NOT REGISTERED... CREATE NEW ACCOUNT",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    rlLoader.visibility = View.GONE
                } else {

                    Toast.makeText(
                        this,
                        "ERROR REGISTERING USER....",
                        Toast.LENGTH_SHORT
                    ).show()
                    rlLoader.visibility = View.GONE
                }
            }.addOnFailureListener {
                Log.d("failure", "registerUserFirebase: ${it.localizedMessage}")
                rlLoader.visibility = View.GONE
                Toast.makeText(
                    this,
                    it.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()

            }
        /* } catch (e: Exception) {
             rlLoader.visibility=View.GONE
             Toast.makeText(
                 this,
                 e.toString(),
                 Toast.LENGTH_SHORT
             ).show()
         }*/
    }


}
