package com.oxymium.si2gassistant.repositories

import com.oxymium.si2gassistant.model.Issue
import com.oxymium.si2gassistant.model.State
import kotlinx.coroutines.flow.Flow

// ----------------
// IssuesRepository
// ----------------

interface IssuesRepository {

    // Query all issues
    suspend fun getAllIssues(): Flow<State<List<Issue>>>

}