package com.example.tasklist3.repository.mappers;

import com.example.tasklist3.domain.user.Role;
import com.example.tasklist3.domain.user.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class UserRowMapper {

    @SneakyThrows
    public static User mapRow(ResultSet rs) {
        Set<Role> roles = new HashSet<>();
        while (rs.next()) {
            roles.add(Role.valueOf(rs.getString("user_role_role")));
        }
        rs.beforeFirst();
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getLong("user_id"));
            user.setName(rs.getString("user_name"));
            user.setUsername(rs.getString("user_username"));
            user.setPassword(rs.getString("user_password"));
            user.setRoles(roles);
            return user;
        }
        return null;
    }

}
