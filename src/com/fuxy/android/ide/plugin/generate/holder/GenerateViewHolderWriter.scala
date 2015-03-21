package com.fuxy.android.ide.plugin.generate.holder

import com.fuxy.android.ide.plugin.generate.BaseGenerateCodeWriter
import com.fuxy.android.ide.plugin.utils.AndroidViewInfo
import com.intellij.codeInsight.actions.{ReformatCodeProcessor, OptimizeImportsProcessor}
import com.intellij.psi.codeStyle.JavaCodeStyleManager
import com.intellij.psi.{PsiModifier, JavaPsiFacade, PsiClass}
import scala.collection.mutable.ArrayBuffer

/**
 * Created by fuxiuyuan on 15-3-21.
 */
class GenerateViewHolderWriter (clazz:PsiClass,ids:ArrayBuffer[AndroidViewInfo],commandName:String)
  extends BaseGenerateCodeWriter(clazz,commandName){

  def this(clazz:PsiClass,ids:ArrayBuffer[AndroidViewInfo]) = this(clazz,ids,"Generate ViewHolder pattern")

  override def generate() {
    val classStr = new StringBuilder();
    ids.foreach((view) => {
      if (view.getName().contains(".")) {
        classStr.append(String.format("public final %s %s;\n", view.getName(), view.getFieldName()));
      } else {
        classStr.append(String.format("public final android.widget.%s %s;\n", view.getName(), view.getFieldName()));
      }
    });

    classStr.append("public final android.view.View root;\n");
    classStr.append("public ViewHolder(android.view.View root){\n");
    ids.foreach((view) => {
      classStr.append(String.format("%s = (%s) root.findViewById(%s);\n",
        view.getFieldName(),
        view.getName(),
        view.getId()));
    })

    classStr.append("this.root = root;\n");
    classStr.append("}\n}");

    //create
    val elementFactory = JavaPsiFacade.getElementFactory(prj);
    val viewHolderClass = elementFactory.createClassFromText(classStr.toString(), cls);
    viewHolderClass.setName("ViewHolder");
    val modifierList = viewHolderClass.getModifierList();
    //assert modifierList != null : "no modifiers";
    modifierList.setModifierProperty(PsiModifier.PUBLIC, true);

    //add and reformat
    val newClass = cls.add(viewHolderClass);
    JavaCodeStyleManager.getInstance(prj).shortenClassReferences(newClass);
    new OptimizeImportsProcessor(prj, cls.getContainingFile()).runWithoutProgress()
    new ReformatCodeProcessor(prj,cls.getContainingFile(),null,true).runWithoutProgress()

    //        //todo rename inplace
    //        RefactoringFactory.getInstance(prj)
    //                .createRename(newClass, null, true, false)
    //                .run();
  }

}
