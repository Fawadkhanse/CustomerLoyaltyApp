package org.example.project.presentation.ui.web
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyColors
import com.multiplatform.webview.web.LoadingState

@Composable
fun WebViewScreenRoute(
    onBack: () -> Unit
) {
    WebViewScreen(
        title = "",
        url = "https://privacy-website-1.onrender.com/",
        onBack = onBack
    )
}


@Composable
fun WebViewScreen(
    title: String,
    url: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val webViewState = rememberWebViewState(url)

    ScreenContainer(
        horizontalPadding = 0.dp,
        verticalPadding = 0.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            WebView(
                state = webViewState,
                modifier = Modifier.fillMaxSize()
            )

            // Loader when loading
            if (webViewState.loadingState is LoadingState.Loading ||
                webViewState.loadingState is LoadingState.Initializing
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = LoyaltyColors.OrangePink
                    )
                }
            }
        }
    }
}

sealed class PageState {
    object Loading : PageState()
    object Finished : PageState()
    data class Error(val message: String?) : PageState()
}
