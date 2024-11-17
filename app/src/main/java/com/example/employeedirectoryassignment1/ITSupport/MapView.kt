package com.example.employeedirectoryassignment1.ITSupport

import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import android.Manifest
import android.location.Location
import androidx.compose.foundation.layout.Spacer
import com.example.employeedirectoryassignment1.ITRoomDB.Task
import com.example.employeedirectoryassignment1.ITRoomDB.TaskDatabase

import com.example.employeedirectoryassignment1.R
import com.example.employeedirectoryassignment1.ui.theme.buttonContainer
import com.example.employeedirectoryassignment1.ui.theme.buttonText
import com.example.employeedirectoryassignment1.ui.theme.dimens
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapView(navController: NavHostController, taskId: Int) {

    val context = LocalContext.current
    val database = TaskDatabase.getDatabase(context)

    val taskFlow = database.taskDao().getAllTasks()
    var taskClicked by remember { mutableStateOf<Task?>(null) }

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val defaultLocation = LatLng(45.4215, -75.6972) // Ottawa's coordinates
    var userLocation by remember { mutableStateOf(defaultLocation) }
    var hasPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
    }
    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            hasPermission = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    userLocation = LatLng(location.latitude, location.longitude)
                }
            }
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // Use LaunchedEffect to collect data and log it
    LaunchedEffect(Unit) {
        taskFlow.collect { tasks ->
            tasks.forEach { task ->
                Log.d("MapView", "Task: ${task.clientFirstName} ${task.clientLastName}, Address: ${task.address}")
            }
        }
        taskClicked = database.taskDao().getTaskById(taskId)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.techfix)) }
            )
        },
        content = { padding ->
            GoogleMapComposeView(userLocation, taskClicked)

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium3))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .height(MaterialTheme.dimens.buttonHeight),
                onClick = {
                    navController.navigate("navigationScreen")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.buttonContainer,
                    contentColor = MaterialTheme.colorScheme.buttonText
                ),
                shape = RoundedCornerShape(size = 4.dp)
            ) {
                Text(
                    text = stringResource(R.string.go_back),
                    style = MaterialTheme
                        .typography.labelMedium.copy(fontWeight = FontWeight.Medium)
                )
            }
        }
    )

}

@Composable
fun GoogleMapComposeView(userLocation: LatLng, task: Task?) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 12f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = userLocation),
            title = "Your Location",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
        )
        if (task != null) {
            Marker(
                state = MarkerState(position = LatLng(task.lat, task.lon)),
                title = "${task.clientFirstName} ${task.clientLastName}",
                snippet = task.address,
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            )
        }

    }
}
