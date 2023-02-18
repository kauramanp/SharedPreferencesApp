package com.aman.sharedpreferencesapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ContentInfoCompat.Flags
import com.aman.sharedpreferencesapp.databinding.ActivityMainBinding
import com.aman.sharedpreferencesapp.models.ModelData
import com.google.gson.Gson;

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences : SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    private lateinit var binding: ActivityMainBinding
    var gson = Gson()
    var model = ModelData()
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
                binding.rbFalse.isChecked = true
            }
        }

        if(sharedPreferences.contains("model")){
            var string = sharedPreferences.getString("model","")
            if(string.isNullOrEmpty() ){
                model = gson.fromJson(string, ModelData::class.java)
                binding?.etString?.setText(model.name?:"")
                binding?.etInteger?.setText(model.number)
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
                var stringify = gson.toJson(ModelData(binding.etString.text.toString(), Integer.valueOf(binding.etInteger.text.toString())))
                editor.putString("model",stringify)
                if(binding.rbTrue.isChecked)
                    editor.putBoolean("bool",true)
                else
                    editor.putBoolean("bool",false)
                Toast.makeText(this, resources.getString(R.string.data_saved_successfully), Toast.LENGTH_LONG).show()

                editor.commit()
        }
        }

        binding.btnClear.setOnClickListener {
            AlertDialog.Builder(this).apply{
                setTitle(resources.getString(R.string.delete_prefs))
                setMessage(resources.getString(R.string.delete_prefs_msg))
                setPositiveButton(resources.getString(R.string.yes)){_,_->
                    editor.clear()
                    editor.apply()
                    var intent = Intent(this@MainActivity,MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    this@MainActivity.finish()
                }
                setNegativeButton(resources.getString(R.string.no)){_,_->}
                show()
            }
        }
    }




}