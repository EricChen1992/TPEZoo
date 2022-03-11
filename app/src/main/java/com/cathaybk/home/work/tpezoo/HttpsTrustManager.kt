package com.cathaybk.home.work.tpezoo

import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

class HttpsTrustManager() : X509TrustManager{
    private val _AcceptedIssuers : Array<X509Certificate> = arrayOf()

    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {

    }

    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {

    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return _AcceptedIssuers
    }

    fun allowALLSSL(){
        HttpsURLConnection.setDefaultHostnameVerifier(HostnameVerifier { s, sslSession -> true })

        var context : SSLContext

        var trustManager = arrayOf(HttpsTrustManager())

        try {
            context = SSLContext.getInstance("TLS")
            context.init(null, trustManager, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(context.socketFactory)
        } catch (e : NoSuchAlgorithmException){
            e.printStackTrace()
        } catch (e : KeyManagementException){
            e.printStackTrace()
        }


    }

}