package com.fuxy.android.ide.plugin.generate;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

/**
 * Created by fuxiuyuan on 15-3-20.
 */
public class BaseSimple extends WriteCommandAction.Simple {
    protected BaseSimple(Project project, PsiFile... files) {
        super(project, files);
    }

    protected BaseSimple(Project project, String commandName) {
        super(project, commandName);
    }

    protected BaseSimple(Project project, String name, String groupID, PsiFile... files) {
        super(project, name, groupID, files);
    }

    @Override
    protected void run() throws Throwable {

    }
}
