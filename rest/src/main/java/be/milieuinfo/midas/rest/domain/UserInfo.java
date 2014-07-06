package be.milieuinfo.midas.rest.domain;

import java.util.List;

/**
 * @since 23/06/14
 */
public class UserInfo {

    private String username;
    private List<String> roles;

    public UserInfo(final String username, final List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
