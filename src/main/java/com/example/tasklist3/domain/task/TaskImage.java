package com.example.tasklist3.domain.task;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TaskImage {

    private MultipartFile file;

}
