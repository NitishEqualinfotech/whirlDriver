package com.app.whirl.network


import com.app.whirl.activities.earned.Models.EarnResponse
import com.app.whirl.activities.notification.NotificationModels.NotificationResponse
import com.app.whirl.activities.otp.OtpVerifyModel.OtpVerifyResponse
import com.app.whirl.activities.profile.ProfileModels.GetProfileResponse
import com.app.whirl.activities.profile.ProfileModels.UpdateProfileResponse
import com.app.whirl.activities.wallet.AccountDetailsResponse
import com.app.whirl.activities.addaccount.response.AddAccountResponse
import com.app.whirl.activities.addaccount.response.DriverAccountDetailsResponse
import com.app.whirl.activities.addaccount.response.UpdateDetailsResponse
import com.app.whirl.model.Login
import com.app.whirl.ui.home.AcceptTripModels.AcceptTripResponse
import com.app.whirl.ui.home.DriverTripModels.DriverTripResponse
import com.app.whirl.ui.home.LocationModels.LocationUpdateResponse
import com.app.whirl.ui.home.OTPVerifyModels.OtpMatchResponse
import com.app.whirl.ui.home.OffLineDetailModels.OffLineDetailResponse
import com.app.whirl.ui.home.UpdateTokenModels.UpdateFCMTokenResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface WhirlService {
    @GET("")
    fun otpCheck(): Call<List<Login>>

    @POST("driver/driverLoginotp")
    //   @FormUrlEncoded
//    fun loginOtp( @Field("mobile") mobile:String ):Call<List<Login>>
    fun loginOtp(@Body mobile: HashMap<String, String>): Call<LoginApiResponse>

    @POST("driver/driverSignup")
    //   @FormUrlEncoded
    fun driverSignup(@Body mobile: HashMap<String, String>): Call<SignupApiResponse>


    @POST("driver/driverotpverify")
    // @FormUrlEncoded
    fun otpVerify(@Body mobile: HashMap<String, String>): Call<OtpVerifyResponse>


    @POST("driver/resend_otp")
    //   @FormUrlEncoded
    fun driverResendOtp(@Body mobile: HashMap<String, String>): Call<SignupApiResponse>
/* @Multipart
    @POST(ApiConstant.API_UPLOAD_IMAGE)
    Call<ApiResponse> uploadImage(@Header("Content-Language") String language, @Part MultipartBody.Part image,
                                  @Part("image_type") RequestBody imageType, @Part("result_key") RequestBody requestBody);
*/


    @Multipart
    @POST("driver/editdriverProfile")
    fun uploadDocument(
        @Part("userId") userId: RequestBody,
        @Part("fname") fname: RequestBody,
        @Part("lname") lname: RequestBody,
        @Part("email") email: RequestBody,
        @Part("earn_type") earnTYpe: RequestBody,
        @Part("type") type: RequestBody,
        @Part file: MultipartBody.Part?
    ): Call<UploadDocumentApiResponse>

    @POST("driver/offlinehistory")
    fun getOffLineDetail(@Body request: HashMap<String, String>): Call<OffLineDetailResponse>

    @POST("driver/drivertrip")
    fun getDriverTrip(@Body request: HashMap<String, String>): Call<DriverTripResponse>

    @POST("driver/accepttrip")
    fun acceptTrip(@Body request: HashMap<String, String>): Call<AcceptTripResponse>

    @POST("driver/rejecttrip")
    fun rejectTrip(@Body request: HashMap<String, String>): Call<AcceptTripResponse>


    @POST("driver/driverlocation")
    fun updateLocation(@Body request: HashMap<String, String>): Call<LocationUpdateResponse>


    @POST("driver/bookingotpverify")
    fun BookingOtp(@Body request: HashMap<String, String>): Call<OtpMatchResponse>

    @POST("driver/completetrip")
    fun completeTrip(@Body request: HashMap<String, String>): Call<AcceptTripResponse>


    @Multipart
    @POST("driver/editdriverProfilenew")
    fun uploadDocument2(
        @Part("driver_id") userId: RequestBody?,
        @Part("fname") fname: RequestBody?,
        @Part("lname") lname: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("earn_type") earnTYpe: RequestBody?,
        @Part("tag_no") tag_no: RequestBody?,
        @Part profile: MultipartBody.Part?,
        @Part licence: MultipartBody.Part?,
        @Part pan: MultipartBody.Part?,
        @Part vehicleins: MultipartBody.Part?,
        @Part permit: MultipartBody.Part?,
        @Part rc: MultipartBody.Part?
    ): Call<UpdateProfileResponse>


    @Multipart
    @POST("driver/editdriverProfilenew")
    fun editDocument(
        @Part("driver_id") userId: RequestBody?,
        @Part profile_photo: MultipartBody.Part?,
        @Part social_photo: MultipartBody.Part?,
        @Part driving_license: MultipartBody.Part?,
        @Part pan_card: MultipartBody.Part?,
        @Part vehcile_insurence: MultipartBody.Part?,
        @Part reistration_certificate: MultipartBody.Part?,
        @Part vehicle_permit: MultipartBody.Part?,
        @Part("email") email: RequestBody?,
        @Part("fname") fname: RequestBody?,
        @Part("lname") lname: RequestBody?,
        @Part("earn_type") earn_type: RequestBody?,
        @Part("tag_no") tag_no: RequestBody?,
        @Part("vehicle_name") vehicle_name: RequestBody?,
        @Part("vehicle_type") vehicle_type: RequestBody?,
        //@Part("driver_id") driver_id: RequestBody?,

        //@Part profile: MultipartBody.Part?
    ): Call<UpdateProfileResponse>

    @POST("driver/notification")
    fun getNotification(@Body request: HashMap<String, String>): Call<NotificationResponse>

    @POST("driver/allhistory")
    fun getEarnList(@Body request: HashMap<String, String>): Call<EarnResponse>


    @POST("driver/updatefcmtoken")
    fun updateFcmToken(@Body request: HashMap<String, String>): Call<UpdateFCMTokenResponse>

    @POST("driver/accountdetails")
    fun accountdetails(@Body request: HashMap<String, String>): Call<AccountDetailsResponse>

    @POST("driver/getdriverprofile")
    fun getdriverprofile(@Body request: HashMap<String, String>): Call<GetProfileResponse>

    @POST("driver/addbank")
    fun addaccount(@Body request: HashMap<String, String>): Call<AddAccountResponse>

    @POST("driver/updatebank")
    fun updateaccountdetails(@Body request: HashMap<String, String>): Call<UpdateDetailsResponse>

    @POST("driver/bankdetails")
    fun driveraccountdetails(@Body request: HashMap<String, String>): Call<DriverAccountDetailsResponse>

    @POST("driver/send_notification")
    fun sendnotificaiotn(@Body request: HashMap<String, String>): Call<NotificationResponse>

}