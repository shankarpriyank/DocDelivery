package com.priyank.drdelivery.authentication

import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.priyank.drdelivery.R
import com.priyank.drdelivery.authentication.data.UserStorage
import com.priyank.drdelivery.authentication.model.Info
import com.priyank.drdelivery.navigation.Screen
import com.priyank.drdelivery.utils.TestDispatcherRule
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    private val userStorage: UserStorage = mockk(relaxed = true)
    private val googleSignInClient: GoogleSignInClient = mockk(relaxed = true)
    private val viewModel = LoginViewModel(
        userStorage = userStorage,
        gsc = googleSignInClient,
    )

    @Test
    fun `on data(), should return info list`() {
        val expected = listOf(
            Info("Auto-track orders with \n email sync", R.drawable.ic_autotrack),
            Info("Get Realtime Delivery Updates", R.drawable.ic_realtime),
            Info("Your Data is safe, we do everything on the device itself", R.drawable.ic_privacy_png)
        )
        assertEquals(expected, viewModel.data())
    }

    @Test
    fun `on fetchSignInUser(), should update user and navigate back to screen detail`() {
        val navHostController: NavHostController = mockk(relaxed = true)
        viewModel.fetchSignInUser(
            id = "id",
            name = "name",
            email = "email",
            profilePhotoUrl = "profile photo url",
            navHostController = navHostController,
        )

        verify {
            userStorage.updateUser(
                id = "id",
                name = "name",
                email = "email",
                profilePhotoUrl = "profile photo url",
            )
            navHostController.popBackStack()
            navHostController.navigate(Screen.Detail.route)
        }
    }

    @Test
    fun `on signOutUser(), should call user sign out and sign out from Google`() {
        viewModel.signOutUser()

        verify {
            userStorage.signOut()
            googleSignInClient.signOut()
        }
    }
}
