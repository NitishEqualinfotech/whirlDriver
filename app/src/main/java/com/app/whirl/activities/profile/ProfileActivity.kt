package com.app.whirl.activities.profile

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.app.whirl.R
import com.app.whirl.activities.profile.ProfileModels.GetProfileResponse
import com.app.whirl.activities.profile.ProfileModels.UpdateProfileResponse
import com.app.whirl.network.ApiInterface
import com.app.whirl.utils.Camerautils.FileCompressor
import com.app.whirl.utils.ToastUtil
import com.bumptech.glide.Glide
import com.example.ranierilavastone.Utils.StringUtil
import com.github.dhaval2404.imagepicker.ImagePicker
import com.metaled.Utils.ConstantUtils
import com.metaled.Utils.SharedPreferenceUtils


import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.btnNext
import kotlinx.android.synthetic.main.activity_profile.driverlicenceTick
import kotlinx.android.synthetic.main.activity_profile.imgLicenceBack
import kotlinx.android.synthetic.main.activity_profile.imgSclBack
import kotlinx.android.synthetic.main.activity_profile.imgSoclScrtyFrnt
import kotlinx.android.synthetic.main.activity_profile.imgVehcileInsurance
import kotlinx.android.synthetic.main.activity_profile.imgVehiclRegistration
import kotlinx.android.synthetic.main.activity_profile.imgdrvrLcnsFrnt
import kotlinx.android.synthetic.main.activity_profile.panTick
import kotlinx.android.synthetic.main.activity_profile.permitTick
import kotlinx.android.synthetic.main.activity_profile.profileTick
import kotlinx.android.synthetic.main.activity_profile.regTick
import kotlinx.android.synthetic.main.activity_profile.tvTagno
import kotlinx.android.synthetic.main.activity_profile.vehicleInsureTick
import kotlinx.android.synthetic.main.activity_upload_document.*
import kotlinx.android.synthetic.main.activity_your_earned.rlLoader
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ProfileActivity : AppCompatActivity() {
    private var profileImage: File? = null
    private var socialphoto: File? = null
    private var drivinglicense: File? = null
    private var pancard: File? = null
    private var vehcileinsurence: File? = null
    private var reistrationcertificate: File? = null
    private var vehiclepermit: File? = null

    private val CAMERA = 100
    private val CAMERA_PERMISSION_CODE = 101
    var image_uri: Uri? = null
    var email: String = ""
    var fName: String = ""
    var lName: String = ""
    var earn_type: String = ""
    var tag_no: String = ""
    var vehicle_name: String = ""
    var vehicle_type: String = ""
    var image = ""

    var isProfile: Boolean = false
    var isDrivingLicenceFront: Boolean = false
    var isPanCard: Boolean = false
    var isVehicleInsurance: Boolean = false
    var isRegistrationCertficate: Boolean = false
    var isVehiclePermit: Boolean = false
    var status:String=""

    var mCompressor: FileCompressor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        mCompressor = FileCompressor(this)
        Log.d(
            "profileImage",
            SharedPreferenceUtils.getInstance(this)
                ?.getStringValue(ConstantUtils.PROFILE_IMAGE, "") + "..."
        )
        /* Glide.with(this).load(
             SharedPreferenceUtils.getInstance(this)?.getStringValue(ConstantUtils.PROFILE_IMAGE, ""))
 //            .placeholder(resources.getDrawable(R.drawable.profile_icon))
             .into(ivProfile)*/

        /*    etFName.setText(
                SharedPreferenceUtils.getInstance(this)?.getStringValue(ConstantUtils.FIRSTNAME, "")
            )*/
        /*     etLName.setText(
                 SharedPreferenceUtils.getInstance(this)?.getStringValue(ConstantUtils.LASTNAME, "")
             )*/
/*        etEmail.setText(
            SharedPreferenceUtils.getInstance(this)?.getStringValue(ConstantUtils.EMAIL_ID, "")
        )*/
        /*      tvMobileNo.setText(
                  SharedPreferenceUtils.getInstance(this)
                      ?.getStringValue(ConstantUtils.USER_MOBILE_No, "")
              )*/

        onclick()

        my_toolbar.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24)

        my_toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })

        getprofiledetails()

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
                status="profile_img"

            }

            R.id.llDrivingLicence -> {

                isProfile = false
                isDrivingLicenceFront = true
                isPanCard = false
                isVehicleInsurance = false
                isRegistrationCertficate = false
                isVehiclePermit = false
                status="driving_licence"
            }

            R.id.llPanCard -> {

                isProfile = false
                isDrivingLicenceFront = false
                isPanCard = true
                isVehicleInsurance = false
                isRegistrationCertficate = false
                isVehiclePermit = false
                status="psn_card"

            }
            R.id.llVehicleInsurance -> {

                isProfile = false
                isDrivingLicenceFront = false
                isPanCard = false
                isVehicleInsurance = true
                isRegistrationCertficate = false
                isVehiclePermit = false
                status="vehicle_insurance"

            }
            R.id.llRC -> {

                isProfile = false
                isDrivingLicenceFront = false
                isPanCard = false
                isVehicleInsurance = false
                isRegistrationCertficate = true
                isVehiclePermit = false
                status="vehicle_registration"

            }

            R.id.llVehiclePermit -> {

                isProfile = false
                isDrivingLicenceFront = false
                isPanCard = false
                isVehicleInsurance = false
                isRegistrationCertficate = false
                isVehiclePermit = true
                status="vehicle_permit"

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



    fun onclick() {

        /*    profileTick!!.setOnClickListener {
                intent = Intent(this@ProfileActivity, ProfilePhotoActivity::class.java)
                intent.putExtra("image", uri.path)
                startActivity(intent)

            }*/

        ivProfile.setOnClickListener {
            status="profile"
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }


        btnNext.setOnClickListener {
            fName = etFName.text.toString()
            lName = etLName.text.toString()
            email = etEmail.text.toString()
            tag_no = tvTagno.text.toString()
            if (fName.isNullOrEmpty()) {
                ToastUtil.toast_Long(this, resources.getString(R.string.plzfname))
            }
            /*   else if (lName.isNullOrEmpty()) {
                   ToastUtil.toast_Long(this, resources.getString(R.string.plzlname))
               }*/
            else if (email.isNullOrEmpty()) {
                ToastUtil.toast_Long(this, resources.getString(R.string.plzemail))
            } else if (!StringUtil.isEmailValid(email)) {
                ToastUtil.toast_Long(this, resources.getString(R.string.plzvalidemail))
            } else {
                update()
            }

        }

        /* profileTick!!.setOnClickListener {
             intent = Intent(this@ProfileActivity, ProfilePhotoActivity::class.java)
             intent.putExtra("image", image)
             startActivity(intent)

         }*/
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
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            //   ivProfile.setImageURI(uri)

            Log.d("uri", "onActivityResult: " + uri)
            /*      profileImage = File(uri.path)
                  profileImage = mCompressor!!.compressToFile(profileImage)*/
            // Use Uri object instead of File to avoid storage permissions\

            if (status.equals("profile_img")){
                imgdrvrLcnsFrnt.setImageURI(uri)
                profileTick!!.setOnClickListener {
                    intent = Intent(this@ProfileActivity, ProfilePhotoActivity::class.java)
                    intent.putExtra("image", uri.toString());
                    startActivity(intent)
                }
            }else if (status.equals("driving_licence")){
                imgLicenceBack.setImageURI(uri)
                driverlicenceTick!!.setOnClickListener {
                    intent = Intent(this@ProfileActivity, ProfilePhotoActivity::class.java)
                    intent.putExtra("image", uri.toString());
                    startActivity(intent)
                }
            }else if (status.equals("psn_card")){
                imgSoclScrtyFrnt.setImageURI(uri)
                panTick!!.setOnClickListener {
                    intent = Intent(this@ProfileActivity, ProfilePhotoActivity::class.java)
                    intent.putExtra("image", uri.toString());
                    startActivity(intent)
                }
            }else if (status.equals("vehicle_insurance")){
                imgSclBack.setImageURI(uri)
                vehicleInsureTick!!.setOnClickListener {
                    intent = Intent(this@ProfileActivity, ProfilePhotoActivity::class.java)
                    intent.putExtra("image", uri.toString());
                    startActivity(intent)
                }
            }else if (status.equals("vehicle_registration")){
                imgVehiclRegistration.setImageURI(uri)
                regTick!!.setOnClickListener {
                    intent = Intent(this@ProfileActivity, ProfilePhotoActivity::class.java)
                    intent.putExtra("image", uri.toString());
                    startActivity(intent)
                }
            }else if (status.equals("vehicle_permit")){
                imgVehcileInsurance.setImageURI(uri)
                permitTick!!.setOnClickListener {
                    intent = Intent(this@ProfileActivity, ProfilePhotoActivity::class.java)
                    intent.putExtra("image", uri.toString());
                    startActivity(intent)
                }
            }else if (status.equals("profile")){
                ivProfile.setImageURI(uri)
                profileImage = File(uri.path)
                profileImage = mCompressor!!.compressToFile(profileImage)
            }


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }


    }

    private fun setImageUri(uri: Uri) {
        if (isProfile) {
            socialphoto = File(uri.path)
            socialphoto=mCompressor!!.compressToFile(socialphoto)
            val bmImg = BitmapFactory.decodeFile(File(uri.path).toString())
            imgdrvrLcnsFrnt.setImageBitmap(bmImg)

        } else if (isDrivingLicenceFront) {
            drivinglicense = File(uri.path)
            drivinglicense=mCompressor!!.compressToFile(drivinglicense)
            val bmImg = BitmapFactory.decodeFile(File(uri.path).toString())
            imgLicenceBack.setImageBitmap(bmImg)


            //createMultiPart(""+userId,uri,11,"drivinglicence");
        } else if (isPanCard) {
            pancard = File(uri.path)
            pancard=mCompressor!!.compressToFile(pancard)
            val bmImg = BitmapFactory.decodeFile(File(uri.path).toString())
            imgSoclScrtyFrnt.setImageBitmap(bmImg)


        } else if (isVehicleInsurance) {
            vehcileinsurence = File(uri.path)
            vehcileinsurence=mCompressor!!.compressToFile(vehcileinsurence)
            val bmImg = BitmapFactory.decodeFile(File(uri.path).toString())
            imgSclBack.setImageBitmap(bmImg)


        } else if (isRegistrationCertficate) {
            reistrationcertificate = File(uri.path)
            reistrationcertificate=mCompressor!!.compressToFile(reistrationcertificate)
            val bmImg = BitmapFactory.decodeFile(File(uri.path).toString())
            imgVehiclRegistration.setImageBitmap(bmImg)



        } else if (isVehiclePermit) {
            vehiclepermit = File(uri.path)
            vehiclepermit=mCompressor!!.compressToFile(vehiclepermit)
            val bmImg = BitmapFactory.decodeFile(File(uri.path).toString())
            imgVehcileInsurance.setImageBitmap(bmImg)

        }

    }

    private fun update() {

        val progressDialog = ProgressDialog(this)
        //  progressDialog.setTitle("Kotlin Progress Bar")
        progressDialog.setMessage("Loading...")
        progressDialog.show()

//        Log.d("uri", "onActivityResult: ===>" + doc1.toString())

        val userId: RequestBody? =
            SharedPreferenceUtils.getInstance(this)?.getStringValue(ConstantUtils.USER_ID, "")
                ?.let {
                    RequestBody.create(
                        MultipartBody.FORM, it
                    )
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
        val earn_type: RequestBody? = earn_type?.let {
            RequestBody.create(
                MultipartBody.FORM, it
            )
        }
        val tag_no: RequestBody? = tag_no?.let {
            RequestBody.create(
                MultipartBody.FORM, it
            )
        }
        val vehicle_name: RequestBody? = vehicle_name?.let {
            RequestBody.create(
                MultipartBody.FORM, it
            )
        }
        val vehicle_type: RequestBody? = vehicle_type?.let {
            RequestBody.create(
                MultipartBody.FORM, it
            )
        }



        val multiPartRepeatString = "application/image"

        var profile_image: MultipartBody.Part? = null
        if (profileImage != null) {
            //  val file = File(isProfileUri!!.path)
            val file = profileImage
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            profile_image =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "profile_photo", file.name,
                        it
                    )
                }
        }

        //val multiPartRepeatString = "application/image"

        var social_photo: MultipartBody.Part? = null
        if (socialphoto != null) {
            //  val file = File(isProfileUri!!.path)
            val file = socialphoto
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            social_photo =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "social_photo", file.name,
                        it
                    )
                }
        }

        var driving_license: MultipartBody.Part? = null
        if (drivinglicense != null) {
            //  val file = File(isProfileUri!!.path)
            val file = drivinglicense
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            driving_license =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "driving_license", file.name,
                        it
                    )
                }
        }

        var pan_card: MultipartBody.Part? = null
        if (pancard != null) {
            //  val file = File(isProfileUri!!.path)
            val file = pancard
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            pan_card =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "pan_card", file.name,
                        it
                    )
                }
        }

        var vehcile_insurence: MultipartBody.Part? = null
        if (vehcileinsurence != null) {
            //  val file = File(isProfileUri!!.path)
            val file = vehcileinsurence
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            vehcile_insurence =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "vehcile_insurence", file.name,
                        it
                    )
                }
        }

        var reistration_certificate: MultipartBody.Part? = null
        if (reistrationcertificate != null) {
            //  val file = File(isProfileUri!!.path)
            val file = reistrationcertificate
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            reistration_certificate =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "reistration_certificate", file.name,
                        it
                    )
                }
        }

        var vehicle_permit: MultipartBody.Part? = null
        if (vehiclepermit != null) {
            //  val file = File(isProfileUri!!.path)
            val file = vehiclepermit
            val signPicBody =
                file?.let { RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), it) }
            vehicle_permit =
                signPicBody?.let {
                    MultipartBody.Part.createFormData(
                        "vehicle_permit", file.name,
                        it
                    )
                }
        }


        //Log.d("uri", "onActivityResult: ========>after part" + doc1.toString())

        val apiCall = ApiInterface.create().editDocument(
            userId,
            profile_image,
            social_photo,
            driving_license,
            pan_card,
            vehcile_insurence,
            reistration_certificate,
            vehicle_permit,
            email,
            fName,
            lName,
            earn_type,
            tag_no,
            vehicle_name,
            vehicle_type,
            // driver_id
        )

        apiCall.enqueue(object : Callback<UpdateProfileResponse> {
            override fun onResponse(
                call: Call<UpdateProfileResponse>,
                response: Response<UpdateProfileResponse>
            ) {
                progressDialog.dismiss()
                if (response.isSuccessful) {

                    if (response.body()!!.equals(true)) {


                        if (!response.body()!!.data.tag_no.isNullOrEmpty()) {
                            tvTagno.setText(response.body()!!.data.tag_no)
                        }

                        if (!response.body()!!.data.fname.isNullOrEmpty()) {
                            etFName.setText(response.body()!!.data.fname)
                        }

                        if (!response.body()!!.data.mobile.isNullOrEmpty()) {
                            tvMobileNo.setText(response.body()!!.data.mobile)
                        }
                        if (!response.body()!!.data.email.isNullOrEmpty()) {
                            etEmail.setText(response.body()!!.data.email)
                        }

                        if(!response.body()!!.data.driving_license.isNullOrEmpty()) {
                            Glide.with(this@ProfileActivity).load(response.body()!!.data.driving_license)
                                //.placeholder(resources.getDrawable(R.drawable.profile))
                                .into(imgdrvrLcnsFrnt)
                            profileTick!!.setOnClickListener {
                                intent = Intent(this@ProfileActivity, ProfilePhotoActivity::class.java)
                                intent.putExtra("image", response.body()!!.data.driving_license)
                                startActivity(intent)

                            }
                        }

                        /*  SharedPreferenceUtils.getInstance(this@ProfileActivity)
                              ?.setStringValue(
                                  ConstantUtils.TAG_NO,
                                  this@ProfileActivity.tag_no
                              )*/

                        /*    SharedPreferenceUtils.getInstance(this@ProfileActivity)
                                ?.setStringValue(
                                    ConstantUtils.FIRSTNAME,
                                    this@ProfileActivity.fName
                                )*/

                        /*  SharedPreferenceUtils.getInstance(this@ProfileActivity)
                              ?.setStringValue(
                                  ConstantUtils.LASTNAME,
                                  this@ProfileActivity.lName
                              )*/

                        /*  SharedPreferenceUtils.getInstance(this@ProfileActivity)
                              ?.setStringValue(
                                  ConstantUtils.EMAIL_ID,
                                  this@ProfileActivity.email
                              )*/

                        /*    SharedPreferenceUtils.getInstance(this@ProfileActivity)
                                ?.setStringValue(
                                ?.setStringValue(
                                    ConstantUtils.PROFILE_IMAGE,
                                    response.body()!!.data.profile_photo
                                )*/
                        Toast.makeText(
                            applicationContext,
                            response.body()!!.msg,
                            Toast.LENGTH_SHORT
                        ).show()
                        onBackPressed()


                    } else {
                        Toast.makeText(
                            applicationContext,
                            response.body()!!.msg,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }


            }

            override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()
            }


        })


    }



    private fun getprofiledetails() {
        rlLoader.visibility = View.VISIBLE
        var hashMap: HashMap<String, String> = HashMap<String, String>()
        hashMap.put("driver_id", StringUtil.getUserId(this@ProfileActivity))

        val apiCall = ApiInterface.create().getdriverprofile(hashMap)
        apiCall.enqueue(object : Callback<GetProfileResponse> {
            override fun onResponse(
                call: Call<GetProfileResponse>,
                response: Response<GetProfileResponse>
            ) {
                if (response.isSuccessful) {
                    rlLoader.visibility = View.GONE
                    if (response.body()!!.success.equals("true")) {

                        if (!response.body()!!.data.fname.isNullOrEmpty()) {
                            etFName.setText(response.body()!!.data.fname)
                        }
                        if (!response.body()!!.data.mobile.isNullOrEmpty()) {
                            tvMobileNo.setText(response.body()!!.data.mobile)
                        }
                        if (!response.body()!!.data.email.isNullOrEmpty()) {
                            etEmail.setText(response.body()!!.data.email)
                        }
                        if (!response.body()!!.data.vehicle_type.isNullOrEmpty()) {
                            etLName.setText(response.body()!!.data.vehicle_type)
                        }
                        if (!response.body()!!.data.vehicle_name.isNullOrEmpty()) {
                            etVicheltype.setText(response.body()!!.data.vehicle_name)
                        }
                        if (!response.body()!!.data.tag_no.isNullOrEmpty()) {
                            tvTagno.setText(response.body()!!.data.tag_no)
                        }

                        if(!response.body()!!.data.profile_photo.isNullOrEmpty()) {
                            Glide.with(this@ProfileActivity).load(response.body()!!.data.profile_photo)
                                //.placeholder(resources.getDrawable(R.drawable.profile))
                                .into(ivProfile)

                        }

                        if(!response.body()!!.data.social.isNullOrEmpty()) {
                            Glide.with(this@ProfileActivity).load(response.body()!!.data.social)
                                //.placeholder(resources.getDrawable(R.drawable.profile))
                                .into(imgdrvrLcnsFrnt)
                            profileTick!!.setOnClickListener {
                                intent = Intent(this@ProfileActivity, ProfilePhotoActivity::class.java)
                                intent.putExtra("image", response.body()!!.data.social)
                                startActivity(intent)

                            }
                        }

                        if(!response.body()!!.data.driving_license.isNullOrEmpty()) {
                            Glide.with(this@ProfileActivity).load(response.body()!!.data.driving_license)
                                //.placeholder(resources.getDrawable(R.drawable.profile))
                                .into(imgLicenceBack)
                            driverlicenceTick!!.setOnClickListener {
                                intent = Intent(this@ProfileActivity, ProfilePhotoActivity::class.java)
                                intent.putExtra("image", response.body()!!.data.driving_license)
                                startActivity(intent)

                            }

                        }

                        if(!response.body()!!.data.pan_card.isNullOrEmpty()) {
                            Glide.with(this@ProfileActivity).load(response.body()!!.data.pan_card)
                                //.placeholder(resources.getDrawable(R.drawable.profile))
                                .into(imgSoclScrtyFrnt)
                            panTick!!.setOnClickListener {
                                intent = Intent(this@ProfileActivity, ProfilePhotoActivity::class.java)
                                intent.putExtra("image", response.body()!!.data.pan_card)
                                startActivity(intent)
                            }
                        }

                        if(!response.body()!!.data.vehcile_insurence.isNullOrEmpty()) {
                            Glide.with(this@ProfileActivity).load(response.body()!!.data.vehcile_insurence)
                                //.placeholder(resources.getDrawable(R.drawable.profile))
                                .into(imgSclBack)
                            vehicleInsureTick!!.setOnClickListener {
                                intent = Intent(this@ProfileActivity, ProfilePhotoActivity::class.java)
                                intent.putExtra("image", response.body()!!.data.vehcile_insurence)
                                startActivity(intent)
                            }
                        }

                        if(!response.body()!!.data.reistration_certificate.isNullOrEmpty()) {
                            Glide.with(this@ProfileActivity).load(response.body()!!.data.reistration_certificate)
                                //.placeholder(resources.getDrawable(R.drawable.profile))
                                .into(imgVehiclRegistration)
                            regTick!!.setOnClickListener {
                                intent = Intent(this@ProfileActivity, ProfilePhotoActivity::class.java)
                                intent.putExtra("image", response.body()!!.data.reistration_certificate)
                                startActivity(intent)
                            }
                        }

                        if(!response.body()!!.data.vehicle_permit.isNullOrEmpty()) {
                            Glide.with(this@ProfileActivity).load(response.body()!!.data.vehicle_permit)
                                //.placeholder(resources.getDrawable(R.drawable.profile))
                                .into(imgVehcileInsurance)
                            permitTick!!.setOnClickListener {
                                intent = Intent(this@ProfileActivity, ProfilePhotoActivity::class.java)
                                intent.putExtra("image", response.body()!!.data.vehicle_permit)
                                startActivity(intent)
                            }
                        }

                    } else {
                        rlLoader.visibility = View.GONE
                        // ToastUtil.toast_Long(activity, response.body()!!.msg)
                    }

                }


            }

            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                rlLoader.visibility = View.GONE
                // ToastUtil.toast_Long(activity, t.toString())
            }


        })

    }




}

