package com.oxymium.si2gassistant.model

import com.google.firebase.firestore.DocumentId

// ---------------
// USER DATA CLASS
// ---------------

data class User(
    @DocumentId
    var id: String?,
    var superUser: Boolean = false,
    val name: String?,
    val firstName: String?,
    val academy: String?)

// EMPTY DATACLASS CONSTRUCTOR, REQUIRED FOR FIRESTORE DESERIALIZATION
{ constructor() : this(null, false, null, null, null) }