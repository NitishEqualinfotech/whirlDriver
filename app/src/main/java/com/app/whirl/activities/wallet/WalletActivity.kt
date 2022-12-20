package com.app.whirl.activities.wallet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.whirl.R
import com.app.whirl.activities.addaccount.AccountDetailsActivity
import com.app.whirl.activities.addaccount.response.DriverAccountDetailsResponse
import com.app.whirl.activities.addaccount.response.UpdateDetailsResponse
import com.app.whirl.network.ApiInterface
import com.app.whirl.utils.ToastUtil
import com.example.ranierilavastone.Utils.StringUtil
import com.metaled.Utils.ConstantUtils
import com.metaled.Utils.SharedPreferenceUtils
import kotlinx.android.synthetic.main.activity_account_details.*
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.activity_your_earned.my_toolbar_earned
import kotlinx.android.synthetic.main.activity_your_earned.rlLoader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WalletActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        my_toolbar_earned.setTitle("")

        my_toolbar_earned.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24)

        my_toolbar_earned.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })

        ll_addaccount.setOnClickListener {
            val intent = Intent(this, AccountDetailsActivity ::class.java)
            startActivity(intent)
        }

        accountdetails()

    }


    private fun accountdetails() {
        rlLoader.visibility = View.VISIBLE
        var hashMap: HashMap<String, String> = HashMap<String, String>()
        hashMap.put("driver_id", StringUtil.getUserId(this@WalletActivity))


        val apiCall = ApiInterface.create().accountdetails(hashMap)
        apiCall.enqueue(object : Callback<AccountDetailsResponse> {
            override fun onResponse(
                call: Call<AccountDetailsResponse>,
                response: Response<AccountDetailsResponse>
            ) {
                if (response.isSuccessful) {
                    rlLoader.visibility = View.GONE
                    if (response.body()!!.success.equals("true")) {

                        if (!response.body()!!.total_amount.isNullOrEmpty()) {
                            tv_totalamount.setText(response.body()!!.total_amount)
                        }
                        if (!response.body()!!.pending_amount.isNullOrEmpty()) {
                            tv_pending_amount.setText(response.body()!!.pending_amount)
                        }
                        if (!response.body()!!.pay_amount.isNullOrEmpty()) {
                            tv_recive_amount.setText(response.body()!!.pay_amount)
                        }
                        if (!response.body()!!.data[0].name.isNullOrEmpty()) {
                            tv_addaccount.visibility=View.GONE
                            holder_name.visibility=View.VISIBLE
                            holder_name.setText(response.body()!!.data[0].name)
                        }
                        if (!response.body()!!.data[0].ac_number.isNullOrEmpty()) {
                            tv_accountnumber.setText(response.body()!!.data[0].ac_number)
                            val number = tv_accountnumber.text.toString()
                            val mask = number.replace("\\w(?=\\w{4})".toRegex(), "*")

                            tv_accountnumber.setText(mask)
                        }

                    } else {
                        rlLoader.visibility = View.GONE
                       // ToastUtil.toast_Long(activity, response.body()!!.msg)
                    }

                }


            }

            override fun onFailure(call: Call<AccountDetailsResponse>, t: Throwable) {
                rlLoader.visibility = View.GONE
               // ToastUtil.toast_Long(activity, t.toString())
            }


        })

    }




    override fun onResume() {
        super.onResume()
        try {
            accountdetails()

        } catch (e: Exception) {


        }
    }

}