package com.example.tasklist3.repository.mappers;

import com.example.tasklist3.domain.task.Status;
import com.example.tasklist3.domain.task.Task;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TaskRowMapper {

    @SneakyThrows
    public static Task mapRow(ResultSet rs) {
        if (rs.next()) {
            return createTaskFromRow(rs);
        }
        return null;
    }

    private static Task createTaskFromRow(ResultSet rs) throws SQLException {
        Task task = new Task();
        String title = rs.getString("task_title");
        if (title == null) {
            return null;
        }
        task.setId(rs.getLong("task_id"));
        task.setTitle(title);
        task.setDescription(rs.getString("task_description"));
        task.setStatus(Status.valueOf(rs.getString("task_status")));
        Timestamp expirationDate = rs.getTimestamp("task_expiration_date");
        if (expirationDate != null) {
            task.setExpirationDate(expirationDate.toLocalDateTime());
        }
        return task;
    }

    @SneakyThrows
    public static List<Task> mapRows(ResultSet rs) {
        List<Task> tasks = new ArrayList<>();
        while (rs.next()) {
            Task task = createTaskFromRow(rs);
            if (task != null) {
                tasks.add(task);
            }
        }
        return tasks;
    }
}
