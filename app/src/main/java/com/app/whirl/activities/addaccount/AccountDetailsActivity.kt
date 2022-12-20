package com.app.whirl.activities.addaccount

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.whirl.R
import com.app.whirl.activities.addaccount.response.AddAccountResponse
import com.app.whirl.activities.addaccount.response.DriverAccountDetailsResponse
import com.app.whirl.activities.addaccount.response.UpdateDetailsResponse
import com.app.whirl.network.ApiInterface
import com.app.whirl.utils.NetworkUtils
import com.app.whirl.utils.ToastUtil
import com.example.ranierilavastone.Utils.StringUtil
import com.metaled.Utils.ConstantUtils
import com.metaled.Utils.SharedPreferenceUtils
import kotlinx.android.synthetic.main.activity_account_details.*
import kotlinx.android.synthetic.main.activity_your_earned.rlLoader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountDetailsActivity : AppCompatActivity() {
    var Id: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details)
        //my_toolbar_earned.setTitle("")

        my_toolbar_earnedd.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24)

        my_toolbar_earnedd.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })

        //--For Get Driver Account Details--//


        driveraccountdetails()

        //--For Add Driver Account Details--//


        btnNext.setOnClickListener {
            if (tv_bank_name.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Please Enter Bank Name", Toast.LENGTH_SHORT).show()
            } else if (et_bank_address.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Please Enter Bank Address", Toast.LENGTH_SHORT).show()
            } else if (et_accountholder_name.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Please Enter Account Holder Name", Toast.LENGTH_SHORT).show()
            } else if (tv_account_holder_address.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Please Enter Account Holder Address", Toast.LENGTH_SHORT)
                    .show()
            } else if (et_rounting_number.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Please Enter Routing Number", Toast.LENGTH_SHORT).show()
            } else if (et_account_number.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Please Enter Account Number", Toast.LENGTH_SHORT).show()
            } else {
                if (NetworkUtils.checkInternetConnection(this@AccountDetailsActivity)) {
                    accountdetails(
                        tv_bank_name.text.toString(),
                        et_bank_address.text.toString(),
                        et_accountholder_name.text.toString(),
                        tv_account_holder_address.text.toString(),
                        et_rounting_number.text.toString(),
                        et_account_number.text.toString()

                    )
                }
            }


        }

        //--Update Details--//

        btnupdate.setOnClickListener {
            if (tv_bank_name.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Please Enter Bank Name", Toast.LENGTH_SHORT).show()
            } else if (et_bank_address.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Please Enter Bank Address", Toast.LENGTH_SHORT).show()
            } else if (et_accountholder_name.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Please Enter Account Holder Name", Toast.LENGTH_SHORT).show()
            } else if (tv_account_holder_address.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Please Enter Account Holder Address", Toast.LENGTH_SHORT)
                    .show()
            } else if (et_rounting_number.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Please Enter Routing Number", Toast.LENGTH_SHORT).show()
            } else if (et_account_number.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Please Enter Account Number", Toast.LENGTH_SHORT).show()
            } else {
                if (NetworkUtils.checkInternetConnection(this@AccountDetailsActivity)) {
                    updateaccountdetails(
                        tv_bank_name.text.toString(),
                        et_bank_address.text.toString(),
                        et_accountholder_name.text.toString(),
                        tv_account_holder_address.text.toString(),
                        et_account_number.text.toString(),
                        et_rounting_number.text.toString()

                    )
                }
            }


        }

    }


    private fun driveraccountdetails() {
        rlLoader.visibility = View.VISIBLE
        var hashMap: HashMap<String, String> = HashMap<String, String>()
        hashMap.put("driver_id", StringUtil.getUserId(this@AccountDetailsActivity))

        val apiCall = ApiInterface.create().driveraccountdetails(hashMap)
        apiCall.enqueue(object : Callback<DriverAccountDetailsResponse> {
            override fun onResponse(
                call: Call<DriverAccountDetailsResponse>,
                response: Response<DriverAccountDetailsResponse>
            ) {
                if (response.isSuccessful) {

                    rlLoader.visibility = View.GONE
                    if (response.body()!!.success.equals("true")) {

                        /*    if (!response.body()!!.data[0].bank_name.isNullOrEmpty()) {
                                btnNext.visibility = View.GONE
                                btnupdate.visibility = View.VISIBLE
                            }*/
                        if (tv_bank_name.text.equals("")) {
                            btnNext.visibility = View.VISIBLE
                            btnupdate.visibility = View.GONE
                        } else {
                            btnNext.visibility = View.GONE
                            btnupdate.visibility = View.VISIBLE
                        }
                        SharedPreferenceUtils.getInstance(this@AccountDetailsActivity)
                            ?.setStringValue(ConstantUtils.ID, response.body()!!.data[0].id)

                        if (!response.body()!!.data[0].bank_name.isNullOrEmpty()) {
                            tv_bank_name.setText(response.body()!!.data[0].bank_name)
                        }
                        if (!response.body()!!.data[0].bank_address.isNullOrEmpty()) {
                            et_bank_address.setText(response.body()!!.data[0].bank_address)
                        }
                        if (!response.body()!!.data[0].name.isNullOrEmpty()) {
                            et_accountholder_name.setText(response.body()!!.data[0].name)
                        }
                        if (!response.body()!!.data[0].holder_address.isNullOrEmpty()) {
                            tv_account_holder_address.setText(response.body()!!.data[0].holder_address)
                        }
                        if (!response.body()!!.data[0].ac_number.isNullOrEmpty()) {
                            et_account_number.setText(response.body()!!.data[0].ac_number)
                        }
                        if (!response.body()!!.data[0].ro_number.isNullOrEmpty()) {
                            et_rounting_number.setText(response.body()!!.data[0].ro_number)
                        }


                    } else {

                        rlLoader.visibility = View.GONE
                        // ToastUtil.toast_Long(activity, response.body()!!.msg)
                    }

                }


            }

            override fun onFailure(call: Call<DriverAccountDetailsResponse>, t: Throwable) {
                rlLoader.visibility = View.GONE
                // ToastUtil.toast_Long(activity, t.toString())
            }


        })

    }


    private fun accountdetails(
        bank_name: String,
        bank_address: String,
        name: String,
        holder_address: String,
        ro_number: String,
        ac_number: String
    ) {
        rlLoader.visibility = View.VISIBLE
        var hashMap: HashMap<String, String> = HashMap<String, String>()
        hashMap.put("driver_id", StringUtil.getUserId(this@AccountDetailsActivity))
        hashMap.put("bank_name", bank_name)
        hashMap.put("bank_address", bank_address)
        hashMap.put("name", name)
        hashMap.put("holder_address", holder_address)
        hashMap.put("ro_number", ro_number)
        hashMap.put("ac_number", ac_number)

        val apiCall = ApiInterface.create().addaccount(hashMap)
        apiCall.enqueue(object : Callback<AddAccountResponse> {
            override fun onResponse(
                call: Call<AddAccountResponse>,
                response: Response<AddAccountResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        rlLoader.visibility = View.VISIBLE
                        // binding.rlLoader.visibility=View.GONE


                        if (response.body()!!.success.equals("true")) {
                            /*      Id= SharedPreferenceUtils.getInstance(this@AccountDetailsActivity)?.getStringValue(
                                      ConstantUtils.ID,"")*/



                            rlLoader.visibility = View.VISIBLE
                            onBackPressed()
                        } else {
                            rlLoader.visibility = View.GONE
                            //binding.rlLoader.visibility=View.GONE
                            Toast.makeText(
                                this@AccountDetailsActivity,
                                response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        rlLoader.visibility = View.GONE

                        Toast.makeText(
                            this@AccountDetailsActivity,
                            response.body()!!.msg,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } catch (e: Exception) {
                    rlLoader.visibility = View.GONE
                    //binding.rlLoader.visibility=View.GONE

                }

            }

            override fun onFailure(call: Call<AddAccountResponse>, t: Throwable) {
                rlLoader.visibility = View.GONE
                ToastUtil.toast_Long(this@AccountDetailsActivity, t.toString())
            }


        })

    }


    private fun updateaccountdetails(
        bank_name: String,
        bank_address: String,
        name: String,
        holder_address: String,
        ac_number: String,
        ro_number: String
    ) {
        rlLoader.visibility = View.VISIBLE
        var hashMap: HashMap<String, String> = HashMap<String, String>()
        hashMap.put("driver_id", StringUtil.getUserId(this@AccountDetailsActivity))
        hashMap.put("id",  SharedPreferenceUtils.getInstance(this)?.getStringValue(ConstantUtils.ID, "").toString())
        hashMap.put("bank_name", bank_name)
        hashMap.put("bank_address", bank_address)
        hashMap.put("name", name)
        hashMap.put("holder_address", holder_address)
        hashMap.put("ac_number", ac_number)
        hashMap.put("ro_number", ro_number)


        val apiCall = ApiInterface.create().updateaccountdetails(hashMap)
        apiCall.enqueue(object : Callback<UpdateDetailsResponse> {
            override fun onResponse(
                call: Call<UpdateDetailsResponse>,
                response: Response<UpdateDetailsResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        rlLoader.visibility = View.VISIBLE
                        // binding.rlLoader.visibility=View.GONE

                        if (response.body()!!.success.equals("true")) {
                            rlLoader.visibility = View.GONE
                            onBackPressed()

                        } else {
                            rlLoader.visibility = View.GONE
                            //binding.rlLoader.visibility=View.GONE
                            Toast.makeText(
                                this@AccountDetailsActivity,
                                response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        rlLoader.visibility = View.GONE

                        Toast.makeText(
                            this@AccountDetailsActivity,
                            response.body()!!.msg,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } catch (e: Exception) {
                    rlLoader.visibility = View.GONE
                    //binding.rlLoader.visibility=View.GONE

                }

            }

            override fun onFailure(call: Call<UpdateDetailsResponse>, t: Throwable) {
                rlLoader.visibility = View.GONE
                ToastUtil.toast_Long(this@AccountDetailsActivity, t.toString())
            }


        })

    }


}