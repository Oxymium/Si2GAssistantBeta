package com.oxymium.si2gassistant.model

import com.google.firebase.firestore.DocumentId

// ----------------
// ACTOR DATA CLASS
// ----------------

data class Actor(
    @DocumentId
    val id: String?,
    val name: String?,
    val firstName: String?,
    val role: String?,
    val academy: String?,
    val validatedModules: List<String>
)

// EMPTY DATACLASS CONSTRUCTOR, REQUIRED FOR FIRESTORE DESERIALIZATION
{ constructor() : this(null, null, null, null, null, listOf())}

