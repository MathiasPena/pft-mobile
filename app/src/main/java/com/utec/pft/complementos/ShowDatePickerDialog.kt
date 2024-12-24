package com.utec.pft.complementos

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DatePickerHelper(private val context: Context) {

    fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(context, { _, year, month, day ->
            // Manejar la fecha seleccionada
            val selectedDate = "$day/${month + 1}/$year" // +1 porque el índice del mes comienza en 0


            val dateTimeHelper = DateTime(context)
            val formattedDateTime = dateTimeHelper.getCurrentDateTimeFormatted("dd/MM/yyyy")

            // Comparar las fechas
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateSelected = sdf.parse(selectedDate)
            val dateCurrent = sdf.parse(formattedDateTime)


            println(formattedDateTime)
            println(selectedDate)

            if (dateSelected != null) {
                if (dateSelected >= dateCurrent) {
                    Toast.makeText(context, "Ingrese una fecha anterior a la actual", Toast.LENGTH_SHORT).show()
                    editText.text.clear()
                } else {
                    editText.setText(selectedDate)
                }
            }

        }, year, month, day)

        datePickerDialog.show()
    }
}