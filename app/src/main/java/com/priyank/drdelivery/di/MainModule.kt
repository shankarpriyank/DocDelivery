package com.priyank.drdelivery.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.priyank.drdelivery.R
import com.priyank.drdelivery.authentication.data.UserDetails
import com.priyank.drdelivery.offlineShipmentDetails.data.GetSMS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideGsa(@ApplicationContext context: Context): GoogleSignInAccount? {
        return GoogleSignIn.getLastSignedInAccount(context)
    }

    @Singleton
    @Provides
    fun provideUserDetails(@ApplicationContext context: Context): UserDetails {
        val sharedPreferences = context.getSharedPreferences("accountDetails", MODE_PRIVATE)
        return UserDetails(sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideGoogleSigninClient(@ApplicationContext context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.gcp_credentials))
            .requestScopes(Scope("https://www.googleapis.com/auth/gmail.readonly"))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        return GoogleSignIn.getClient(context, gso)
    }

    @Singleton
    @Provides
    fun provideGetSMS(@ApplicationContext context: Context): GetSMS {
        return GetSMS(context)
    }
}
