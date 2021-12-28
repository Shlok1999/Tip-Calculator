package com.example.tipcalculator

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
private const val INITIAL_TIP_PERCENT=15
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sB_tipBar.progress = INITIAL_TIP_PERCENT
        tV_tipPercent.text = "$INITIAL_TIP_PERCENT %"

        updateTipDescription(INITIAL_TIP_PERCENT)

        sB_tipBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                tV_tipPercent.text = "$progress %"
                computeTipandTotal()
                updateTipDescription(progress)


            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
//                Toast.makeText(this@MainActivity,"",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
//                Toast.makeText(this@MainActivity,"Change done",Toast.LENGTH_SHORT).show()
            }

        })


            eT_price.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               }

                override fun afterTextChanged(s: Editable?) {
                    Log.i("Hello", "After change: $s ")
                    computeTipandTotal()
                }

            })

    }

    private fun updateTipDescription(tipPercent: Int) {
        val tipDesc = when (tipPercent) {
            in 0..9 -> "poor"
            in 10..14 -> "Acceptable"
            in 15..19 -> "Good"
            in 20..24 -> "Great"
            else -> "Amazing"
        }
        tV_tipDescription.text= tipDesc
        //Update the color based on tip description

        val color = ArgbEvaluator().evaluate(
            (tipPercent.toFloat() / sB_tipBar.max),
            ContextCompat.getColor(this, R.color.worstTip),
            ContextCompat.getColor(this,R.color.bestTip)
        ) as Int
        tV_tipDescription.setTextColor(color)


    }

    private fun computeTipandTotal() {
        if(eT_price.text.isEmpty()){
            tV_tipTotal.text=""
            tV_total.text=""
            return
        }
        //Set the value of the base and tip percentage
        val base_amount = eT_price.text.toString().toDouble()
        val tip_percent = sB_tipBar.progress
        Log.i("tip percent", "$tip_percent %")

        //Compute the tip and total
        val tip_amount = base_amount * tip_percent / 100
        Log.i("Base amount", "$base_amount")
        Log.i("tip amount", "$tip_amount")
        val totalAmount = base_amount + tip_amount

        //Update the UI
        //%.2f stops the calculation at 2 digits after decimal
        tV_tipTotal.text = "%.2f".format(tip_amount)
        tV_total.text= "%.2f".format(totalAmount)

    }
}