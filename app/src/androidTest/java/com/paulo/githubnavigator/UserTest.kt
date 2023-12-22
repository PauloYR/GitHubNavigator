package com.paulo.githubnavigator

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import com.paulo.githubnavigator.model.User
import com.paulo.githubnavigator.network.Output
import com.paulo.githubnavigator.repository.UserRepository
import com.paulo.githubnavigator.screen.Users
import com.paulo.githubnavigator.ui.theme.GitHubNavigatorTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class UserTest: KoinTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockModule: Module
    private lateinit var userRepository: UserRepository

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
    }


    @Test
    fun `list_users`() = runTest {
        `when`(userRepository.getUsers()).thenReturn(flow {
            emit(Output.Success(fakeUsers))
        })

        val mockNavController = mock<NavHostController>()

        composeTestRule.setContent {
            GitHubNavigatorTheme {
                Users(navController = mockNavController)
            }
        }

        advanceTimeBy(500)

        composeTestRule.onNodeWithText("Nome do Usuário 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Nome do Usuário 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Nome do Usuário 3").assertIsDisplayed()
    }

    private suspend fun advanceTimeBy(delayMillis: Long) {
        withContext(Dispatchers.Main) {
            delay(delayMillis)
        }
    }

    @Test
    fun `search_users`() = runTest {
        `when`(userRepository.getUsers()).thenReturn(flow {
            emit(Output.Success(fakeUsers))
        })

        val search = "Paulo"

        val fakeSearchUsers = listOf(
            User(login = "Paulo 1"),
            User(login = "Paulo 2"),
            User(login = "Paulo 3")
        )

        `when`(userRepository.searchUsers(search)).thenReturn(flow {
            emit(Output.Success(fakeSearchUsers))
        })

        val mockNavController = mock<NavHostController>()

        composeTestRule.setContent {
            GitHubNavigatorTheme {
                Users(navController = mockNavController)
            }
        }

        advanceTimeBy(500)

        composeTestRule.onNodeWithText("Busque por usuários").performTextInput(search)

        advanceTimeBy(500)

        composeTestRule.onNodeWithText(search).assertIsDisplayed()

        fakeSearchUsers[0].login?.let { composeTestRule.onNodeWithText(it).assertIsDisplayed() }
    }
}
