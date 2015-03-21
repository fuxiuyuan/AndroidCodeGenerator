/**
 * Created by fuxiuyuan on 15-3-20.
 */

package com.fuxy.android.ide.plugin.utils

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.{Sdk, ProjectJdkTable}
import com.intellij.psi._
import com.intellij.psi.search.EverythingGlobalScope
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlTag
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable


import scala.collection.mutable.ArrayBuffer


class AndroidUtils {

}

object AndroidUtils {

  @Nullable
  def findSdk(): Sdk = {
    val allJdks = ProjectJdkTable.getInstance().getAllJdks()
    for (s <- allJdks) {
      if (s.getSdkType().getName().toLowerCase().contains("android")) {
        return s
      }
    }
    return null
  }

  @Nullable
  def findClass(project: Project,clazz:String): PsiClass = {
    val facade = JavaPsiFacade.getInstance(project)
    val findClass = facade.findClass(clazz, new EverythingGlobalScope(project))
    findClass
  }

  @Nullable
  def findAdapterClass(project: Project): PsiClass = {
    val facade = JavaPsiFacade.getInstance(project)
    val adapterClass = facade.findClass("android.widget.Adapter", new EverythingGlobalScope(project))
    adapterClass
  }

  @Nullable
  def isClassSubclassOfAdapter(cls: PsiClass): Boolean = {
    val adapterClass = findAdapterClass(cls.getProject)
    return !(adapterClass == null || !cls.isInheritor(adapterClass, true))
  }

  @Nullable
  def isClassSubclassOfActivity(cls: PsiClass): Boolean = {
    val adapterClass = findClass(cls.getProject,"android.app.Activity")
    return !(adapterClass == null || !cls.isInheritor(adapterClass, true))
  }

  @Nullable
  def isClassSubclassOfFragment(cls: PsiClass): Boolean = {
    val adapterClass = findClass(cls.getProject,"android.app.Fragment")
    return !(adapterClass == null || !cls.isInheritor(adapterClass, true))
  }

  @Nullable
  def implementsParcelable(cls: PsiClass): Boolean = {
    val implementsListTypes = cls.getImplementsListTypes
    for (implementsListType <- implementsListTypes) {
      val resolved = implementsListType.resolve()
      if (resolved != null && "android.os.Parcelable".equals(resolved.getQualifiedName())) {
        true
      }
    }
    false
  }

  @Nullable
  def findParcelableClass(@NotNull p: Project): PsiClass = {
    return JavaPsiFacade.getInstance(p)
      .findClass("android.os.Parcelable", new EverythingGlobalScope(p))
  }

  @Nullable
  def getLayoutXMLFileFromCaret(@NotNull e: Editor, @NotNull f: PsiFile): PsiFile = {
    val offset = e.getCaretModel.getOffset
    val candidate1 = f.findElementAt(offset)
    val candidate2 = f.findElementAt(offset - 1)

    val ret = findXmlResource(candidate1)
    if (ret != null) {
      return ret
    }
    return findXmlResource(candidate2)
  }

  @Nullable
  def findXmlResource(elementAt: PsiElement): PsiFile = {
    if (elementAt == null) {
      return null
    }
    if (!(elementAt.isInstanceOf[PsiIdentifier])) {
      return null
    }
    val rLayout = elementAt.getParent.getFirstChild;
    if (rLayout == null) {
      return null
    }
    if (!"R.layout".equals(rLayout.getText)) {
      return null
    }
    val prj = elementAt.getProject()
    val name = String.format("%s.xml", elementAt.getText)
    val foundFiles = FilenameIndex
      .getFilesByName(prj, name, new EverythingGlobalScope(prj))
    if (foundFiles.length <= 0) {
      return null
    }
    return foundFiles(0)
  }

  @NotNull
  def getIDsFromXML(@NotNull f: PsiFile): ArrayBuffer[AndroidViewInfo] = {
    val ret = new ArrayBuffer[AndroidViewInfo]();
    f.accept(new XmlRecursiveElementVisitor() {
      override def visitElement(element: PsiElement) {
        super.visitElement(element);
        if (element.isInstanceOf[XmlTag]) {
          val t = element.asInstanceOf[XmlTag]
          val id = t.getAttribute("android:id", null)
          if (id == null) {
            return
          }
          val value = id.getValue()
          if (value == null) {
            return
          }
          ret += (new AndroidViewInfo(value, t.getName))
        }

      }
    })
    return ret
  }
}