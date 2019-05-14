package edu.washington.bycao96.arewethereyet

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
        private var msg : String = ""
        private var contentError : Boolean = false
        private var numberError : Boolean = false
        private var intervalError : Boolean = false
        private var number : String = ""
        private var interval : String = ""
        private var timer : Timer = Timer("toast", true)
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

        // Setup the changelistener for contentEdittext, if the content is edited, check its validation
        contentEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                //validate the input

                if(s.isNullOrEmpty()){
                    Toast.makeText(this@MainActivity,"Text content can not be empty!",Toast.LENGTH_LONG).show()
                }else{
                    contentError = true
                    button.isEnabled=contentError && numberError && intervalError
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
                    numberError = true
                    button.isEnabled=contentError && numberError && intervalError
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
                    intervalError = true
                    button.isEnabled=contentError && numberError && intervalError
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
            if(btnText == "Start"){
                button.setText("Stop")
                timer.scheduleAtFixedRate(timerTask(this, msg,number), 0, interval.toLong() * 60 * 1000)

            } else {
                timer.cancel()
                button.setText("Start")
                Toast.makeText(this,"The program is terminated",Toast.LENGTH_LONG).show()
            }

        }
    }
    inner class timerTask(context: Context, message : String, phonenumber : String): TimerTask() {
        private val Context = context
        private val message = msg
        private val phonenumber = number
        override fun run() {
            runOnUiThread(Runnable {
                val toast = Toast.makeText(Context, "$phonenumber$message", Toast.LENGTH_LONG).show()
            })
        }

    }

}
