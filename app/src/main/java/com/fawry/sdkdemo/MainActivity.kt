package com.fawry.sdkdemo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.mylibrary.FawrySdk
import com.app.mylibrary.interfaces.FawryPreLaunch
import com.app.mylibrary.interfaces.FawrySdkCallbacks
import com.app.mylibrary.models.*


class MainActivity : AppCompatActivity() {
    //Replace all data below with your own data
    var baseUrl = "USE_PROVIDED_BASE_URL"

    //customer info
    var customerName = "testName"
    var customerMobile = "01234567890"
    var customerEmail =
        "test@test.com" //required in saving cards for payment with card tokenization

    //merchant info
    var merchantCode = "USE_PROVIDED_MERCHANT_CODE"
    var merchantSecretCode = "USE_PROVIDED_SECRET_KEY"

    val beneficiaryName = "testName"
    val beneficiaryWalletNumber = "12345678911"
    val avlFees = 1.00
    val avlValue = 1.00
    val billingAcct = "01234567890"
    val avlInfo = AVLInfo(billTypeCodeWithFees = 11,
    billTypeCodeWithoutFees = 1234,
    internationalBANs = arrayListOf("123456","654321"),
    BANValidationSize = 6)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAVL = findViewById<Button>(R.id.btn_AVL)
        btnAVL.setOnClickListener {
            startAVL()
        }

    }

    private fun startAVL() {
        FawrySdk.launchAVL(
            this, FawrySdk.Languages.ENGLISH, baseUrl,
            FawryLaunchModel(
                launchCustomerModel = LaunchCustomerModel(
                    customerName = customerName,
                    customerEmail = customerEmail,
                    customerMobile = customerMobile
                ),
                launchMerchantModel = LaunchMerchantModel(
                    merchantCode = merchantCode,
                    secretCode = merchantSecretCode,
                    merchantRefNum = "${System.currentTimeMillis()}"
                ),
                allow3DPayment = true,
                skipReceipt = false,
                skipLogin = true,
                beneficiaryName = beneficiaryName,
                beneficiaryWalletNumber = beneficiaryWalletNumber,
                avlFees = avlFees,
                avlValue = avlValue,
                billingAcct = billingAcct,
                avlInfo = avlInfo
            ),
            object : FawrySdkCallbacks {
                override fun onPreLaunch(onPreLaunch: FawryPreLaunch) {
                    onPreLaunch.onContinue()
                }

                override fun onInit() {

                }

                override fun onSuccess(msg: String, data: Any?) {
                    Log.d("avl_sdk","on success ${msg}")
                    Toast.makeText(this@MainActivity, "on success ${msg}", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onPaymentCompleted(msg: String, data: Any?) {
                    Log.d("avl_sdk","on payment completed $data")
                    Toast.makeText(
                        this@MainActivity,
                        "on payment completed $data",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(error: String) {
                    Log.d("avl_sdk","on failure ${error}")
                    Toast.makeText(
                        this@MainActivity,
                        "on failure ${error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

}