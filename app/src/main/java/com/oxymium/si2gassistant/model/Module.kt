package com.oxymium.si2gassistant.model

import com.google.firebase.firestore.DocumentId

// -----------------
// MODULE DATA CLASS
// -----------------

data class Module(
    @DocumentId
    var id: String?,
    var content: String?,
    var validated: Boolean?
    )

// EMPTY DATACLASS CONSTRUCTOR, REQUIRED FOR FIRESTORE DESERIALIZATION
{ constructor() : this(null, null,false) }