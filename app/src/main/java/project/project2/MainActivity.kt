package project.project2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var etWebUrl: EditText
    private lateinit var etEmailAddress: EditText
    private lateinit var etEmailSubject: EditText
    private lateinit var etEmailBody: EditText
    private lateinit var etShareText: EditText
    private lateinit var btnOpenWeb: Button
    private lateinit var btnSendEmail: Button
    private lateinit var btnShareText: Button
    private lateinit var btnDialPhone: Button
    private lateinit var btnOpenMaps: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        initViews()

        // Set click listeners
        setClickListeners()
    }

    private fun initViews() {
        etWebUrl = findViewById(R.id.etWebUrl)
        etEmailAddress = findViewById(R.id.etEmailAddress)
        etEmailSubject = findViewById(R.id.etEmailSubject)
        etEmailBody = findViewById(R.id.etEmailBody)
        etShareText = findViewById(R.id.etShareText)
        btnOpenWeb = findViewById(R.id.btnOpenWeb)
        btnSendEmail = findViewById(R.id.btnSendEmail)
        btnShareText = findViewById(R.id.btnShareText)
        btnDialPhone = findViewById(R.id.btnDialPhone)
        btnOpenMaps = findViewById(R.id.btnOpenMaps)
    }

    private fun setClickListeners() {
        btnOpenWeb.setOnClickListener {
            openWebPage()
        }

        btnSendEmail.setOnClickListener {
            sendEmail()
        }

        btnShareText.setOnClickListener {
            shareText()
        }

        btnDialPhone.setOnClickListener {
            dialPhoneNumber()
        }

        btnOpenMaps.setOnClickListener {
            openMapLocation()
        }
    }

    private fun openWebPage() {
        val url = etWebUrl.text.toString().trim()
        if (url.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_enter_url), Toast.LENGTH_SHORT).show()
            return
        }

        // Ensure URL has protocol
        val webUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
            "https://$url"
        } else {
            url
        }

        try {
            val intent = Intent(Intent.ACTION_VIEW, webUrl.toUri())
            startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Error opening web page", e)
            Toast.makeText(this, getString(R.string.toast_cannot_open_url), Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendEmail() {
        val emailAddress = etEmailAddress.text.toString().trim()
        val subject = etEmailSubject.text.toString().trim()
        val body = etEmailBody.text.toString().trim()

        if (emailAddress.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_enter_email), Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:".toUri()
            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Error sending email", e)
            Toast.makeText(this, getString(R.string.toast_no_email_app), Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareText() {
        val textToShare = etShareText.text.toString().trim()

        if (textToShare.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_enter_share_text), Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, textToShare)
        }

        try {
            startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
        } catch (e: Exception) {
            Log.e(TAG, "Error sharing text", e)
        }
    }

    private fun dialPhoneNumber() {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:1234567890".toUri()
        }
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Error dialing phone number", e)
        }
    }

    private fun openMapLocation() {
        val geoUri = "geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+CA".toUri()
        val intent = Intent(Intent.ACTION_VIEW, geoUri)
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Error opening maps", e)
        }
    }
}