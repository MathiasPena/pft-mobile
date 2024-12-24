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
import com.utec.pft.objetos.ReclamoAnalista

class ReclamoAnalistaAdapter(private var reclamos: List<ReclamoAnalista>, private val itemSpacing: Int) :
    RecyclerView.Adapter<ReclamoAnalistaAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION
    private var itemClickListener: ((Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun actualizar(reclamos2: List<ReclamoAnalista>) {
        reclamos = reclamos2
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reclamo_analista, parent, false)

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
        private val fechaRegistroTextView: TextView = itemView.findViewById(R.id.fechaRegistroTextView)
        private val nombrePersonaTextView: TextView = itemView.findViewById(R.id.nombrePersonaTextView)
        private val rolPersonaTextView: TextView = itemView.findViewById(R.id.rolPersonaTextView)
        private val estadoTextView: TextView = itemView.findViewById(R.id.estadoTextView)

        fun bind(reclamo: ReclamoAnalista, isSelected: Boolean) {

            val tipoReclamo = SpannableString("Tipo: ${reclamo.tipo}")
            tipoReclamo.setSpan(StyleSpan(Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val tituloReclamo = SpannableString("Título: ${reclamo.titulo}")
            tituloReclamo.setSpan(StyleSpan(Typeface.BOLD), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val fechaRegistro = SpannableString("Fecha de Registro: ${reclamo.fechaRegistro}")
            fechaRegistro.setSpan(StyleSpan(Typeface.BOLD), 0, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val nombrePersona = SpannableString("Nombre de Persona: ${reclamo.nombrePersona}")
            nombrePersona.setSpan(StyleSpan(Typeface.BOLD), 0, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val rolPersona = SpannableString("Rol de Persona: ${reclamo.rolPersona}")
            rolPersona.setSpan(StyleSpan(Typeface.BOLD), 0, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val estado = SpannableString("Estado de Reclamo: ${reclamo.estado}")
            estado.setSpan(StyleSpan(Typeface.BOLD), 0, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

             //asigno las SpannableStrings a los TextViews
            tipoTextView.text = tipoReclamo
            tituloTextView.text = tituloReclamo
            fechaRegistroTextView.text = fechaRegistro
            nombrePersonaTextView.text = nombrePersona
            rolPersonaTextView.text = rolPersona
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