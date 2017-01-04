package com.matcha.security;

import sun.security.util.SecurityConstants;

import javax.security.auth.Subject;
import java.io.FilePermission;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;

/**
 * Created by Matcha on 2016/12/14.
 */
public class TestSecurity2
{
    public static void main(String[] args)
    {
        Subject matchaUser = new Subject();
        matchaUser.getPrincipals().add(new Principal()
        {
            @Override
            public String getName()
            {
                return "Test Principal";
            }
        });
        Subject.doAs(matchaUser, new PrivilegedAction<String>()
        {
            @Override
            public String run()
            {
                AccessControlContext accessControlContext = AccessController.getContext();
                AccessController.checkPermission(
                        new FilePermission(
                                "/",
                                SecurityConstants.FILE_READ_ACTION
                        )
                );
                return accessControlContext.toString();
            }
        });
    }
}
