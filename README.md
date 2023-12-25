# 

# **FawryPay-AVL android SDK**

Accept popular payment methods with a single client-side implementation.

## **Before You Start**

Use this integration if you want your iOS application to:

-   Accept cards and other payment methods.
-   Save and display cards for reuse.

Make sure you have an active FawryPay account, or [**create an account**](https://atfawry.fawrystaging.com/merchant/register).


### **How Android SDK Looks Like**
<img src="https://github.com/FawryPay/Android-Fawrypay-AVL-sample/blob/master/Docs/1.png" width="300"/>    <img src="https://github.com/FawryPay/Android-Fawrypay-AVL-sample/blob/master/Docs/2.png" width="300"/>

[**Download**](https://drive.google.com/drive/folders/1T__IkSKiM21zHcpeGfKF0xTQNCE6YtKk) and test our sample application.
------------------------------------------------------------------------
### **How it works**
<img src="https://github.com/FawryPay/Android-Fawrypay-AVL-sample/blob/master/Docs/3.png" width="900"/>


On this page, we will walk you through the Android SDK integration steps:

1.  InstallingFawryPaySDK.
2.  InitializeandConfigureFawryPayAndroidSDK.
3.  Override the SDK colors.
4.   Return payment processing information and inform your client of the payment result.

## **Step 1: Installing FawryPaySDK**
This document illustrates how our gateway can be integrated into your iOS application in simple and easy steps. Please follow the steps in order to integrate the FawryPay iOS SDK in your application.


1.  Add the following repository to your (root) `build.gradle`
<!-- -->

    repositories { ...
    jcenter()
    maven { url 'https://nexus.mobile.fawry.io/repository/maven-releases/' } 
    }
2. And add the following to your (app) `build.gradle`
<!-- -->

    dependencies {
    ...
    implementation 'com.fawry.fawrypay:avl:0.0.9' 
    }

3. Add the following to your `Manifest.xml`
<!-- -->

    <application
    ...
    tools:replace="android:allowBackup" />

4. Finally add the following property to your `build.properties`
<!-- -->

    android.enableJetifier=true

## **Step 2: Initialize AVL Android SDK**
1. Create an instance of
    - LaunchCustomerModel
    - LaunchMerchantModel
    - FawryLaunchModel

and pass the required parameters (Required and optional parameters are determined below).
![](https://github.com/FawryPay/Android-Fawrypay-AVL-sample/blob/master/Docs/4.png)


LaunchCustomerModel
| **PARAMETER**     | **TYPE** | **REQUIRED** | **DESCRIPTION**                                 | **EXAMPLE**                                        |
|---------------|---------------|---------------|---------------|---------------|
| customerName      | String   | optional     | \-                                              | Name Name                                          |
| customerEmail     | String   | optional     | \-                                              | [email\@email.com](mailto:email@email.com)         |
| customerMobile    | String   | optional     | \-                                              | +0100000000                                        |
| customerProfileId | String   | optional     | \-                                              | 1234                                               |

AVLInfo
| **PARAMETER**     | **TYPE** | **REQUIRED** | **DESCRIPTION**                                 | **EXAMPLE**                                          |
|---------------|---------------|---------------|---------------|---------------|
| billTypeCodeWith Fees| Int      | mandatory    | \-                                                | 4433                                              |
| billTypeCodeWith outFees| Int      | mandatory    | \-                                                | 3344                                              |
| internationalBANs| ArrayList <String>| mandatory    |BANs related to the bank to use the billTypeCodeWithoutFees| arrayListOf("51234 5","5506900          |
| BANValidationSize| Int      | mandatory    | \-                                                | 6                                                 |
| onUsAvlFees      | Double   | mandatory    | \-                                                | 5.0                                               |
| offUsAvlFees     | Double   | mandatory    | \-                                                | 7.0                                               |



