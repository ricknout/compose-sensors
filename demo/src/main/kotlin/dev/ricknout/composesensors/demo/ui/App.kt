package dev.ricknout.composesensors.demo.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.ricknout.composesensors.demo.R
import dev.ricknout.composesensors.demo.model.Demo
import dev.ricknout.composesensors.demo.ui.accelerometer.AccelerometerDemo
import dev.ricknout.composesensors.demo.ui.gyroscope.GyroscopeDemo
import dev.ricknout.composesensors.demo.ui.light.LightDemo
import dev.ricknout.composesensors.demo.ui.magneticfield.MagneticFieldDemo
import dev.ricknout.composesensors.demo.ui.pressure.PressureDemo
import dev.ricknout.composesensors.demo.ui.proximity.ProximityDemo

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DemoApp(onItemClick: (demo: Demo) -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            val demos = Demo.values().toList().filter { demo -> demo != Demo.NONE }
            items(demos) { demo ->
                ListItem(
                    headlineContent = { Text(text = demo.title) },
                    leadingContent = {
                        Icon(painter = painterResource(id = demo.iconResourceId), contentDescription = null)
                    },
                    modifier = Modifier.clickable { onItemClick(demo) },
                )
            }
        }
    }
}

@Composable
fun DemoNavigation(
    demo: Demo,
    onItemClick: (demo: Demo) -> Unit,
) {
    Crossfade(targetState = demo) {
        when (it) {
            Demo.NONE -> DemoApp(onItemClick = onItemClick)
            Demo.ACCELEROMETER -> AccelerometerDemo()
            Demo.GYROSCOPE -> GyroscopeDemo()
            Demo.LIGHT -> LightDemo()
            Demo.MAGNETIC_FIELD -> MagneticFieldDemo()
            Demo.PRESSURE -> PressureDemo()
            Demo.PROXIMITY -> ProximityDemo()
        }
    }
}
