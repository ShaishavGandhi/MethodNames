package com.shaishavgandhi.methodnames.lint

import com.android.tools.lint.client.api.JavaEvaluator
import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression
import java.util.*

/**
 * Simple lint check which looks out for all `subscribe`/`subscribeWith` method calls and
 * raises an error if they're from Reactive types like Observable, Flowable etc.
 */
class SubscribeDetector: Detector(), SourceCodeScanner {

  companion object {
    val ISSUE: Issue = Issue.create(
        "SubscribeUsage",
        "Do not subscribe to anything!",
        "For the purposes of this example, you're not allowed to subscribe to any Observables. ",
        Category.CORRECTNESS,
        10,
        Severity.ERROR,
        Implementation(SubscribeDetector::class.java,
            EnumSet.of(Scope.JAVA_FILE, Scope.TEST_SOURCES),
            EnumSet.of(Scope.JAVA_FILE),
            EnumSet.of(Scope.TEST_SOURCES))
    )

    private const val OBSERVABLE = "io.reactivex.Observable"
    private const val FLOWABLE = "io.reactivex.Flowable"
    private const val PARALLEL_FLOWABLE = "io.reactivex.parallel.ParallelFlowable"
    private const val SINGLE = "io.reactivex.Single"
    private const val MAYBE = "io.reactivex.Maybe"
    private const val COMPLETABLE = "io.reactivex.Completable"

    private val REACTIVE_TYPES = setOf(OBSERVABLE, FLOWABLE, PARALLEL_FLOWABLE, SINGLE, MAYBE,
        COMPLETABLE)
  }

  override fun getApplicableMethodNames(): List<String> = listOf("subscribe", "subscribeWith")

  override fun visitMethod(context: JavaContext, node: UCallExpression, method: PsiMethod) {
    if (isReactiveType(context.evaluator, method)) {
      context.report(ISSUE, node, context.getLocation(node), "For the purposes of this example, you're not allowed to subscribe to any Reactive types.")
    }
  }

  private fun isReactiveType(evaluator: JavaEvaluator, method: PsiMethod): Boolean {
    return REACTIVE_TYPES.any { evaluator.isMemberInClass(method, it) }
  }
}