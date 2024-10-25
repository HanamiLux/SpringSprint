package api.vpopooapi.model

import jakarta.persistence.*

@Entity
data class UserModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var username: String = "",
    var password: String = "",
    var isActive: Boolean = true,

    @ElementCollection(targetClass = RoleEnum::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    var roles: Set<RoleEnum>? = null
)