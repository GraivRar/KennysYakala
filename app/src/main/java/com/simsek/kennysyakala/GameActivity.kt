package com.simsek.kennysyakala

import android.content.DialogInterface
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.simsek.kennysyakala.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    var score = 0
    val kennysArray = arrayListOf<ImageView>()
    var runnable : Runnable = Runnable{}
    var handler : Handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        kennysArray.add(binding.imageView1)
        kennysArray.add(binding.imageView2)
        kennysArray.add(binding.imageView3)
        kennysArray.add(binding.imageView4)
        kennysArray.add(binding.imageView5)
        kennysArray.add(binding.imageView6)
        kennysArray.add(binding.imageView7)
        kennysArray.add(binding.imageView8)
        kennysArray.add(binding.imageView9)


        downTimer()
        randomlyViewImage()
    }

    fun multiply(view: View){
        score += 1
        binding.scoreText.text = "Score: $score"
    }

    fun randomlyViewImage(){
        runnable = object : Runnable{
            override fun run() {
                val randomIndex = (0 until kennysArray.size).random()
                for(image in kennysArray){
                    image.visibility = View.INVISIBLE
                }
                kennysArray[randomIndex].visibility = View.VISIBLE
                handler.postDelayed(runnable,500)
            }
        }
        handler.post(runnable)
    }

    fun downTimer(){
        object : CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                binding.timeText.text = "Left: ${p0/1000}"
            }

            override fun onFinish() {
                handler.removeCallbacks(runnable)
                for (image in kennysArray){
                    image.visibility = View.INVISIBLE
                }
                val finishAlert = AlertDialog.Builder(this@GameActivity)
                finishAlert.setTitle("Oyun Bitti")
                finishAlert.setMessage("Tekrar Oynamak İster misiniz?")
                finishAlert.setPositiveButton("Evet", object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        val finishIntent = Intent(this@GameActivity, GameActivity::class.java)
                        finish()
                        startActivity(finishIntent)
                    }
                })
                finishAlert.setNegativeButton("Hayır", object  : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        val returnIntent = Intent(this@GameActivity, MainActivity::class.java)
                        finish()
                        startActivity(returnIntent)
                    }
                })
                finishAlert.show()
            }
        }.start()
    }
}