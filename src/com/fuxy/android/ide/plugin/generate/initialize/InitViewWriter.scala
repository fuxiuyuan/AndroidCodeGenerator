package com.fuxy.android.ide.plugin.generate.initialize

import com.fuxy.android.ide.plugin.generate.BaseGenerateCodeWriter
import com.fuxy.android.ide.plugin.utils.AndroidViewInfo
import com.intellij.codeInsight.actions.{ReformatCodeProcessor, OptimizeImportsProcessor}
import com.intellij.psi.codeStyle.JavaCodeStyleManager
import com.intellij.psi.{JavaPsiFacade, PsiClass}
import java.util.ArrayList
import java.util.List
/**
 * Created by fuxiuyuan on 15-3-20.
 */
class InitViewWriter(isActivityOrFragment:Boolean,clazz:PsiClass,ids:List[AndroidViewInfo],commandName:String)
  extends BaseGenerateCodeWriter(clazz,commandName){

  val elementFactory = JavaPsiFacade.getElementFactory(prj)

  def this(isActivityOrFragment:Boolean,clazz:PsiClass,ids:List[AndroidViewInfo]) = this(isActivityOrFragment,clazz,ids,"init views")

  override def generate():Unit = {
    if(isActivityOrFragment) {
      createActivityFindViewMethod
    } else {
      createFragmentFindViewMethod
    }
  }

  def createActivityFindViewMethod(): Unit = {
    //        PsiClass butterKnifeInjectAnnotation = JavaPsiFacade.getInstance(prj)
    //                .findClass("butterknife.InjectView", new EverythingGlobalScope(prj));
    //        if (butterKnifeInjectAnnotation == null) return;
    val methodSB = new StringBuilder()
    methodSB.append("private void initView() {")
    val it = ids.iterator()
    while(it.hasNext()) {
      //for (v:AndroidViewInfo : ids) {
      //String sb = "@butterknife.InjectView(" + v.getId() + ")" + " " + v.getName() + " " + v.getFieldName() + ";";
      val v = it.next();
      def sb = v.getName + " " + v.getFieldName() + ";"
      cls.add(elementFactory.createFieldFromText(sb, cls))

      methodSB.append(v.getFieldName()).append(" ").append("= ")
        .append("(").append(v.getName).append(") ")
        .append("findViewById(").append(v.getId()).append(");")
    }
    methodSB.append("}")
    //System.out.println(methodSB.toString())
    cls.add(elementFactory.createMethodFromText(methodSB.toString(),cls))
    JavaCodeStyleManager.getInstance(prj).shortenClassReferences(cls)
    //        new ReformatAndOptimizeImportsProcessor(prj, cls.getContainingFile(), false)
    //                .runWithoutProgress();
    new OptimizeImportsProcessor(prj, cls.getContainingFile()).runWithoutProgress()
    new ReformatCodeProcessor(prj,cls.getContainingFile(),null,true).runWithoutProgress()
  }

  def createFragmentFindViewMethod(): Unit = {
    //        PsiClass butterKnifeInjectAnnotation = JavaPsiFacade.getInstance(prj)
    //                .findClass("butterknife.InjectView", new EverythingGlobalScope(prj));
    //        if (butterKnifeInjectAnnotation == null) return;
    val methodSB = new StringBuilder()
    methodSB.append("private void initView(View root) {")
    val it = ids.iterator()
    while(it.hasNext()) {
      //for (v:AndroidViewInfo : ids) {
      //String sb = "@butterknife.InjectView(" + v.getId() + ")" + " " + v.getName() + " " + v.getFieldName() + ";";
      val v = it.next();
      def sb = v.getName + " " + v.getFieldName() + ";"
      cls.add(elementFactory.createFieldFromText(sb, cls))

      methodSB.append(v.getFieldName()).append(" ").append("= ")
        .append("(").append(v.getName).append(") ")
        .append("root.findViewById(").append(v.getId()).append(");")
    }
    methodSB.append("}")
    //System.out.println(methodSB.toString())
    cls.add(elementFactory.createMethodFromText(methodSB.toString(),cls))
    JavaCodeStyleManager.getInstance(prj).shortenClassReferences(cls)
    //        new ReformatAndOptimizeImportsProcessor(prj, cls.getContainingFile(), false)
    //                .runWithoutProgress();
    new OptimizeImportsProcessor(prj, cls.getContainingFile()).runWithoutProgress()
    new ReformatCodeProcessor(prj,cls.getContainingFile(),null,true).runWithoutProgress()
  }
}
