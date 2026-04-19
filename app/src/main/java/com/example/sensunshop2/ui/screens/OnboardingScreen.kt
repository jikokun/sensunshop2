package com.example.sensunshop2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensunshop2.ui.theme.SensunDarkGray
import com.example.sensunshop2.ui.theme.SensunLightGray2
import com.example.sensunshop2.ui.theme.SensunOrange
import com.example.sensunshop2.ui.theme.SensunShop2Theme
import kotlinx.coroutines.launch

data class OnboardingPageData(
    val titleNormalText: String,
    val titleHighlightText: String,
    val description: String,
    val imageRes: Int
)

@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit
) {
    val pages = listOf(
        OnboardingPageData(
            titleNormalText = "Descubre profesionales y soluciones en ",
            titleHighlightText = "energía solar",
            description = "Explora nuestro catálogo y conecta con quienes impulsan un futuro más limpio.",
            imageRes = com.example.sensunshop2.R.drawable.onboarding_solar_1
        ),
        OnboardingPageData(
            titleNormalText = "Encuentra los mejores ",
            titleHighlightText = "instaladores",
            description = "Conecta con expertos locales para llevar la energía del sol a tu hogar o negocio.",
            imageRes = com.example.sensunshop2.R.drawable.onboarding_solar_2
        ),
        OnboardingPageData(
            titleNormalText = "Cotiza y compara los mejores ",
            titleHighlightText = "equipos",
            description = "Descubre las últimas tecnologías en paneles y baterías para optimizar tu inversión.",
            imageRes = com.example.sensunshop2.R.drawable.onboarding_solar_3
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onFinishOnboarding) {
                    Text(
                        text = "Omitir",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { position ->
                OnboardingPageContent(pageData = pages[position])
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Indicators
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pages.size) { iteration ->
                    val color = if (pagerState.currentPage == iteration) SensunOrange else SensunLightGray2
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(10.dp)
                    )
                }
            }

            // Bottom Button
            Button(
                onClick = {
                    if (pagerState.currentPage < pages.size - 1) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onFinishOnboarding()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SensunOrange,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (pagerState.currentPage == pages.size - 1) "Comenzar" else "Siguiente",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun OnboardingPageContent(pageData: OnboardingPageData) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val annotatedTitle = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                append(pageData.titleNormalText)
            }
            withStyle(style = SpanStyle(color = SensunOrange)) {
                append(pageData.titleHighlightText)
            }
        }

        Text(
            text = annotatedTitle,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = pageData.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(id = pageData.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    SensunShop2Theme {
        OnboardingScreen(onFinishOnboarding = {})
    }
}