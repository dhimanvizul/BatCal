package com.example.batcal

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.mariuszgromada.math.mxparser.Expression
import com.example.batcal.databinding.ActivityMainBinding
import java.text.DecimalFormat
import kotlin.math.min

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var isDecimalPlaced: Boolean = false
    var isMinusPlaced: Boolean = false
    var isOpPlaced: Boolean = false
    var result: Double = 0.0
    var saved: String = ""
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        sharedPreferences = getSharedPreferences(getString(R.string.preference_fileName), Context.MODE_PRIVATE)
        setContentView(binding.root)

        binding.btn0.setOnClickListener {
            if(binding.tvInput.text.toString() != "0") {
                binding.tvInput.text = addToInputText("0")
                isOpPlaced = false
            }
        }
        binding.btn1.setOnClickListener {
            binding.tvInput.text = addToInputText("1")
            isOpPlaced = false
        }
        binding.btn2.setOnClickListener {
            binding.tvInput.text = addToInputText("2")
            isOpPlaced = false
        }
        binding.btn3.setOnClickListener {
            binding.tvInput.text = addToInputText("3")
            isOpPlaced = false
        }
        binding.btn4.setOnClickListener {
            binding.tvInput.text = addToInputText("4")
            isOpPlaced = false
        }
        binding.btn5.setOnClickListener {
            binding.tvInput.text = addToInputText("5")
            isOpPlaced = false
        }
        binding.btn6.setOnClickListener {
            binding.tvInput.text = addToInputText("6")
            isOpPlaced = false
        }
        binding.btn7.setOnClickListener {
            binding.tvInput.text = addToInputText("7")
            isOpPlaced = false
        }
        binding.btn8.setOnClickListener {
            binding.tvInput.text = addToInputText("8")
            isOpPlaced = false
        }
        binding.btn9.setOnClickListener {
            binding.tvInput.text = addToInputText("9")
            isOpPlaced = false
        }
        binding.btnDot.setOnClickListener {
            if (!isDecimalPlaced) {
                binding.tvInput.text = addToInputText(".")
                isDecimalPlaced = true
            }
        }

        binding.apply {
            btnAdd.setOnClickListener {
                if(tvInput.text != "") {
                    saved += tvInput.text.toString()+"+"
                }
                if(tvInput.text != "" && !isOpPlaced) {
                    tvInput.text = addToInputText("+")
                    isOpPlaced = true
                }
                if(tvInput.text != "" && tvInput.text.last() != '+' && isOpPlaced) {
                    tvInput.text = tvInput.text.subSequence(0,tvInput.text.length-1)
                    tvInput.text = addToInputText("+")
                }
            }
            btnSub.setOnClickListener {
                if(tvInput.text != "") {
                    saved += tvInput.text.toString()+"-"
                }
                if (tvInput.text != "" && !isOpPlaced) {
                    tvInput.text = addToInputText("-")
                    isOpPlaced = true
                }
                if(tvInput.text != "" && tvInput.text.last() != '-' && isOpPlaced) {
                    tvInput.text = tvInput.text.subSequence(0,tvInput.text.length-1)
                    tvInput.text = addToInputText("-")
                }
            }
            btnMul.setOnClickListener {
                if(tvInput.text != "") {
                    saved += tvInput.text.toString()+"×"
                }
                if(tvInput.text != "" && !isOpPlaced) {
                    tvInput.text = addToInputText("×")
                    isOpPlaced = true
                }
                if(tvInput.text != "" && tvInput.text.last() != '×' && isOpPlaced) {
                    tvInput.text = tvInput.text.subSequence(0,tvInput.text.length-1)
                    tvInput.text = addToInputText("×")
                }
            }
            btndiv.setOnClickListener {
                if(tvInput.text != "") {
                    saved += tvInput.text.toString()+"/"
                }
                if(tvInput.text != "" && !isOpPlaced) {
                    tvInput.text = addToInputText("÷")
                    isOpPlaced = true
                }
                if(tvInput.text != "" && tvInput.text.last() != '÷' && isOpPlaced) {
                    tvInput.text = tvInput.text.subSequence(0,tvInput.text.length-1)
                    tvInput.text = addToInputText("÷")
                }
            }
            btnPlusMinus.setOnClickListener {
                if(tvInput.text == "") {
                    tvInput.text = addToInputText("-")
                    isMinusPlaced = true
                }

                if (tvInput.text.last() != '-' && !isMinusPlaced) {
                    tvInput.text = addToInputText("-")
                    isMinusPlaced = true
                }
                if(tvInput.text.last() == '-' && isMinusPlaced) {
                    tvInput.text.replace(Regex("-"),"")
                    isMinusPlaced = false
                }
            }
            btnAllClear.setOnClickListener {
                tvInput.text = ""
                tvOutput.text = ""
                result = 0.0
                isDecimalPlaced = false
                isOpPlaced = false
                if(saved != "") savePreference(saved)
                saved = ""
            }
            btnClear.setOnClickListener {
                if (tvInput.text != "") {
                    if (tvInput.text.last() == '.') isDecimalPlaced = false
                    tvInput.text = tvInput.text.toString().subSequence(0, tvInput.text.length - 1)
                    isOpPlaced = false
                }
            }
            imgEqual.setOnClickListener {
                showResult()
            }
        }
    }
    private fun addToInputText(buttonValue: String): String {
        return binding.tvInput.text.toString() + buttonValue
    }
    private fun getInputExpression(): String {
        var expression = binding.tvInput.text.replace(Regex("÷"), "/")
        expression = expression.replace(Regex("×"), "*")
        return expression
    }

    private fun showResult() {
        try {
            val expression = getInputExpression()
            if(expression.contains("÷0")) {
                binding.tvOutput.text = "ERROR"
                return
            }
            val result = Expression(expression).calculate()
            if (result.isNaN()) {
                binding.tvOutput.text = "ERROR"
            } else {
                binding.tvOutput.text = DecimalFormat("0.######").format(result).toString()
            }
        } catch (e: Exception) {

            binding.tvOutput.text = "ERROR"
        }
    }
    private fun savePreference(str: String) {
//        sharedPreferences.edit().clear().apply()
        val s = str.subSequence(0,str.length-1).toString()
        sharedPreferences.edit().putString("input",s).apply()
    }
}