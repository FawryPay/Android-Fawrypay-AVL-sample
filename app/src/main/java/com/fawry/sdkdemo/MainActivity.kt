package com.fawry.sdkdemo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fawry.fawrypay.FawrySdk
import com.fawry.fawrypay.interfaces.FawryPreLaunch
import com.fawry.fawrypay.interfaces.FawrySdkCallbacks
import com.fawry.fawrypay.models.AVLInfo
import com.fawry.fawrypay.models.BillItems
import com.fawry.fawrypay.models.CardDetailsModel
import com.fawry.fawrypay.models.CloseAction
import com.fawry.fawrypay.models.FawryLaunchModel
import com.fawry.fawrypay.models.LaunchCustomerModel
import com.fawry.fawrypay.models.LaunchMerchantModel
import com.fawry.fawrypay.models.PayableItem


class MainActivity : AppCompatActivity() {
    val chargeItems = ArrayList<PayableItem>()

    //Replace all data below with your own data
    var baseUrl = "https://atfawrystaging.atfawry.com/"

    //customer info
    var customerName = "testName"
    var customerMobile = "01234567890"
    var customerEmail =
        "test@test.com" //required in saving cards for payment with card tokenization

    //merchant info
    var merchantCode = "siYxylRjSPyg6dz0QH/y9A=="
    var merchantSecretCode = "086f55c1-463b-425a-9342-f75b094c8b3e"

    val beneficiaryWalletNumber = "12345678911"
    val avlValue = 15.00
    val billingAcct = "12345678911"
    val avlInfo = AVLInfo(
        billTypeCodeWithFees = 11,
        billTypeCodeWithoutFees = 13,
        internationalBANs = arrayListOf("123456", "654321"),
        BANValidationSize = 6,
        onUsAvlFees = 7.0,
        offUsAvlFees = 11.0,
        minValue = 13.0,
        maxValue = 21.0
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chargeItems.add(
            BillItems(
                itemId = "897fa8e81be26df25db592e81c31c",
                description = "",
                quantity = "1",
                price = "7.00",
            )
        )


        val btnAVL = findViewById<Button>(R.id.btn_AVL)
        btnAVL.setOnClickListener {
            startAVL()
        }

        val btnScreenlessCreditCardPayment = findViewById<Button>(R.id.btn_screenless_card_payment)
        btnScreenlessCreditCardPayment.setOnClickListener {
            startScreenlessCreditCardPayment()
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
                    secretCode = merchantSecretCode
                ),
                allow3DPayment = true,
                skipReceipt = false,
                skipLogin = true,
                beneficiaryWalletNumber = beneficiaryWalletNumber,
                avlValue = null,
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
                    Log.d("avl_sdk", "on success ${msg}")
                    Toast.makeText(this@MainActivity, "on success ${msg}", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onPaymentCompleted(msg: String, data: Any?) {
                    Log.d("avl_sdk", "on payment completed $data")
                    Toast.makeText(
                        this@MainActivity,
                        "on payment completed $data",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(error: String, closeAction: CloseAction?) {
                    Log.d("avl_sdk", "on failure ${error}")
                    Log.d("avl_sdk", "close action: ${closeAction?.name}")
                    Toast.makeText(
                        this@MainActivity,
                        "on failure ${error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun startScreenlessCreditCardPayment() {
        FawrySdk.launchCreditcardByCardDetailsPayment(
            activity = this,
            _languages = FawrySdk.Languages.ENGLISH,
            _baseUrl = baseUrl,
            FawryLaunchModel(
                launchCustomerModel = LaunchCustomerModel(
                    customerEmail = customerEmail,
                    customerMobile = customerMobile
                ),
                launchMerchantModel = LaunchMerchantModel(
                    merchantCode = merchantCode,
                    secretCode = merchantSecretCode,
                    merchantRefNum = "${System.currentTimeMillis()}"

                ),
                allow3DPayment = true,
                chargeItems = chargeItems,
                skipReceipt = false,
                skipLogin = true,
                authCaptureMode = true,
                paymentMethods = FawrySdk.PaymentMethods.ALL
            ), object : FawrySdkCallbacks {
                override fun onPreLaunch(onPreLaunch: FawryPreLaunch) {
                    onPreLaunch.onContinue()
                }

                override fun onInit() {

                }

                override fun onPaymentCompleted(msg: String, data: Any?) {
                    Log.d("avl_sdk", "onPaymentCompleted msg: ${data}")
                    Toast.makeText(
                        this@MainActivity,
                        "on payment completed $data",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onSuccess(msg: String, data: Any?) {
                    Toast.makeText(this@MainActivity, "on success ${msg}", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("avl_sdk", "msg: ${data}")
                }


                override fun onFailure(error: String, closeAction: CloseAction?) {
                    Log.d("avl_sdk", "error msg: ${error}")
                    Log.d("avl_sdk", "close action: ${closeAction?.name}")
                    Toast.makeText(this@MainActivity, "on failure ${error}", Toast.LENGTH_SHORT)
                        .show()
                }
                }, CardDetailsModel("5123450000000008","Khater","39","01","100")
        )
    }

}