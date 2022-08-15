package com.aman.sharedpreferencesapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aman.sharedpreferencesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences : SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(sharedPreferences.contains("string"))
            binding.etString.setText(sharedPreferences.getString("string",""))
        if(sharedPreferences.contains("int"))
            binding.etInteger.setText(sharedPreferences.getInt("int",0).toString())
        if(sharedPreferences.contains("float"))
            binding.etFloat.setText(sharedPreferences.getFloat("float",0f).toString())
        if(sharedPreferences.contains("long"))
            binding.etLong.setText(sharedPreferences.getLong("long",0L).toString())
        if(sharedPreferences.contains("bool"))
        {
            var isChecked = sharedPreferences.getBoolean("bool",false)
            if(isChecked){
                binding.rbTrue.isChecked = true
            }else{
                binding.rbFalse.isChecked = false

            }
        }
        binding.btnSave.setOnClickListener {
            if(binding.etInteger.text.toString().isNullOrEmpty()){
                binding.etInteger.error = resources.getString(R.string.enter_integer_to_save)
                binding.etInteger.requestFocus()
            }else if(binding.etFloat.text.toString().isNullOrEmpty()){
                binding.etFloat.error = resources.getString(R.string.enter_float_to_save)
                binding.etFloat.requestFocus()
            }else if(binding.etLong.text.toString().isNullOrEmpty()){
                binding.etLong.error = resources.getString(R.string.enter_long_to_save)
                binding.etLong.requestFocus()
            }else if(binding.etString.text.toString().isNullOrEmpty()){
                binding.etString.error = resources.getString(R.string.enter_string_to_save)
                binding.etString.requestFocus()
            }else if(binding.rg.checkedRadioButtonId == -1){
                Toast.makeText(this, resources.getString(R.string.select_boolean_value_to_save), Toast.LENGTH_LONG).show()
            }else{
                editor.putInt("int",binding.etInteger.text.toString().toInt())
                editor.putFloat("float",binding.etFloat.text.toString().toFloat())
                editor.putLong("long",binding.etLong.text.toString().toLong())
                editor.putString("string",binding.etString.text.toString())
                if(binding.rbTrue.isChecked)
                    editor.putBoolean("bool",true)
                else
                    editor.putBoolean("bool",false)
                Toast.makeText(this, resources.getString(R.string.data_saved_successfully), Toast.LENGTH_LONG).show()

                editor.commit()
        }
        }
    }




}