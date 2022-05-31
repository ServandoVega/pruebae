package com.example.pruebaexm.user.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.pruebaexm.R
import com.example.pruebaexm.database.model.Register
import com.example.pruebaexm.databinding.FragmentCreateRegisterBinding
import com.example.pruebaexm.databinding.FragmentRegisterListBinding
import com.example.pruebaexm.user.utils.getFileUri
import com.example.pruebaexm.user.utils.isEmail
import com.example.pruebaexm.user.utils.isPhone
import com.example.pruebaexm.user.utils.required
import com.example.pruebaexm.user.viewmodel.RegisterViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import java.io.File


class CreateRegisterFragment : Fragment() {
    private lateinit var vBind: FragmentCreateRegisterBinding
    private val viewModel: RegisterViewModel by activityViewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var location: Location
    private var currentImageUri: Uri? = null
    lateinit var marker: Marker
    lateinit var mapController: IMapController
    lateinit var geoPoint: GeoPoint
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vBind = FragmentCreateRegisterBinding.inflate(inflater, container, false)
        initView()
        initObservers()
        return vBind.root
    }


    private fun initObservers() {

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initView() {
        initMap()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        getLocation()
        permissionCamara()
        vBind.apply {

            btnTakePhoto.setOnClickListener {
                openCamara()
            }

            btnSave.setOnClickListener {
                if (validateData()) {
                    currentImageUri?.let { it1 ->
                        requireContext().contentResolver.openInputStream(it1)?.readBytes()
                            ?.let { it1 ->
                                Register(
                                    0,
                                    etName.text.toString(),
                                    etSurname.text.toString(),
                                    etPhone.text.toString(),
                                    etEmail.text.toString(),
                                    it1,
                                    location.latitude,
                                    location.longitude,
                                )
                            }?.let { it2 ->
                                viewModel.createRegister(
                                    it2
                                )
                            }
                    }
                }

            }
        }

    }

    private fun permissionCamara() {

        val externalPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.READ_EXTERNAL_STORAGE, false) && permissions.getOrDefault(Manifest.permission.WRITE_EXTERNAL_STORAGE, false)  -> {
                }
                else -> {

                }
            }
        }

        externalPermissionRequest.launch(arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE))

    }

    private fun openCamara() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera")
        currentImageUri = getFileUri(requireContext())
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, currentImageUri)
        resultLauncher.launch(intent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleCameraImage()
            }
        }
    }

    private fun handleCameraImage() {
        Glide.with(this).load(currentImageUri).into(vBind.imgCard)

    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLocation() {
        mapController = vBind.map.apply {
            overlays.clear()
            setUseDataConnection(true)
            setTileSource(TileSourceFactory.MAPNIK)

        }.controller
        mapController.setZoom(18.5)
        marker = Marker(vBind.map)
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    false
                ) && permissions.getOrDefault(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    false
                ) -> {

                    fusedLocationClient.lastLocation.addOnSuccessListener {
                        location = it
                        setPosition(it)
                        vBind.tilCoordinates.text = "Coordenadas: ${it.latitude}, ${it.longitude}"
                    }
                }
                else -> {
                    // No location access granted.
                }
            }
        }
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun initMap() {
        Configuration.getInstance().load(
            requireContext(),
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        )

    }

    private fun setPosition(location: Location) {
        geoPoint = GeoPoint(location.latitude, location.longitude)
        marker.position = geoPoint
        mapController.setCenter(geoPoint)
        vBind.map.overlays.add(marker)
        vBind.map.invalidate()
    }

    private fun validateData(): Boolean {
        var validate = false

        vBind.apply {
            if (tilEmail.isEmail()){
                validate = true
            }
            if (tilPhone.isPhone()) validate = true
            if (tilName.required()) validate = true
            if (tilSurnames.required()) validate = true
            return validate
        }
    }
}