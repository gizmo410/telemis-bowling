package com.telemis.bowling.rest.support;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static org.springframework.util.StringUtils.hasText;

/**
 * Utility class that offers functionalities on top of the security implementation.
 *
 * @since 17/06/14
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Extract the name of the authenticated user out of the security context.
     *
     * @return The username extracted or "?user?" if none has been found or if the user is not authenticated anymore.
     */
    public static String getAuthenticatedUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            checkArgument(hasText(authentication.getName()), "authentication.name is null or empty:" + authentication.toString());
            return authentication.getName().trim().toLowerCase();
        } else {
            return "?user?";
        }
    }

    public static ImmutableList<String> getAuthenticatedUserRoles() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final List<String> rollen = Lists.newArrayList();

        if (authentication != null) {
            if (!authentication.getAuthorities().isEmpty()) {
                for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                    rollen.add(grantedAuthority.getAuthority());
                }
            }

        }

        return ImmutableList.copyOf(rollen);
    }

}
