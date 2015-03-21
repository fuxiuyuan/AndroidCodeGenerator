package com.fuxy.android.ide.plugin.generate.holder

import com.fuxy.android.ide.plugin.generate.BaseAndroidGenerateCodeAction
import com.fuxy.android.ide.plugin.utils.AndroidUtils
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.util.PsiUtilBase
import com.intellij.psi.{PsiClass, PsiFile}
import org.jetbrains.annotations.NotNull
/**
 * Created by fuxiuyuan on 15-3-21.
 */
class GenerateHolderAction  extends BaseAndroidGenerateCodeAction(null) {

  override def isValidForFile(@NotNull project:Project, @NotNull editor:Editor, @NotNull file: PsiFile):Boolean = {
    return super.isValidForFile(project, editor, file) && null != AndroidUtils.getLayoutXMLFileFromCaret(editor, file)
  }

  override def isValidForClass(@NotNull targetClass: PsiClass ):Boolean = {
    return super.isValidForClass(targetClass) &&
      AndroidUtils.isClassSubclassOfAdapter(targetClass);
  }

  override def actionPerformed(e: AnActionEvent) {
    super.actionPerformed(e);
  }

  override def actionPerformedImpl(@NotNull project: Project, editor:Editor) {
    val f = PsiUtilBase.getPsiFileInEditor(editor, project);
    if (f == null) return;
    val layout = AndroidUtils.getLayoutXMLFileFromCaret(editor, f);
    if (layout == null) return;

    val ids = AndroidUtils.getIDsFromXML(layout);
    val cls = getTargetClass(editor, f);
    if (ids.length > 0) {
      new GenerateViewHolderWriter(cls, ids)
        .execute();
    } else {
      //todo error ballon - no ivews
    }
  }
}
