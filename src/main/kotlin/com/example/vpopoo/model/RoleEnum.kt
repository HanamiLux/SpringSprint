package com.example.vpopoo.model

import org.springframework.security.core.GrantedAuthority

enum class RoleEnum : GrantedAuthority {
    USER, ADMIN, MANAGER;

    override fun getAuthority(): String {
        return name
    }}
