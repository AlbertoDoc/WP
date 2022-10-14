package com.example.wps.util

import android.text.TextUtils

class ValidationUtil {

    companion object {
        const val NAME_FIELD_IS_EMPTY = "name_field_is_empty"
        const val EMAIL_FIELD_IS_EMPTY = "email_field_is_empty"
        const val EMAIL_FIELD_IS_INVALID = "email_field_is_invalid"
        const val FIELDS_IS_VALID = "fields_is_valid"

        fun isEmailValid(email: String) : Boolean {
            if (TextUtils.isEmpty(email)) {
                return false
            }

            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}