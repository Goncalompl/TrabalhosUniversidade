package pt.isec.amov.tp201101617120180146432019139650

import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import android.location.LocationManager
import pt.isec.amov.tp201101617120180146432019139650.data.repos.AuthenticationRepository
import pt.isec.amov.tp201101617120180146432019139650.data.repos.CategoryRepository
import pt.isec.amov.tp201101617120180146432019139650.data.repos.LocationRepository
import pt.isec.amov.tp201101617120180146432019139650.data.repos.PointOfInterestRepository
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.LocationViewModel
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.PointOfInterestViewModel
import pt.isec.ans.locationmaps.utils.location.LocationHandler
import pt.isec.ans.locationmaps.utils.location.LocationManagerHandler
import java.util.Locale

class AMovApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setAppLocale("en")
    }
    val locationHandler : LocationHandler by lazy{
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        LocationManagerHandler(locationManager)
    }

    companion object {
        private const val DEFAULT_LANGUAGE = "en"

        fun setAppLocale(language: String) {
            val locale = Locale(language)
            Locale.setDefault(locale)

            val resources = Resources.getSystem()
            val configuration = Configuration(resources.configuration)
            configuration.setLocale(locale)

            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
    }

    val categoryRepository by lazy { CategoryRepository() }
    val locationRepository by lazy { LocationRepository() }
    val pointOfInterestRepository by lazy { PointOfInterestRepository() }
    val authenticationRepository by lazy { AuthenticationRepository() }
    val locations by lazy { LocationViewModel(LocationRepository(),pointOfInterestRepository) }
    val pointOfInterestViewModel by lazy { PointOfInterestViewModel(pointOfInterestRepository,categoryRepository,locationRepository) }


}