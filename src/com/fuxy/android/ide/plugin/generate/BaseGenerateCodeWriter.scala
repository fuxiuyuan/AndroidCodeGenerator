package com.fuxy.android.ide.plugin.generate

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.command.WriteCommandAction.{Simple}
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
/**
 * Created by fuxiuyuan on 15-3-20.
 */
abstract class BaseGenerateCodeWriter(clazz:PsiClass,commandName:String)
  extends BaseSimple(clazz.getProject(),commandName) {

  protected val cls  = clazz
  protected val prj = clazz.getProject

  @throws(classOf[Throwable]) override def run()  = {
    generate();
  }

  def generate():Unit = {}
}
