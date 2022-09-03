package com.tools.tooldetection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.File

var predicted:MutableLiveData<Int> = MutableLiveData()

class FileViewModel(
    private val repository: FileRepository = FileRepository()
):ViewModel() {

    fun uploadImage(file: File){
        CoroutineScope(Dispatchers.IO).async{
            predicted.value =  repository.uploadImage(file)
        }
    }
}