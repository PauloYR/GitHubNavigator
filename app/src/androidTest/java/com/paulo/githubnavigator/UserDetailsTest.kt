package com.paulo.githubnavigator

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.paulo.githubnavigator.model.User
import com.paulo.githubnavigator.network.Output
import com.paulo.githubnavigator.repository.UserRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class UserDetailsTest : KoinTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockModule: Module
    private lateinit var userRepository: UserRepository
    lateinit var navController: TestNavHostController
    private val fakeUsers = listOf(
        User(login = "Nome do Usuário 1"),
        User(login = "Nome do Usuário 2"),
        User(login = "Nome do Usuário 3")
    )

    @Before
    fun setup() {
        userRepository = mock()
        mockModule = module {
            single { userRepository }
        }
        loadKoinModules(mockModule)

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppNavHost(navController = navController)
        }
    }

    @Test
    fun `get_user_details`() = runTest {
        `when`(userRepository.getUsers()).thenReturn(flow {
            emit(Output.Success(fakeUsers))
        })
        val username = fakeUsers[0].login ?: ""

        `when`(userRepository.getInfoUser(username)).thenReturn(flow {
            emit(Output.Success(fakeUsers[0]))
        })

        `when`(userRepository.getInfoRepositorysByUser(username)).thenReturn(flow {
            emit(Output.Success(listOf()))
        })

        advanceTimeBy(500)


        composeTestRule.onNodeWithTag("rowUserDetails_${username}").performClick()
    }
}