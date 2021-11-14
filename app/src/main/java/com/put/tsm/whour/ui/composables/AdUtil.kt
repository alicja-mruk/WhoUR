package com.put.tsm.whour.ui.composables

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.put.tsm.whour.R

var interstitialAdLocal: InterstitialAd? = null

fun Context.loadInterstitial() {
    InterstitialAd.load(
        this,
        this.getString(R.string.ad_id_rewarded),
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                interstitialAdLocal = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                interstitialAdLocal = interstitialAd
            }
        }
    )
}

fun Context.addInterstitialCallbacks() {
    interstitialAdLocal?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
        }

        override fun onAdShowedFullScreenContent() {
            interstitialAdLocal = null
            loadInterstitial()
        }

        override fun onAdDismissedFullScreenContent() {
        }
    }
}

fun Context.showInterstitial() {
    interstitialAdLocal?.let {
        it.show(this as Activity)
    }
}