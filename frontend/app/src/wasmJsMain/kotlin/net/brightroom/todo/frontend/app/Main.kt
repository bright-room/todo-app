package net.brightroom.todo.frontend.app

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow

/**
 * Bright Room Todo フロントエンドアプリケーション エントリーポイント
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(
        title = "Bright Room Todo",
        canvasElementId = "ComposeTarget",
    ) {
        App()
    }
}
