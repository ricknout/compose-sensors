package dev.ricknout.composesensors.demo.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.ricknout.composesensors.demo.model.Demo

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Demo(
    demo: Demo,
    value: String,
    content: @Composable BoxWithConstraintsScope.() -> Unit,
) {
    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text(text = demo.title) }) }) { paddingValues ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart),
                text = value,
            )
            content()
        }
    }
}

@Composable
fun NotAvailableDemo(demo: Demo) {
    Demo(
        demo = demo,
        value = "Not available",
        content = {},
    )
}
