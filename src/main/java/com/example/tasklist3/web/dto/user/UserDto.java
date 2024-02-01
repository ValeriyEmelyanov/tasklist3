package com.example.tasklist3.web.dto.user;

import com.example.tasklist3.domain.task.Task;
import com.example.tasklist3.domain.user.Role;
import com.example.tasklist3.web.dto.validation.OnCreate;
import com.example.tasklist3.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Set;

@Data
@Schema(description = "User DTO")
public class UserDto {

    @NotNull(message = "Id must be not null", groups = OnUpdate.class)
    @Schema(description = "User id", example = "1")
    private Long id;

    @NotNull(message = "Name must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Name length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    @Schema(description = "User name", example = "John Doe")
    private String name;

    @NotNull(message = "Username must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Username length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    @Schema(description = "User email", example = "johndoe@gmail.com")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Schema(description = "User crypted password", example = "12345")
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password confirmation must be not null", groups = {OnCreate.class})
    @Schema(description = "User password confirmation", example = "12345")
    private String passwordConfirmation;

}
