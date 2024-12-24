package com.utec.pft.complementos.adaptadores

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utec.pft.R
import com.utec.pft.objetos.Reclamo

class ReclamoAdapter(private var reclamos: List<Reclamo>, private val itemSpacing: Int) :
    RecyclerView.Adapter<ReclamoAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION
    private var itemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }

    fun getItemAtPosition(position: Int): Reclamo {
        return reclamos[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun actualizar(reclamos2: List<Reclamo>) {
        reclamos = reclamos2
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reclamo, parent, false)

        val layoutParams = view.layoutParams as RecyclerView.LayoutParams
        layoutParams.topMargin = itemSpacing
        view.layoutParams = layoutParams

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reclamo = reclamos[position]
        holder.bind(reclamo, position == selectedPosition)
    }

    override fun getItemCount(): Int {
        return reclamos.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tipoTextView: TextView = itemView.findViewById(R.id.tipoTextView)
        private val tituloTextView: TextView = itemView.findViewById(R.id.tituloTextView)
        private val fechaRegistroTextView: TextView =
            itemView.findViewById(R.id.fechaRegistroTextView)
        private val respuestaTextView: TextView = itemView.findViewById(R.id.respuestaTextview)
        private val estadoTextView: TextView = itemView.findViewById(R.id.estadoEstudianteTextView)

        fun bind(reclamo: Reclamo, isSelected: Boolean) {


            val tipoReclamo = SpannableString("Tipo: ${reclamo.tipo}")
            tipoReclamo.setSpan(StyleSpan(Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val tituloReclamo = SpannableString("Título: ${reclamo.titulo}")
            tituloReclamo.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                7,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            val fechaRegistro = SpannableString("Fecha de Registro: ${reclamo.fechaRegistro}")
            fechaRegistro.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                17,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            val respuesta: SpannableString

            if (reclamo.respuesta == "null" || reclamo.respuesta == "" || reclamo.respuesta.isNullOrEmpty()) {
                 respuesta = SpannableString("Respuesta de Reclamo: Aún no hay una respuesta")
                respuesta.setSpan(
                    StyleSpan(Typeface.BOLD),
                    0,
                    21,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } else {
                 respuesta = SpannableString("Respuesta de Reclamo: ${reclamo.respuesta}")
                respuesta.setSpan(
                    StyleSpan(Typeface.BOLD),
                    0,
                    21,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            val estado = SpannableString("Estado de Reclamo: ${reclamo.estado}")
            estado.setSpan(StyleSpan(Typeface.BOLD), 0, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)


            tipoTextView.text = tipoReclamo
            tituloTextView.text = tituloReclamo
            fechaRegistroTextView.text = fechaRegistro
            respuestaTextView.text = respuesta
            estadoTextView.text = estado

            itemView.isSelected = isSelected

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(selectedPosition)
                    selectedPosition = adapterPosition
                    notifyItemChanged(selectedPosition)

                    itemClickListener?.invoke(adapterPosition)
                }
            }
        }
    }
}