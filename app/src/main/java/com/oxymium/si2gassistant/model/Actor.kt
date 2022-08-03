package com.oxymium.si2gassistant.model

// ----------------
// ACTOR DATA CLASS
// ----------------

data class Actor(
    val id: Int?,
    val name: String?,
    val firstName: String?,
    val role: String?,
    val academy: String?,
    val validatedModules: List<String>
)

// EMPTY DATACLASS CONSTRUCTOR, REQUIRED FOR FIRESTORE DESERIALIZATION
{ constructor() : this(null, null, null, null, null, listOf())}

