package com.fuxy.android.ide.plugin.generate.res

import com.fuxy.android.ide.plugin.generate.BaseGenerateCodeWriter
import com.fuxy.android.ide.plugin.utils.{AndroidUtils, AndroidStringInfo, AndroidViewInfo}
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.impl.source.tree.TreeElement
import com.intellij.psi.impl.source.xml.{XmlFileImpl, XmlTagImpl}
import com.intellij.psi.util.PsiUtilBase
import com.intellij.psi.xml.{XmlTag, XmlFile}
import com.intellij.psi.{PsiElement, PsiFile, PsiClass}
import org.jetbrains.annotations.NotNull

import scala.collection.mutable.ArrayBuffer

/**
 * Created by xiuyuan.fuxy on 2015/3/24.
 */
class CreateStringWriter (val file:PsiFile,@NotNull project: Project,@NotNull editor: Editor,val strInfo : AndroidStringInfo,clazz:PsiClass,commandName:String)
  extends BaseGenerateCodeWriter(clazz,commandName){
  override def generate():Unit = {
    if(!file.isInstanceOf[XmlFileImpl])
      return;
    val xmlFile = file.asInstanceOf[XmlFileImpl]
    if(xmlFile.getDocument.getRootTag == null) {
      println("xmlFile.getDocument.getRootTag == null")
      return
    }

    val xmlTag = xmlFile.getDocument.getRootTag.createChildTag("string",null,strInfo.value,true)
    xmlTag.setAttribute("name",strInfo.name)
    xmlFile.getDocument.getRootTag.addSubTag(xmlTag,true)

//    val cursorPosition = editor.getCaretModel.getOffset
//    val f = PsiUtilBase.getPsiFileInEditor(editor, project)
//    val psiClass = AndroidUtils.getTargetClass(editor, f)

  }
}
