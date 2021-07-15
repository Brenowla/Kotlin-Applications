package com.example.componentes

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.componentes.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener,
    SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonToast.setOnClickListener(this)
        binding.buttonSnack.setOnClickListener(this)
        binding.buttonSpinnerestatico.setOnClickListener(this)
        binding.buttonSpinnerdinamico.setOnClickListener(this)
        binding.buttonObterseek.setOnClickListener(this)
        binding.buttonAtribuirseek.setOnClickListener(this)

        binding.spinnerStatic.onItemSelectedListener = this
        binding.spinnerDinamic.onItemSelectedListener = this

        binding.seekBar.setOnSeekBarChangeListener(this)

        binding.switchOnOff.setOnCheckedChangeListener(this)

        binding.checkOnOff.setOnCheckedChangeListener(this)

        binding.radioOn.setOnCheckedChangeListener(this)
        binding.radioOn.setOnCheckedChangeListener(this)

        loadSpinner()
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.button_toast -> {
                val toast = Toast.makeText(this, "TOAST", Toast.LENGTH_LONG)
            }
            R.id.button_snack -> {
                val snack = Snackbar.make(binding.linearRoot, "Snack", Snackbar.LENGTH_LONG)
                snack.setAction("Desfazer", View.OnClickListener {
                    toast("Desfeito")
                })

                snack.setActionTextColor(Color.BLUE)
                snack.setBackgroundTint(Color.GRAY)

                snack.show()
            }
            R.id.button_spinnerestatico ->{
                val selectedItem = binding.spinnerStatic.selectedItem
                val selectedItemId = binding.spinnerStatic.selectedItemId
                val selectedItemPos = binding.spinnerStatic.selectedItemPosition

                toast("Position: $selectedItemId $selectedItemPos")
            }
            R.id.button_spinnerdinamico ->{
                binding.spinnerStatic.setSelection(2)
            }
            R.id.button_atribuirseek ->{
                binding.seekBar.progress = 25
            }
            R.id.button_obterseek -> {
                toast(binding.seekBar.progress.toString())
            }
        }
    }

    private fun toast(str: String){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    private fun loadSpinner() {
        val mList = listOf("Gramas","Kg","Arroba","Tonelada")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,mList)
        binding.spinnerDinamic.adapter = adapter
    }

    // Spinner
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id){
            R.id.spinner_static -> {
                toast(parent?.getItemAtPosition(position).toString())
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        toast("nothing")
    }


    // Seek
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        // Não tratou pois só tem um
        binding.textSeekBar.text = "Valor Seekbar" + progress.toString()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        toast("Track Started")
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        toast("Track Stoped")
    }


    // Switch
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView?.id){
            R.id.switch_on_off -> {
                toast("Switch: ${if (isChecked) "true" else "false"}")
                //binding.switchOnOff.isChecked = true

            }
            R.id.check_on_off -> {
                // binding.checkOnOff.isChecked = true
                toast("CheckBox: ${if (isChecked) "true" else "false"}")
            }
            R.id.radio_on -> {
                toast("Radio on: ${if (isChecked) "true" else "false"}")
            }
            R.id.radio_off -> {
                toast("Radio off: ${if (isChecked) "true" else "false"}")
            }
        }
    }

}