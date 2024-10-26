package com.example.vpopoo.model

data class UserModel(
    var id: Long? = null,
    var username: String = "",
    var password: String = "",
    var isActive: Boolean = true,
    var roles: Set<RoleEnum>? = null
)
