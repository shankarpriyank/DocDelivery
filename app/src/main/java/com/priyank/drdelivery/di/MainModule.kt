package com.priyank.drdelivery.di

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.room.Room
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.priyank.drdelivery.R
import com.priyank.drdelivery.authentication.data.UserDetails
import com.priyank.drdelivery.shipmentDetails.data.local.EmailDatabase
import com.priyank.drdelivery.shipmentDetails.data.remote.GetEmails
import com.priyank.drdelivery.shipmentDetails.data.repository.EmailRepositoryImpl
import com.priyank.drdelivery.shipmentDetails.domain.repository.EmailRepository
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
    @Provides
    @Singleton
    fun providesGetEmails(gsc: GoogleSignInClient, userDetails: UserDetails): GetEmails {
        return GetEmails(gsc, userDetails)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: EmailDatabase,
        api: GetEmails
    ): EmailRepository {
        return EmailRepositoryImpl(db.emailDao, api)
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): EmailDatabase {
        return Room.databaseBuilder(
            app, EmailDatabase::class.java, "email_db"
        ).build()
    }
}
