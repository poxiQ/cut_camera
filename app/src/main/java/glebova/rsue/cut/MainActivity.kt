package glebova.rsue.cut

import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageActivity

class MainActivity : AppCompatActivity() {

    private val cropActivityResultContact = object: ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(16,9)
                .getIntent(this@MainActivity)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }
    }

    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chooseImage: Button = findViewById(R.id.chooseImg)
        val croppedImage: ImageView = findViewById(R.id.croppedImg)

        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContact){
            it?.let { uri ->
                croppedImage.setImageURI(uri)
            }
        }

        chooseImage.setOnClickListener{
            cropActivityResultLauncher.launch(null)
        }
    }
}