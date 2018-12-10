package com.shaishavgandhi.methodnames.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.google.auto.service.AutoService

@AutoService(IssueRegistry::class)
class MethodNameIssueRegistry: IssueRegistry() {
  override val issues: List<Issue> = listOf(SubscribeDetector.ISSUE)
  override val api: Int = CURRENT_API
  override val minApi: Int = 2
}