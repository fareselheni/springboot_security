package com.security.demo.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.security.demo.security.ApplicationUserPermission.*;
import static com.security.demo.security.ApplicationUserPermission.STUDENT_WRITE;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(STUDENT_WRITE, STUDENT_READ, COURSE_WRITE, COURSE_READ));

    private final Set<ApplicationUserPermission> permissions;


    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
