package com.example.aluraviagens.util

import android.content.Context
import android.graphics.drawable.Drawable
import com.example.aluraviagens.model.Pacote

fun drawable(pacote: Pacote, context: Context): Drawable? {
    val identifierDrawable =
        context.resources.getIdentifier(pacote.imagem, "drawable", context.packageName)
    val drawableImagem = context.resources.getDrawable(identifierDrawable)
    return drawableImagem
}