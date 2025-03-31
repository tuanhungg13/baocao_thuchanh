package com.dev.autoreplyapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log

class CallReceiver : BroadcastReceiver() {
    private var lastState: String? = TelephonyManager.EXTRA_STATE_IDLE

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AutoReply", "onReceive triggered")

        if (intent?.action != TelephonyManager.ACTION_PHONE_STATE_CHANGED) return

        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        if (state == null) return

        val prefs = context?.getSharedPreferences("call_prefs", Context.MODE_PRIVATE)

        when (state) {
            TelephonyManager.EXTRA_STATE_RINGING -> {
                val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                Log.d("AutoReply", "Cuộc gọi đến từ: $incomingNumber")

                // Lưu số điện thoại vào prefs
                prefs?.edit()?.putString("incoming_number", incomingNumber)?.apply()
            }

            TelephonyManager.EXTRA_STATE_IDLE -> {
                val savedNumber = prefs?.getString("incoming_number", null)
                Log.d("AutoReply", "Check gọi đến: state=$state, lastState=$lastState, số=$savedNumber")

                if (lastState == TelephonyManager.EXTRA_STATE_IDLE && savedNumber != null) {
                    Log.d("AutoReply", ": $savedNumber")

                    try {
                        val smsManager = SmsManager.getDefault()
                        val message = "Xin lỗi, tôi đang bận. Tôi sẽ gọi lại sau nhé."
                        smsManager.sendTextMessage(savedNumber, null, message, null, null)
                        Log.e("AutoReply", "Đã gửi SMS đến $savedNumber")
                    } catch (e: Exception) {
                        Log.e("AutoReply", "Lỗi gửi SMS: ${e.message}")
                    }

                    // Xóa số sau khi đã gửi
                    prefs.edit()?.remove("incoming_number")?.apply()
                }
            }
        }

        lastState = state
    }
}
