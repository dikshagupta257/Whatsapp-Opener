package com.codingblocksmodules.whatsappopener

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lateinit var number:String

        //to get the entered number in our variable if the action mentioned is performed
        if(intent.action == Intent.ACTION_PROCESS_TEXT){
            number = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
            number = number.filter { !it.isWhitespace() }
        }else{
            Toast.makeText(this , "Please select the number first to open that chat.", Toast.LENGTH_SHORT).show()
        }

        //starting our whatsapp chat screen only if the selected text is a number else just show thw text
        if(number.isDigitsOnly()){
            startWhatsapp(number)
        }else{
            Toast.makeText(this , "Please check the number again.", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    //function to open the whatsapp chat with that particular number
    private fun startWhatsapp(number: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.whatsapp")

        //putting a check in order to get the correct format of our number according to what the whatsapp accepts
        val data:String = if(number[0] == '+'&& number.length == 13){
            number.substring(1)
        }else if(number.length == 10){
            "91"+number
        }else{
            number
        }

        //giving the number data to our intent
        intent.data = Uri.parse("https://wa.me/$data")

        //to act accordingly, if the intent we want to open is available in the phone or not+

        try{
            startActivity(intent)
        }catch (e: ActivityNotFoundException){
            Toast.makeText(this , "Please install whatsapp" , Toast.LENGTH_SHORT).show()
        }

        //finish the activity once this function is completed successfully so that the activity does not open up automatically
        finish()
    }
}