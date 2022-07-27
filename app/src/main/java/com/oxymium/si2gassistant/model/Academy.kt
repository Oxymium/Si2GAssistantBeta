package com.oxymium.si2gassistant.model

import com.google.firebase.firestore.DocumentId

// ------------------
// ACADEMY DATA CLASS
// ------------------

data class Academy(
    @DocumentId
    val id: String?,
    val fullTitle: String?,
    val location: String?
)

// EMPTY DATACLASS CONSTRUCTOR, REQUIRED FOR FIRESTORE DESERIALIZATION
{ constructor() : this(null, null, null)}