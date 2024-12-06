package pt.isec.amov.tp201101617120180146432019139650.ui.screens

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier

import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import org.osmdroid.config.Configuration
import pt.isec.amov.tp201101617120180146432019139650.AMovApp
import pt.isec.amov.tp201101617120180146432019139650.ui.theme.LocationMapsTheme
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.AddCategoryViewModelFactory
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.AuthenticationViewModel
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.AuthenticationViewModelFactory
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.CategoryViewModel
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.LocationViewModel
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.LocationViewModelFactory
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.MapsViewModel
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.MapsViewModelFactory
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.PointOfInterestViewModel
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.PointOfInterestViewModelFactory


class MainActivity : ComponentActivity() {

    val app by lazy { application as AMovApp }
    private val categoryViewModel : CategoryViewModel by viewModels{
        AddCategoryViewModelFactory(app.categoryRepository, app.pointOfInterestRepository)
    }
    private val authenticationViewModel : AuthenticationViewModel by viewModels{
        AuthenticationViewModelFactory(app.authenticationRepository)
    }
    private val mapsViewModel : MapsViewModel by viewModels{
        MapsViewModelFactory(app.locationHandler,app.locations,app.pointOfInterestViewModel,app.pointOfInterestRepository)
    }
    private val locationViewModel : LocationViewModel by viewModels{
        LocationViewModelFactory(app.locationRepository, app.pointOfInterestRepository)
    }
    private val pointOfInterestViewModel : PointOfInterestViewModel by viewModels {
        PointOfInterestViewModelFactory(
            app.pointOfInterestRepository,
            app.categoryRepository,
            app.locationRepository,
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AMovApp.setAppLocale("en")


        //Ir buscar as configurações defauld dadas pela comunidade
        Configuration.getInstance().load(
            this,
            PreferenceManager.getDefaultSharedPreferences(this)
        )

        setContent {
            LocationMapsTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    MainScreen(
                        locationViewModel = locationViewModel,
                        pointOfInterestViewModel = pointOfInterestViewModel,
                        categoryViewModel = categoryViewModel,
                        authenticationViewModel = authenticationViewModel,
                        mapsViewModel = mapsViewModel)
                }
            }
        }
        verifyPermissions()
    }



    override fun onResume() {
        super.onResume()
        mapsViewModel.startLocationUpdates()
    }

    private fun verifyPermissions() : Boolean{
        mapsViewModel.coarseLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        mapsViewModel.fineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mapsViewModel.backgroundLocationPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else
            mapsViewModel.backgroundLocationPermission = mapsViewModel.coarseLocationPermission || mapsViewModel.fineLocationPermission

        if (!mapsViewModel.coarseLocationPermission && !mapsViewModel.fineLocationPermission) {
            basicPermissionsAuthorization.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            return false
        } else
            verifyBackgroundPermission()
        return true
    }

    private val basicPermissionsAuthorization = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        mapsViewModel.coarseLocationPermission = results[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        mapsViewModel.fineLocationPermission = results[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        mapsViewModel.startLocationUpdates()
        verifyBackgroundPermission()
    }

    private fun verifyBackgroundPermission() {
        if (!(mapsViewModel.coarseLocationPermission || mapsViewModel.fineLocationPermission))
            return

        if (!mapsViewModel.backgroundLocationPermission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            ) {
                val dlg = AlertDialog.Builder(this)
                    .setTitle("Background Location")
                    .setMessage(
                        "This application needs your permission to use location while in the background.\n" +
                                "Please choose the correct option in the following screen" +
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                                    " (\"${packageManager.backgroundPermissionOptionLabel}\")."
                                else
                                    "."
                    )
                    .setPositiveButton("Ok") { _, _ ->
                        backgroundPermissionAuthorization.launch(
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        )
                    }
                    .create()
                dlg.show()
            }
        }
    }

    private val backgroundPermissionAuthorization = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        mapsViewModel.backgroundLocationPermission = result
        Toast.makeText(this,"Background location enabled: $result", Toast.LENGTH_LONG).show()
    }


}