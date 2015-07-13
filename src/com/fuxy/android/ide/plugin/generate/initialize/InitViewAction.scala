package com.fuxy.android.ide.plugin.generate.initialize

import com.fuxy.android.ide.plugin.generate.BaseAndroidGenerateCodeAction
import com.fuxy.android.ide.plugin.utils.AndroidUtils
import com.intellij.notification.{Notifications, NotificationType, Notification}
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.psi.{JavaPsiFacade, PsiClass, PsiFile}
import com.intellij.psi.util.PsiUtilBase
import org.jetbrains.annotations.NotNull

/**
 * Created by fuxiuyuan on 15-3-20.
 */
class InitViewAction extends BaseAndroidGenerateCodeAction(null) {

  override protected def isValidForFile(@NotNull project: Project, @NotNull editor: Editor , @NotNull file: PsiFile ):Boolean = {
    return super.isValidForFile(project, editor, file) &&
      null != AndroidUtils.getLayoutXMLFileFromCaret(editor, file)
  }

  override def isValidForClass(@NotNull targetClass: PsiClass ):Boolean = {
    return super.isValidForClass(targetClass) &&
      (AndroidUtils.isClassSubclassOfActivity(targetClass) || AndroidUtils.isClassSubclassOfFragment(targetClass))
  }

  override def actionPerformedImpl(@NotNull project: Project ,@NotNull editor: Editor) {
    val f = PsiUtilBase.getPsiFileInEditor(editor, project)
    val layout = AndroidUtils.getLayoutXMLFileFromCaret(editor, f)
    if (layout == null) return;
    val ids = AndroidUtils.getIDsFromXML(layout)
    val currClass = getTargetClass(editor, f)
    val isActivityOrFragment = AndroidUtils.isClassSubclassOfActivity(currClass)

    //todo picker
    if (ids.length > 0) {
      new InitViewWriter(isActivityOrFragment,getTargetClass(editor, f), ids,"Create initViews method")
        .execute()
    } else {
      //todo error ballon - no ivews
      //System.out.println("error:no id views")
      //Messages.showMessageDialog(project,"no id views","error",null);
      //Messages.showWarningDialog(project,"该Layout文件里的View都没有id！","Warning");
      val notification = new Notification("com.fuxy.android.ide.plugin","警告","该Layout文件里的View都没有id！",NotificationType.WARNING)
      Notifications.Bus.notify(notification)
    }
  }
}
