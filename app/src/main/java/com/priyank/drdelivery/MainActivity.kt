package com.priyank.drdelivery

import android.accounts.Account
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.api.client.extensions.android.json.AndroidJsonFactory
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.GmailScopes
import com.google.api.services.gmail.model.ListMessagesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


lateinit var mGoogleSignInClient:GoogleSignInClient
var eb = "e"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().requestScopes(Scope("https://www.googleapis.com/auth/gmail.readonly"))
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        login()

    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)

    }

    fun login(){
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, 1)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            Log.w("Success","${account.displayName}")
            startreading()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Fai", "signInResult:failed code=" + e.statusCode)
        }
    }


    fun startreading(){

        val credential = GoogleAccountCredential.usingOAuth2(
            applicationContext, listOf(GmailScopes.GMAIL_READONLY)
        )
            .setBackOff(ExponentialBackOff())
            .setSelectedAccount(
                Account(
                    GoogleSignIn.getLastSignedInAccount(this)!!.email,"Dr.Delivery"

                )
            )

        val service = Gmail.Builder(
            NetHttpTransport(), AndroidJsonFactory.getDefaultInstance(), credential
        )
            .setApplicationName("YourAppName")
            .build()

        GlobalScope.launch {
            //do the reading
            try {
                val executeResult: ListMessagesResponse? =
                    withContext(Dispatchers.IO) {
                        service.users().messages()?.list("me")?.setQ("to:me")?.execute()
                    }

                // For the sake of test, let's get 1st message on the list
             //   val message: Message? = executeResult?.messages?.get(0)

                // Actual reading of the message, by message ID
                val messageRead = withContext(Dispatchers.IO) {
                    service.users().messages().list(GoogleSignIn.getLastSignedInAccount(this@MainActivity)!!.email).
                    setQ("from:info@net.shiprocket.in").
                        execute()
                    //  ?.get(GoogleSignIn.getLastSignedInAccount(this@MainActivity)!!.email, message?.id)

                }

             for (message in   messageRead.messages){

                 var messageR = withContext(Dispatchers.IO) {
                     service.users().messages()
                         ?.get(GoogleSignIn.getLastSignedInAccount(this@MainActivity)!!.email, message?.id)
                         ?.setFormat("full")?.execute()

                 }
                 Log.d("Email","Email"+ messageR?.payload?.body!!.data)

             }
//                val email = StringUtils.newStringUtf8(Base64.decodeBase64(messageRead?.payload?.parts?.get(0)?.body?.data))
//                Log.d("Email", "TEST"+ StringUtils.newStringUtf8(Base64.decodeBase64 (messageRead?.raw.)))
                // At this point you can get to short version of your message body as messageRead?.snippet
            } catch (e: UserRecoverableAuthIOException) {
                startActivityForResult(e.intent, 1);
            }
        }
    }

    //Do the reading the stuff

}