package com.oxymium.si2gassistant.features.issues

import com.oxymium.si2gassistant.model.Issue

// -------------
// IssueListener
// -------------

class IssueListener(val onClickIssueListener: (issue: Issue) -> Unit) {

    fun onClickIssue(issue: Issue) = onClickIssueListener(issue)

}