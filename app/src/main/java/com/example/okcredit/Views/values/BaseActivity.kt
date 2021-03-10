package com.san.app.activity


import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.provider.Settings
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.okcredit.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*



open class BaseActivity : AppCompatActivity() {
    var mProgressDialog: Dialog? = null
    internal var mngr: ActivityManager? = null
    internal var bitmap: Bitmap? = null
    var mCurrentPhotoPath = ""
    val REQUEST_CAMERA = 11
    val SELECT_FILE = 12
    internal lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        StatusBar()
        mngr = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        context = this@BaseActivity
    }

    /*@Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MyContextWrapper.wrap(newBase,"en"));

    }*/
    fun setCheckBoxPadding(checkBox: CheckBox?) {
        val scale = this.resources.displayMetrics.density
        checkBox!!.setPadding(
            checkBox.paddingLeft + (5.0f * scale + 0.5f).toInt(),
            checkBox.paddingTop,
            checkBox.paddingRight,
            checkBox.paddingBottom
        )
    }

    fun StatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }

    }

    fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager =
                this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)

        }
    }

    /*fun changeFragment(targetFragment: Fragment) {
        val mFragmentManager = supportFragmentManager
        mFragmentManager.beginTransaction().replace(R.id.frameLayout, targetFragment, "fragment")
            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()

    }

    fun changeFragment(targetFragment: Fragment, from: String) {
        val bundle = Bundle()
        bundle.putString("from", from)
        targetFragment.arguments = bundle
        val mFragmentManager = supportFragmentManager
        mFragmentManager.beginTransaction().replace(R.id.frameLayout, targetFragment, "fragment")
            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()

    }*/
    /* fun changeFragment_back(targetFragment: Fragment) {
         val mFragmentManager = supportFragmentManager
         mFragmentManager.beginTransaction().replace(R.id.frame, targetFragment, "fragment")
             .addToBackStack(null)
             .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
             .commit()

     }




     //for dasthboard frame
     fun changeFragment_backDash(targetFragment: Fragment) {
         val mFragmentManager = supportFragmentManager
         mFragmentManager.beginTransaction().replace(R.id.frameDash, targetFragment, "fragment")
             .addToBackStack(null)
             .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
             .commit()

     }

     fun changeFragmentDash(targetFragment: Fragment) {
         val mFragmentManager = supportFragmentManager
         mFragmentManager.beginTransaction().replace(R.id.frameDash, targetFragment, "fragment")
             .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
             .commit()

     }*/

    ///// Permission function


    fun dismissProgressDialog() {
        if (mProgressDialog != null) {
            try {
                mProgressDialog!!.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    ///// Permission function
    fun requestStoragePermission() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        selectImagePopup()
                    }
                    if (report.isAnyPermissionPermanentlyDenied || report.deniedPermissionResponses.size > 0) {
                        openSettings()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener {
                Toast.makeText(applicationContext, "Error occurred! ", Toast.LENGTH_SHORT).show()
            }
            .onSameThread()
            .check()

    }

    fun selectImagePopup() {
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        val dialog1 = Dialog(this@BaseActivity)
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog1.setContentView(R.layout.gallery_type_picker_bottom_sheet)
        dialog1.setCanceledOnTouchOutside(false)
        dialog1.setCancelable(false)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog1.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog1.show()
        dialog1.window!!.attributes = lp
        dialog1.window!!.setGravity(Gravity.BOTTOM)
        dialog1.window!!.setBackgroundDrawable(ColorDrawable(0))

        val tvTakePhoto = dialog1.findViewById(R.id.camera) as LinearLayout
        val tvChooseImage = dialog1.findViewById(R.id.gallery) as LinearLayout
        val tvCancel = dialog1.findViewById(R.id.btn_cancel) as ImageView

        tvTakePhoto.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (cameraIntent.resolveActivity(packageManager) != null) {
                // Create the File where the photo should go
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()

                } catch (ex: IOException) {
                    // Error occurred while creating the File

                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile))
                    startActivityForResult(cameraIntent, REQUEST_CAMERA)
                }
            }

            dialog1.dismiss()
        }

        tvChooseImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE)
            dialog1.dismiss()
        }

        tvCancel.setOnClickListener { dialog1.dismiss() }

        dialog1.show()
    }


    fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    @Throws(IOException::class)
    fun createImageFile(): File {

        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        /* val storageDir = Environment.getExternalStoragePublicDirectory(
             Environment.DIRECTORY_PICTURES
         )*/
        val storageDir = this.getExternalFilesDir(
            Environment.DIRECTORY_PICTURES
        )
        val image = File.createTempFile(
            imageFileName, // prefix
            ".jpg", // suffix
            storageDir      // directory
        )

        mCurrentPhotoPath = "file:" + image.absolutePath
        return image
    }

    fun getMimeType(context: Context, uri: Uri): String? {
        val extension: String?

        //Check uri format to avoid null
        if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //If scheme is a content
            val mime = MimeTypeMap.getSingleton()
            extension = mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension =
                MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path!!)).toString())

        }

        return extension
    }

    @Throws(IOException::class)
    fun rotateImageIfRequired(img: Bitmap, selectedImage: String): Bitmap {
        // ExifInterface ei = new ExifInterface(selectedImage.getPath());
        val ei = ExifInterface(selectedImage)
        val orientation =
            ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270)
            else -> img
        }
    }

    private fun rotateImage(img: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        img.recycle()
        return rotatedImg
    }
}