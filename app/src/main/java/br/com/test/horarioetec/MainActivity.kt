package br.com.test.horarioetec

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {

    val DIAS = arrayOf(
            "SEGUNDA",
            "TERÇA",
            "QUARTA",
            "QUINTA",
            "SEXTA",
            "SÁBADO",
            "DOMINGO"
    )

    val HORARIO_ETEC = arrayOf(
            arrayOf("MAT", "FIS", "MAT", "LPL", "HIS", "HIS", "ING", "VAGA"),
            arrayOf("VAGA", "VAGA", "LPL", "MAT", "LPL", "LPL", "PDTCC", "PDTCC"),
            arrayOf("BIO", "GEO", "ED.FIS", "QUI", "GP", "GP", "GEO", "LE"),
            arrayOf("VAGA", "VAGA", "ED.FIS", "GP", "GPM", "GPM", "FIS", "FIL"),
            arrayOf("MAT", "ING", "BIO", "QUI", "LE", "GFE", "SOC", "GFE")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                updateScreenInfo()
                mainHandler.postDelayed(this, 1000)
            }
        })
    }

    fun updateScreenInfo() {
        var date = Date()
        val dateTime = SimpleDateFormat("HH:mm").format(date)

        // Atualizando texto do horário
        horario.text = dateTime

        var hora_atual = SimpleDateFormat("HH").format(date).toInt()
        var minuto_atual = SimpleDateFormat("mm").format(date).toInt()

        // Dia da semana em Int, com 0 sendo segunda-feira e 6 sendo domingo
        val diaDaSemana = (LocalDate.now().getDayOfWeek().value - 1)

//        DEBUG
//        hora_atual = 0
//        minuto_atual = 0
//        diaDaSemana = 0

        dia_da_semana.text = DIAS[diaDaSemana]

        // Se for final de semana ou a aula ainda não começou/ou acabou
        if (diaDaSemana >= 5 || hora_atual < 8 || (hora_atual >= 16 && minuto_atual > 40)) {
            materia_atual.text = "NENHUMA"
            proxima_materia.text = ""
            tempo_restante.text = ""

            return
        }

        var aulas_dia_atual = HORARIO_ETEC[diaDaSemana]

        // Aula atual == -1 é INTERVALO
        var aula_atual: Int = 0
        var intervalo: Boolean = false
        var aula_pre_intervalo: Boolean = false
        var horario_final_em_minutos: Int = 0

        if (hora_atual == 8) {
            if (minuto_atual < 50) {
                aula_atual = 0
                horario_final_em_minutos = (8 * 60 + 50)
            } else {
                aula_atual = 1
                aula_pre_intervalo = true
                horario_final_em_minutos = (9 * 60 + 40)
            }
        }

        else if (hora_atual == 9) {
            if (minuto_atual < 40) {
                aula_pre_intervalo = true
                horario_final_em_minutos = (9 * 60 + 40)
            } else
                intervalo = true

            aula_atual = 1
        }

        else if (hora_atual == 10) {
            if (minuto_atual < 50) {
                aula_atual = 2
                horario_final_em_minutos = (10 * 60 + 50)
            } else {
                aula_atual = 3
                aula_pre_intervalo = true
                horario_final_em_minutos = (11 * 60 + 40)
            }
        }

        else if (hora_atual == 11) {
            if (minuto_atual < 40) {
                aula_pre_intervalo = true
                horario_final_em_minutos = (11 * 60 + 40)
            } else {
                intervalo = true
            }

            aula_atual = 3
        }

        else if (hora_atual == 12) {
            aula_atual = 3
            intervalo = true
        }

        else if (hora_atual == 13) {
            if (minuto_atual < 50) {
                aula_atual = 4
                horario_final_em_minutos = (13 * 60 + 50)
            } else {
                aula_atual = 5
                aula_pre_intervalo = true
                horario_final_em_minutos = (14 * 60 + 40)
            }
        }

        else if (hora_atual == 14) {
            if (minuto_atual < 40) {
                aula_pre_intervalo = true
                horario_final_em_minutos = (14 * 60 + 40)
            } else {
                intervalo = true
            }

            aula_atual = 5
        }

        else if (hora_atual == 15) {
            if (minuto_atual < 50) {
                aula_atual = 6
                horario_final_em_minutos = (15 * 60 + 50)
            } else {
                aula_atual = 7
                horario_final_em_minutos = (16 * 60 + 40)
            }
        }

        else if (hora_atual == 16) {
            if (minuto_atual < 40) {
                aula_atual = 7
                horario_final_em_minutos = (16 * 60 + 40)
            }
        }

        materia_atual.text = aulas_dia_atual[aula_atual]
        tempo_restante.text = ""

        // Se não for a última aula
        if (aula_atual != 7) {
            if (intervalo) {
                materia_atual.text = "INTERVALO"
                proxima_materia.text = "PRÓXIMA: " + aulas_dia_atual[aula_atual + 1]
            } else if (aula_pre_intervalo) {
                materia_atual.text = aulas_dia_atual[aula_atual]
                proxima_materia.text = "PRÓXIMA: INTERVALO"
            } else {
                materia_atual.text = aulas_dia_atual[aula_atual]
                proxima_materia.text = "PRÓXIMA: " + aulas_dia_atual[aula_atual + 1]

                // Calculando tempo restante da aula
                var horario_atual_em_minutos = (hora_atual * 60 + minuto_atual)
                var diferenca_tempo_em_minutos = (horario_final_em_minutos - horario_atual_em_minutos)
                tempo_restante.text = "ACABA EM " + diferenca_tempo_em_minutos + "m"
            }
        } else
            proxima_materia.text = "PRÓXIMA: NENHUMA"
    }

}
