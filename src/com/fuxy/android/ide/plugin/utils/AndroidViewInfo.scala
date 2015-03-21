/**
 * Created by fuxiuyuan on 15-3-20.
 */
package com.fuxy.android.ide.plugin.utils

class AndroidViewInfo(id:String,name:String) {

  var mId = initId()
  var mName = initName()

  def getId():String = {
    mId
  }

  def getName() : String = {
    mName
  }

  def initId():String = {
    if (id.startsWith("@+id/")) {
      return "R.id." + id.split("@\\+id/")(1)
    } else if (id.contains(":")) {
      val s = id.split(":id/")
      val packageStr = s(0).substring(1, s(0).length())
      return packageStr + ".R.id." + s(1)
    }
    return null;
  }

  def initName() = {
    if (name.contains(".")) {//fully qualified
      name
    } else if (name.equals("View") || name.equals("ViewGroup")) {//view
      String.format("android.view.%s", name)
    } else {
      String.format("android.widget.%s", name)//widget
    }
  }

  def getFieldName() : String = {
    val words : Array[String] = getId().split("_")
    val fieldName = new StringBuilder()

    words.foreach((word:String)=>  {
      val idTokens = word.split("\\.")
      val chars = idTokens(idTokens.length - 1)
      fieldName ++= chars
    });
    fieldName.toString()
  }
}