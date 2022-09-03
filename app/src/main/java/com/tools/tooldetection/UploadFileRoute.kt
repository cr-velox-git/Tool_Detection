//package com.tools.tooldetection
//
//fun Route.uploadFile(){
//    post("file"){
//        val multipart = call.reciveMultipart()
//        multipart.forEachPart{ part ->
//            when(part){
//                is PartData.FromItem -> kotlin.Unit
//                is PartData.FileItem ->{
//                    if (part.name=="image"){
//                        part.save("","")
//                    }
//                }
//                else -> Unit
//            }
//        }
//        call.respond(HttpStatusCode.OK)
//    }
//}