package com.example.calcm

import androidx.compose.ui.test.*
import kotlin.test.Test
import kotlin.test.Ignore

class UiTests {
    @OptIn(ExperimentalTestApi::class)
    @Ignore // Ignored due to skiko environment issues in this environment
    @Test
    fun testInitialState() = runComposeUiTest {
        setContent {
            App()
        }
        
        onNodeWithText("Initial Sum").assertExists()
        onNodeWithText("Calculate").assertExists()
    }
}
