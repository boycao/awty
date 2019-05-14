package edu.washington.bycao96.arewethereyet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contentTextView = findViewById<TextView>(R.id.textViewContent)
        val numberTextView = findViewById<TextView>(R.id.textViewNumber)
        val timeTextView = findViewById<TextView>(R.id.textViewTime)
        val contentEditText = findViewById<EditText>(R.id.editTextContent)
        val numberEditText = findViewById<EditText>(R.id.editTextPhoneNumber)
        val timeEditText = findViewById<EditText>(R.id.editTextTimeInterval)
        val button = findViewById<Button>(R.id.button)

        var toastMsg : String = ""
        var hasError : Boolean = false

        var msg : String = contentEditText.text.toString()
        var number : String = numberEditText.text.toString()
        var interval : String = timeEditText.text.toString()
        button.setOnClickListener {
            // handle the invalid input
            if(msg.isNullOrEmpty()){
                toastMsg += "Text content can not be empty!"
                hasError = true
            }else if (number.isNullOrEmpty()){
                toastMsg += "Number can not be empty"
                hasError = true
            }else if(interval.isNullOrEmpty() || interval.toInt()<0){
                toastMsg += "Time Interval need to be valid"
                hasError = true
            }
            // if the input is invalid, make the toast massage
            if(hasError){
                val toast = Toast.makeText(this,toastMsg,Toast.LENGTH_LONG)
                toast.show()
            }else{
                //Handle the valid output
            }

        }
    }
}
