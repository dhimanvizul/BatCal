package com.example.batcal

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.batcal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var isDecimalPlaced: Boolean = false
    var result: Double = 0.0
    var saved: String = ""
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        sharedPreferences = getSharedPreferences(getString(R.string.preference_fileName), Context.MODE_PRIVATE)
        setContentView(binding.root)

        binding.btn1.setOnClickListener {
            binding.tvInput.text = binding.tvInput.text.toString() + "1"
        }
        binding.btn2.setOnClickListener {
            binding.tvInput.text = binding.tvInput.text.toString() + "2"
        }
        binding.btn3.setOnClickListener {
            binding.tvInput.text = binding.tvInput.text.toString() + "3"
        }
        binding.btn4.setOnClickListener {
            binding.tvInput.text = binding.tvInput.text.toString() + "4"
        }
        binding.btn5.setOnClickListener {
            binding.tvInput.text = binding.tvInput.text.toString() + "5"
        }
        binding.btn6.setOnClickListener {
            binding.tvInput.text = binding.tvInput.text.toString() + "6"
        }
        binding.btn7.setOnClickListener {
            binding.tvInput.text = binding.tvInput.text.toString() + "7"
        }
        binding.btn8.setOnClickListener {
            binding.tvInput.text = binding.tvInput.text.toString() + "8"
        }
        binding.btn9.setOnClickListener {
            binding.tvInput.text = binding.tvInput.text.toString() + "9"
        }
        binding.btn0.setOnClickListener {
            binding.tvInput.text = binding.tvInput.text.toString() + "0"
        }
        binding.btnDot.setOnClickListener {
            if (!isDecimalPlaced) {
                binding.tvInput.text = binding.tvInput.text.toString() + "."
                isDecimalPlaced = true
            }
        }
        binding.apply {
            btnAdd.setOnClickListener {
                if(tvInput.text != "") {
                    saved += tvInput.text.toString()+"+"
                }
                if(tvInput.text != "") {
                    result += tvInput.text.toString().toDouble()
                    tvOutput.text = result.toString()
                    tvInput.text = ""
                }
            }
            btnSub.setOnClickListener {
                if(tvInput.text != "") {
                    saved += tvInput.text.toString()+"-"
                }
                if (tvInput.text != "") {
                    if (result == 0.0) {
                        result = tvInput.text.toString().toDouble() - result
                    } else result -= tvInput.text.toString().toDouble()
                    tvOutput.text = result.toString()
                    tvInput.text = ""
                }
            }
            btnMul.setOnClickListener {
                if(tvInput.text != "") {
                    saved += tvInput.text.toString()+"×"
                }
                if(tvInput.text != "") {
                    if(result == 0.0) {
                        result = tvInput.text.toString().toDouble()
                    }
                    else result *= tvInput.text.toString().toDouble()
                    tvOutput.text = result.toString()
                    tvInput.text = ""
                }
            }
            btndiv.setOnClickListener {
                if(tvInput.text != "") {
                    saved += tvInput.text.toString()+"/"
                }
                if(tvInput.text != "") {
                    if(result == 0.0) {
                        result = tvInput.text.toString().toDouble()
                    }
                    else {
                        if(tvInput.text == "0" || tvInput.text == "-0.0"||tvInput.text == "0.0" || tvInput.text == "-0") {
                            tvOutput.text = "ERROR"
                            tvInput.text = ""
                            return@setOnClickListener
                        }
                        else result /= tvInput.text.toString().toDouble()
                    }
                    tvOutput.text = result.toString()
                    tvInput.text = ""
                }
            }
            btnPlusMinus.setOnClickListener {
                if (tvInput.text == "") tvInput.text = "-"
                else if (tvInput.text.first() != '-') {
                    tvInput.text = (tvInput.text.toString().toDouble()*(-1)).toString()
                }
                else tvInput.text = (tvInput.text.toString().toDouble()*(-1)).toString()
            }
            btnAllClear.setOnClickListener {
                tvInput.text = ""
                tvOutput.text = ""
                result = 0.0
                isDecimalPlaced = false
                if(saved != "") savePreference(saved)
                saved = ""
            }
            btnClear.setOnClickListener {
                if (tvInput.text != "") {
                    if (tvInput.text.last() == '.') isDecimalPlaced = false
                    tvInput.text = tvInput.text.toString().subSequence(0, tvInput.text.length - 1)
                }
            }
            imgEqual.setOnClickListener {
                tvOutput.text = ""
                tvOutput.text = sharedPreferences.getString("input","0.0")
            }
        }
    }
    private fun savePreference(str: String) {
//        sharedPreferences.edit().clear().apply()
        val s = str.subSequence(0,str.length-1).toString()
        sharedPreferences.edit().putString("input",s).apply()
    }
}