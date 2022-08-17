package com.oxymium.si2gassistant.repositories.firebase.firestore

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.model.Issue
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.repositories.IssuesRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

// -------------------------
// FirestoreIssuesRepository
// -------------------------

class FirestoreIssuesImpl(val firebaseFirestore: FirebaseFirestore): IssuesRepository {

    override suspend fun createIssue(issue: Issue) = flow<State<DocumentReference>> {

        // UPLOAD
        emit(State.loading())

        firebaseFirestore
            .collection(ISSUES)
            .add(issue)
            .await()
            .run { emit(State.success(this)) }

    }.catch {
        // FAILURE
        emit(State.failed(it.message.toString()))
    }

    override suspend fun getAllIssues() = flow<State<List<Issue>>>{

        // LOADING
        emit(State.loading())

        firebaseFirestore
            .collection(ISSUES)
            .get()
            .await()
            .toObjects(Issue::class.java)
            .run { emit(State.success(this)) }

    }.catch {
        // FAILURE
        emit(State.failed(it.message.toString()))
    }

    override suspend fun getAllIssuesByAcademy(academyId: String) = flow<State<List<Issue>>> {

        // LOADING
        emit(State.loading())

        firebaseFirestore
            .collection(ISSUES)
            .whereEqualTo("academy", academyId)
            .get()
            .await()
            .toObjects(Issue::class.java)
            .run { emit(State.success(this))}

    }.catch {
        // FAILURE
        emit(State.failed(it.message.toString()))
    }

    override suspend fun updateIssue(documentId: String) = flow<State<Void>> {

        // UPDATING
        emit(State.loading())

        firebaseFirestore
            .collection(ISSUES)
            .document(documentId)
            .update("solved", true)
            .await()
            .run { emit(State.success(this)) }

    }.catch {

        // FAILURE
        emit(State.failed(it.message.toString()))
    }

}