package com.oxymium.si2gassistant.model

import com.google.firebase.firestore.DocumentId

// ----------------
// ISSUE DATA CLASS
// ----------------

data class Issue(
    @DocumentId
    val id: String?,
    val academy: String?,
    val academyLocation: String?,
    val date: Long?,
    val category: String?,
    val gravity: Int?,
    val description: String?,
    var solved: Boolean? = false)

// EMPTY DATACLASS CONSTRUCTOR, REQUIRED FOR FIRESTORE DESERIALIZATION
{ constructor() : this(null, null, null, null, null, null, null, null) }