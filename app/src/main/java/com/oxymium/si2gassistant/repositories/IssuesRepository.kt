package com.oxymium.si2gassistant.repositories

import com.google.firebase.firestore.DocumentReference
import com.oxymium.si2gassistant.model.Issue
import com.oxymium.si2gassistant.model.State
import kotlinx.coroutines.flow.Flow

// ----------------
// IssuesRepository
// ----------------

interface IssuesRepository {

    // Create new Issue
    suspend fun createIssue(issue: Issue): Flow<State<DocumentReference>>

    // Query all issues
    suspend fun getAllIssues(): Flow<State<List<Issue>>>

    // Get Issue
    suspend fun getIssueById(issueId: String): Flow<State<Issue?>>

    // Query all issues from an Academy
    suspend fun getAllIssuesByAcademy(academyId: String): Flow<State<List<Issue>>>

    // Update
    suspend fun updateIssue(documentId: String): Flow<State<Void>>
}