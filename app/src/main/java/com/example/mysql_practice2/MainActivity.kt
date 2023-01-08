package com.example.mysql_practice2


import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class MainActivity : AppCompatActivity() {

    lateinit var text: TextView
    lateinit var errorText: TextView
    lateinit var showButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text = findViewById(R.id.names)
        errorText = findViewById(R.id.error)
        showButton = findViewById(R.id.show_btn)

        showButton.setOnClickListener {
            Toast.makeText(this,"Button Clicked",Toast.LENGTH_LONG).show()
            Log.d("Test","Button Clicked")
            var task = Task()
            task.execute()
        }

    }

    inner class Task : AsyncTask<Void, Void, Void>() {
        var records = ""
        var error = ""

        override fun doInBackground(vararg p0: Void?): Void? {
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance()
                var conn = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/pavandb","root","Sadguru@1520")
                Log.d("Testing","OK!")
                var statement : Statement = conn.createStatement()
                var resultSet : ResultSet = statement.executeQuery("SELECT * FROM mystudents;")
                while (resultSet.next()){
                    records += resultSet.getString(1)+" "+resultSet.getString(2)+"\n"
                }
            }catch (e: Exception){
                error = e.toString()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            text.text = records
            if(error != ""){
                errorText.text = error
            }
            super.onPostExecute(result)
        }
    }
}