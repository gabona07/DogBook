package com.example.dogbook.model

import java.lang.Exception

data class AuthUser(val userId: String?, val email: String?, val authException: Exception?, val userToken: String?)