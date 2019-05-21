package edu.washington.bycao96.arewethereyet

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.telephony.SmsManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {
    companion object {
        val REQUEST_SMS_SEND_PERMISSION = 1234
    }

        private var msg : String = ""
        private var contentValid : Boolean = false
        private var numberValid : Boolean = false
        private var intervalValid : Boolean = false
        private var number : String = ""
        private var interval : String = ""
        private var timer : Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val contentTextView = findViewById<TextView>(R.id.textViewContent)
        //val numberTextView = findViewById<TextView>(R.id.textViewNumber)
        //val timeTextView = findViewById<TextView>(R.id.textViewTime)
        val contentEditText = findViewById<EditText>(R.id.editTextContent)
        val numberEditText = findViewById<EditText>(R.id.editTextPhoneNumber)
        val timeEditText = findViewById<EditText>(R.id.editTextTimeInterval)
        val button = findViewById<Button>(R.id.button)
        button.isEnabled = false

        // Setup the changelistener for contentEdittext, if the content is edited, check its validation
        contentEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                //validate the input

                if(s.isNullOrEmpty()){
                    Toast.makeText(this@MainActivity,"Text content can not be empty!",Toast.LENGTH_LONG).show()
                }else{
                    contentValid = true
                    button.isEnabled=contentValid && numberValid && intervalValid
                }
            }
        })
        // Setup the changelistener for numberEdittext, if the content is edited, check its validation
        numberEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                //validate the input
                if(s.isNullOrEmpty()){
                    Toast.makeText(this@MainActivity,"Number can not be empty",Toast.LENGTH_LONG).show()
                }else{
                    numberValid = true
                    button.isEnabled=contentValid && numberValid && intervalValid
                }
            }
        })
        // Setup the changelistener for intervalEdittext, if the content is edited, check its validation
        timeEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                //validate the input
                if(s.isNullOrEmpty()){
                    Toast.makeText(this@MainActivity,"Time interval needs to be valid",Toast.LENGTH_LONG).show()
                }else{
                    intervalValid = true
                    button.isEnabled=contentValid && numberValid && intervalValid
                }
            }
        })
        //Seeting the button, if the input has error, the button is not gonna work


        button.setOnClickListener {
            //Handle the valid output
            msg = contentEditText.text.toString()
            number = numberEditText.text.toString()
            interval =timeEditText.text.toString()


            val btnText = button.text.toString()
            if(btnText == "Start")
            {   button.setText("Stop")
                timer?.scheduleAtFixedRate(timerTask( msg,number), 0, interval.toLong() * 60 * 1000)
                Toast.makeText(this,"SMS sent to $number: $msg",Toast.LENGTH_LONG).show()
            } else {
                timer?.cancel()
                button.setText("Start")
                Toast.makeText(this,"The program is terminated",Toast.LENGTH_LONG).show()
            }

        }
    }
    inner class timerTask(message : String, phonenumber : String): TimerTask() {
        private val msg = message
        private val phonenumber = phonenumber
        val smsManager = SmsManager.getDefault()
        override fun run() {
                if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@MainActivity,
                        arrayOf(Manifest.permission.SEND_SMS),
                        0)

                } else {
                    smsManager.sendTextMessage(
                        phonenumber,
                        null,
                        msg,
                        null,
                        null
                    )
                }
            }


        }

    }

