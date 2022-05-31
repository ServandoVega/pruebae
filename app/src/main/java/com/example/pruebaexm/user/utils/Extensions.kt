package com.example.pruebaexm.user.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.google.android.material.textfield.TextInputLayout
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

fun TextInputLayout.isEmail(): Boolean {
    error = null
    if(editText!!.text.isNullOrBlank()){
        error = "Correo es obligatorio"
        return false
    }else if (!(editText!!.text.toString()
            .matches(Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}")))
    ) {
        error = "Correo no válido"
        return false
    }
    return true
}

fun TextInputLayout.isPhone(): Boolean {
    error = null
    if(editText!!.text.isNullOrBlank()){
        error = "El campo es obligatorio"
        return false
    }else if (!(editText!!.text.toString()
            .matches(Regex("[0-9]{10}")))
    ) {
        error = "El número de telefono es de 10 dígitos"
        return false
    }
    return true
}

fun TextInputLayout.required(): Boolean {
    error = null
    if (editText!!.text.isNullOrBlank()) {
        error = "El campo es obligatorio"
        return false
    }
    return true

}

//Formar un Uri de forma temporal
fun getFileUri(context: Context): Uri {
    val tmpFile = File.createTempFile(
        "image_file",
        ".jpg",
        context.externalCacheDir).apply {
        createNewFile()
        deleteOnExit()
    }
    return FileProvider.getUriForFile(
        context,
        context.packageName+".provider",
        tmpFile)
}