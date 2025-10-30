package com.avwaveaf.springrestmvc.model.branch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Branch {
    private UUID id;
    private String branchName;
    private String address;
    private String phoneNumber;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
