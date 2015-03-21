package com.fuxy.android.ide.plugin.generate

/**
 * Created by fuxiuyuan on 15-3-20.
 */

import com.fuxy.android.ide.plugin.utils.AndroidUtils
import com.intellij.codeInsight.CodeInsightActionHandler
import com.intellij.codeInsight.generation.actions.BaseGenerateAction
import com.intellij.psi.PsiAnonymousClass
import com.intellij.psi.PsiClass

import org.jetbrains.annotations.NotNull;

abstract class BaseAndroidGenerateCodeAction(handler:CodeInsightActionHandler)
  extends BaseGenerateAction(handler) {
  override def isValidForClass(@NotNull targetClass: PsiClass): Boolean = {
    return super.isValidForClass(targetClass) &&
      AndroidUtils.findSdk() != null &&
      !(targetClass.isInstanceOf[PsiAnonymousClass])
  }


}