package com.fuxy.android.ide.plugin.generate

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.command.WriteCommandAction.{Simple}
import com.intellij.openapi.project.Project
import com.intellij.psi.{PsiFile, PsiClass}
/**
 * Created by fuxiuyuan on 15-3-20.
 */

//trait Extended extends WriteCommandAction.Simple {
//
//  @throws(classOf[Throwable]) override def run()  = {
//    generate();
//  }
//
//  def generate():Unit = {}
//}
//
//object Extended {
//  //def apply(clazz:PsiClass,commandName:String,groupID:String,files:PsiFile*) = new WriteCommandAction.Simple(clazz.getProject,commandName,groupID,files) with Extended
//  def apply(clazz:PsiClass,commandName:String) = new WriteCommandAction.Simple(clazz.getProject,commandName) with Extended
//}

abstract class BaseGenerateCodeWriter[T](clazz:PsiClass,commandName:String) extends WriteCommandAction.Simple[T](clazz.getProject,commandName){

  protected val cls  = clazz
  protected val prj = clazz.getProject

  //def this(clazz:PsiClass,commandName:String,files:PsiFile*) = this(clazz.getProject,commandName,null,files)

  @throws(classOf[Throwable]) override def run()  = {
    generate();
  }

  def generate():Unit = {}
}
