package com.example.cookingntraveler

import android.os.AsyncTask

/*
Code taken from https://bartsimons.me/create-a-loading-screen-with-kotlin-in-android-studio/
loading animation taken from https://lottiefiles.com/9844-loading-40-paperplane?lang=zh_CN
created by Jeffrey Christopher
 */
interface LoadingImplementation {
    fun onFinishedLoading()
}

class LoadingAsync(private val listener: LoadingImplementation) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        for (i in 0 until 10) {
            Thread.sleep(1000)
        }
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)

        listener.onFinishedLoading()
    }
}