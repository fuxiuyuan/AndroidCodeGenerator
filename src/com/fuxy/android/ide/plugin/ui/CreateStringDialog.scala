package com.fuxy.android.ide.plugin.ui

import java.awt.{Dimension, BorderLayout, Color, GridLayout}
import javax.swing.{JTextField, JLabel, JPanel, JComponent}
import com.fuxy.android.ide.plugin.utils.AndroidStringInfo
import com.intellij.notification.{Notifications, NotificationType, Notification}
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import org.jetbrains.annotations.Nullable
import org.jetbrains.annotations.NotNull

/**
 * Created by xiuyuan.fuxy on 2015/3/24.
 */
class CreateStringDialog(callback:(AndroidStringInfo) => Unit,@Nullable project: Project,@NotNull canBeParent: Boolean, @NotNull applicationModalIfPossible:Boolean)
  extends DialogWrapper(project,canBeParent,applicationModalIfPossible) {
  val mPanel:JPanel = new JPanel()
  val mKeyLabel = new JLabel("key:");
  val mValueLabel = new JLabel("value:")
  val mKeyTextField = new JTextField();
  val mValueTextField = new JTextField();
  val mPanel1 = new JPanel()
  val mPanel2 = new JPanel()

  {


    mPanel1.setLayout(new BorderLayout(15,2))
    mPanel1.add(mKeyLabel,BorderLayout.WEST)
    //mPanel1.setBackground(new Color(255,255,0))
    mPanel1.add(mKeyTextField,BorderLayout.CENTER)

    mPanel2.setLayout(new BorderLayout(5,2))
    mPanel2.add(mValueLabel,BorderLayout.WEST)
    mPanel2.add(mValueTextField,BorderLayout.CENTER)
    //mPanel2.setBackground(new Color(0,255,0))

    mPanel.setPreferredSize(new Dimension(300,50))
    mPanel.setLayout(new GridLayout(2,1,5,5))
    mPanel.setBounds(1,1,500,300)
    mPanel.add(mPanel1)
    mPanel.add(mPanel2)
    //mPanel.setBackground(new Color(255,0,0))
    init()
    setTitle("创建字符串资源")
    this.getOKAction().setEnabled(true);
  }

  def this(callback:(AndroidStringInfo) => Unit,@Nullable project: Project) = this(callback,project,true,true)

  override def createCenterPanel(): JComponent = mPanel

  override def doOKAction(): Unit = {
    callback(new AndroidStringInfo(mValueTextField.getText,mKeyTextField.getText))
    super.doOKAction()
  }
}
