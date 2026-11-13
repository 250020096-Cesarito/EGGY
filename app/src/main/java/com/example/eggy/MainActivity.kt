package com.example.eggy

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    // Componentes de la interfaz
    private lateinit var tvCoins: TextView
    private lateinit var progressBarEnergy: ProgressBar
    private lateinit var tvProgresoNumerico: TextView
    private lateinit var tvObjetivosCount: TextView
    private lateinit var btnAddGoal: FloatingActionButton
    private lateinit var containerGoals: LinearLayout

    // Variables lógicas de la aplicación
    private var currentCoins = 0
    private var currentEnergy = 0
    private var completedGoals = 0
    private var totalGoals = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vincular vistas del XML
        tvCoins = findViewById(R.id.tvCoins)
        progressBarEnergy = findViewById(R.id.progressBarEnergy)
        tvProgresoNumerico = findViewById(R.id.tvProgresoNumerico)
        tvObjetivosCount = findViewById(R.id.tvObjetivosCount)
        btnAddGoal = findViewById(R.id.btnAddGoal)
        containerGoals = findViewById(R.id.containerGoals)

        // Inicializar interfaz en cero
        actualizarUI()

        // El botón verde "+" abre exclusivamente el diálogo para CREAR un objetivo
        btnAddGoal.setOnClickListener {
            mostrarDialogoCrearObjetivo()
        }
    }

    // Actualiza los textos y la barra en pantalla
    private fun actualizarUI() {
        tvCoins.text = "🪙 $currentCoins"
        progressBarEnergy.progress = currentEnergy
        tvProgresoNumerico.text = "$currentEnergy/100"
        tvObjetivosCount.text = "$completedGoals/$totalGoals"
    }

    // Muestra la ventana emergente para registrar el texto del nuevo objetivo
    private fun mostrarDialogoCrearObjetivo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Nuevo Objetivo")

        val input = EditText(this)
        input.hint = "Ej. Estudiar Kotlin, Hacer ejercicio..."
        builder.setView(input)

        builder.setPositiveButton("Guardar") { _, _ ->
            val textoObjetivo = input.text.toString()
            if (textoObjetivo.isNotBlank()) {
                agregarObjetivoEnPantalla(textoObjetivo)
            }
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    // Pinta el objetivo creado en la pantalla principal con sus recompensas predeterminadas
    private fun agregarObjetivoEnPantalla(nombre: String) {
        totalGoals++
        actualizarUI()

        // Tarjeta visual del objetivo
        val cardLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(24, 20, 24, 20)
            setBackgroundColor(android.graphics.Color.parseColor("#FFF3B0"))
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 16)
            layoutParams = params
        }

        // Nombre del objetivo escrito por ti
        val tvName = TextView(this).apply {
            text = nombre
            textSize = 14f
            setTextColor(android.graphics.Color.parseColor("#D4A373"))
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        // Indicador de recompensas (2+ energía y 5+ monedas)
        val tvRewardInfo = TextView(this).apply {
            text = "2+ ⚡  5+ 🪙"
            textSize = 12f
            setTextColor(android.graphics.Color.parseColor("#D4A373"))
            setPadding(16, 0, 16, 0)
        }

        // Botón o casilla independiente para marcarlo como cumplido
        val btnCheck = TextView(this).apply {
            text = "⭕"
            textSize = 18f
            setOnClickListener {
                completedGoals++
                currentCoins += 5
                currentEnergy += 2
                if (currentEnergy > 100) currentEnergy = 100

                actualizarUI()

                text = "✅"
                setOnClickListener(null)
            }
        }

        cardLayout.addView(tvName)
        cardLayout.addView(tvRewardInfo)
        cardLayout.addView(btnCheck)

        containerGoals.addView(cardLayout)
    }
}