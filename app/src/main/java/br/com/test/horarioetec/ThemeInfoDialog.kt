package br.com.test.horarioetec

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

class ThemeInfoDialog : AppCompatDialogFragment() {

    private var ctx: Context? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Troca de tema")
                .setMessage("Arraste horizontalmente na tela para trocar o tema!")
                .setNeutralButton("Não mostrar novamente") { _, _ ->
                    if (ctx != null) {
                        var MY_PREF = MyPreference(ctx!!)
                        MY_PREF.setMessageDisplay(false)
                    }
                }
                .setPositiveButton("Ok") { _, _ -> }
        return builder.create()
    }

    override fun onAttach(context: Context) {
        this.ctx = context
        super.onAttach(context)
    }

}