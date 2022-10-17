package com.example.wps.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

     companion object {
         fun parseLongToFormattedDateString(timestamp: Long) : String {
             val date = Date(timestamp)

             val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
             return formatter.format(date)
         }
     }
}