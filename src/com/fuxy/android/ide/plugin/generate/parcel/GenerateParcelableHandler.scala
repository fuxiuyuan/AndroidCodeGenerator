package com.fuxy.android.ide.plugin.generate.parcel

import com.fuxy.android.ide.plugin.utils.AndroidUtils
import com.intellij.codeInsight.generation.{GenerationInfo, PsiFieldMember, ClassMember, GenerateMembersHandlerBase}
import com.intellij.psi.util.TypeConversionUtil
import com.intellij.psi.{JavaPsiFacade, PsiType, PsiClass}
import com.intellij.util.IncorrectOperationException
import org.jetbrains.annotations.NotNull

import scala.collection.mutable.ArrayBuffer

/**
 * Created by xiuyuan.fuxy on 2015/3/25.
 */
class GenerateParcelableHandler(chooserTitle:String) extends GenerateMembersHandlerBase(chooserTitle) {
  def this() = this("Select fields to parcel")

  override def getAllOriginalMembers(aClass: PsiClass ):Array[ClassMember] = {
//    val prj = aClass.getProject()
//    val parcelType = JavaPsiFacade.getElementFactory(prj).createType(AndroidUtils.findParcelableClass(prj),null,null)
//    val ret = new ArrayBuffer[ClassMember]()
//    aClass.getFields.foreach((field) =>{
//      //filter primitive or Parcelable
//      //todo add support Lists etc - see Parcel src
//      if (TypeConversionUtil.isPrimitiveAndNotNullOrWrapper(field.getType())
//        || TypeConversionUtil.isAssignable(parcelType, field.getType())) {
//        ret += new PsiFieldMember(field)
//      }
//    })
//    ret.toArray
    null
  }
  @throws(classOf[IncorrectOperationException])
  override def generateMemberPrototypes(aClass: PsiClass , originalMember: ClassMember ):Array[GenerationInfo] = {
    //assert false : "I dont call it. Who called it?";
    return null
  }
}
