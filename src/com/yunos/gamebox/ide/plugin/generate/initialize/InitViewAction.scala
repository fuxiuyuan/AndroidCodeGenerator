package com.yunos.gamebox.ide.plugin.generate.initialize

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.{JavaPsiFacade, PsiClass, PsiFile}
import com.intellij.psi.util.PsiUtilBase
import com.yunos.gamebox.ide.plugin.generate.BaseAndroidGenerateCodeAction
import com.yunos.gamebox.ide.plugin.utils.{AndroidViewInfo, AndroidUtils}
import org.jetbrains.annotations.NotNull

/**
 * Created by fuxiuyuan on 15-3-20.
 */
class InitViewAction extends BaseAndroidGenerateCodeAction(null) {

  override protected def isValidForFile(@NotNull project: Project, @NotNull editor: Editor , @NotNull file: PsiFile ):Boolean = {
    //JavaPsiFacade.getInstance(project).

    return super.isValidForFile(project, editor, file) && null != AndroidUtils.getLayoutXMLFileFromCaret(editor, file);
  }

  override def actionPerformedImpl(@NotNull project: Project ,@NotNull editor: Editor) {
    val f = PsiUtilBase.getPsiFileInEditor(editor, project)
    val layout = AndroidUtils.getLayoutXMLFileFromCaret(editor, f)
    if (layout == null) return;
    val ids = AndroidUtils.getIDsFromXML(layout)
    //todo picker
    if (ids.size() > 0) {
      new InitViewWriter(getTargetClass(editor, f), ids,"Create initViews method")
        .execute()
    } else {
      //todo error ballon - no ivews
      //System.out.println("error:no id views")
    }
  }
}
