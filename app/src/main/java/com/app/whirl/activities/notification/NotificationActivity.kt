package com.app.whirl.activities.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.whirl.R
import com.app.whirl.activities.notification.Adapters.NotificationAdapterNew
import com.app.whirl.activities.notification.NotificationModels.Data
import com.app.whirl.activities.notification.NotificationModels.NotificationResponse

import com.app.whirl.network.ApiInterface

import com.app.whirl.utils.NetworkUtils


import com.example.ranierilavastone.Utils.StringUtil

import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_notification.my_toolbar_earned
import kotlinx.android.synthetic.main.activity_notification.rlLoader
import kotlinx.android.synthetic.main.activity_notification.rvList
import kotlinx.android.synthetic.main.activity_notification.tvNorecord
import kotlinx.android.synthetic.main.activity_your_earned.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationActivity : AppCompatActivity() {
    lateinit var list:ArrayList<Data>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        list= ArrayList()
        if(NetworkUtils.checkInternetConnection(this)){
            getNofication()
        }else{
            tvNorecord.visibility=View.VISIBLE
        }

        my_toolbar_earned.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24)

        my_toolbar_earned.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })

    }


    private fun getNofication() {
        rlLoader.visibility = View.VISIBLE
        var hashMap: HashMap<String, String> = HashMap<String, String>()
        hashMap.put("driver_id", StringUtil.getUserId(this))
      

        val apiCall = ApiInterface.create().getNotification(hashMap)
        apiCall.enqueue(object : Callback<NotificationResponse> {
            override fun onResponse(
                call: Call<NotificationResponse>,
                response: Response<NotificationResponse>
            ) {
                rlLoader.visibility = View.GONE
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        if(response.body()!!.data!=null){
                            list=response.body()!!.data
                            setList()
                        }else{
                            setList()
                        }



                    } else {
                        setList()

                    }

                } else {
                    setList()

                }


            }

            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                rlLoader.visibility = View.GONE
                setList()


            }


        })

    }
    fun setList(){
        if(list.size>0){
            tvNorecord.visibility=View.GONE
        }else{
            tvNorecord.visibility=View.VISIBLE
        }
        rvList.layoutManager = LinearLayoutManager(this)

        rvList.layoutManager=
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        rvList.adapter= NotificationAdapterNew(this, list)
    }
}