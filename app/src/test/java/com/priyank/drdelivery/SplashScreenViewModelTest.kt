package com.priyank.drdelivery

import com.priyank.drdelivery.authentication.data.UserStorage
import com.priyank.drdelivery.navigation.Screen
import com.priyank.drdelivery.utils.TestDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashScreenViewModelTest {
    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    private val userStorage: UserStorage = mockk(relaxed = true)

    @Test
    fun `on init with user logged in, should update start destination state to screen detail`() = runTest {
        coEvery { userStorage.isLoggedIn() } returns true
        val viewModel = SplashScreenViewModel(userStorage)
        assertEquals(Screen.Detail.route, viewModel.startDestination.value)
        assertFalse(viewModel.showSplashScreen.value)
    }

    @Test
    fun `on init with user logged in, should update start destination state to screen authentication`() = runTest {
        coEvery { userStorage.isLoggedIn() } returns false
        val viewModel = SplashScreenViewModel(userStorage)
        assertEquals(Screen.Authentication.route, viewModel.startDestination.value)
        assertFalse(viewModel.showSplashScreen.value)
    }
}
