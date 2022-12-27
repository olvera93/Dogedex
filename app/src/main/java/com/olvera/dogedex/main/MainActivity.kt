package com.olvera.dogedex.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import coil.annotation.ExperimentalCoilApi
import com.olvera.dogedex.LABEL_PATH
import com.olvera.dogedex.MODEL_PATH
import com.olvera.dogedex.api.ApiResponseStatus
import com.olvera.dogedex.api.ApiServiceInterceptor
import com.olvera.dogedex.auth.LoginActivity
import com.olvera.dogedex.databinding.ActivityMainBinding
import com.olvera.dogedex.dogdetail.DogDetailComposeActivity
import com.olvera.dogedex.doglist.DogListActivity
import com.olvera.dogedex.doglist.DogListComposeActivity
import com.olvera.dogedex.machinelearning.DogRecognition
import com.olvera.dogedex.model.Dog
import com.olvera.dogedex.model.User
import com.olvera.dogedex.settings.SettingsActivity
import org.tensorflow.lite.support.common.FileUtil
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@ExperimentalCoilApi
class MainActivity : AppCompatActivity() {

    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private var isCameraReady = false

    private val viewModel: MainViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // open camera
                setUpCamera()
            } else {
                Toast.makeText(
                    this,
                    "You need accept camera permission to use camera",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = User.getLoggedInUser(this)
        if (user == null) {
            openLoginActivity()
            return
        } else {
            ApiServiceInterceptor.setSessionToken(user.authenticationToken)
        }

        binding.settingsFab.setOnClickListener {
            openSettingsActivity()
        }

        binding.dogListFab.setOnClickListener {
            openDogListActivity()
        }

        viewModel.status.observe(this) { status ->
            when (status) {
                is ApiResponseStatus.Error -> {
                    binding.loadingWheel.visibility = View.GONE
                    Toast.makeText(this, status.messageId, Toast.LENGTH_SHORT).show()
                }

                is ApiResponseStatus.Loading -> binding.loadingWheel.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> binding.loadingWheel.visibility = View.GONE
            }

        }

        viewModel.dog.observe(this) { dog ->
            if (dog != null) {
                openDetailActivity(dog)
            }
        }
        viewModel.dogRecognition.observe(this) { dogRecognition ->
            enableTakePhotoButton(dogRecognition)

        }

        requestCameraPermission()

    }

    private fun openDetailActivity(dog: Dog) {
        val intent = Intent(this, DogDetailComposeActivity::class.java)
        intent.putExtra(DogDetailComposeActivity.DOG_KEY, dog)
        intent.putExtra(DogDetailComposeActivity.IS_RECOGNITION_KEY, true)
        startActivity(intent)
    }

    private fun setUpCamera() {

        binding.cameraPreview.post {
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(binding.cameraPreview.display.rotation)
                .build()

            cameraExecutor = Executors.newSingleThreadExecutor()

            startCamera()
            isCameraReady = true
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.setupClassifier(
            FileUtil.loadMappedFile(this@MainActivity, MODEL_PATH),
            FileUtil.loadLabels(this@MainActivity, LABEL_PATH)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::cameraExecutor.isInitialized) {
            cameraExecutor.shutdown()
        }
    }

    private fun requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // You can use the API that requires the permission.
                    setUpCamera()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    AlertDialog.Builder(this)
                        .setTitle("Aceptame por favor")
                        .setMessage("Acepta la camara")
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            requestPermissionLauncher.launch(
                                Manifest.permission.CAMERA
                            )
                        }
                        .setNegativeButton(android.R.string.cancel) { _, _ ->

                        }.show()
                }
                else -> {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    requestPermissionLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                }
            }
        } else {
            // Open camera
            setUpCamera()
        }

    }

/*    private fun takePhoto() {
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(getOutputPhotoFile()).build()
        imageCapture.takePicture(outputFileOptions, cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: OutputFileResults) {

                }

                override fun onError(error: ImageCaptureException) {
                    Toast.makeText(
                        this@MainActivity,
                        "Error taking photo ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
        )
    }*/

    /*   private fun getOutputPhotoFile(): File {
           val mediaDir = externalMediaDirs.firstOrNull()?.let {
               File(it, resources.getString(R.string.app_name) + ".jpg").apply { mkdirs() }
           }

           return if (mediaDir != null && mediaDir.exists()) {
               mediaDir
           } else {
               filesDir
           }
       }
       */

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the tifecycle owner
            val cameraProvider = cameraProviderFuture.get()
            //Preview
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                viewModel.reconizeImage(imageProxy)
            }


            // bind use cases to camera
            cameraProvider.bindToLifecycle(
                this, cameraSelector,
                preview, imageCapture, imageAnalysis
            )
        }, ContextCompat.getMainExecutor(this))

    }

    private fun enableTakePhotoButton(dogRecognition: DogRecognition) {
        if (dogRecognition.confidence > 70.0) {
            binding.takePhotoFab.alpha = 1f
            binding.takePhotoFab.setOnClickListener {
                viewModel.getDogByMlId(dogRecognition.id)
            }
        } else {
            binding.takePhotoFab.alpha = 0.2f
            binding.takePhotoFab.setOnClickListener(null)
        }

    }

    private fun openDogListActivity() {
        startActivity(Intent(this, DogListComposeActivity::class.java))
    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}