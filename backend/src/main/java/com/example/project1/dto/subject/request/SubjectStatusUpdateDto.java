package com.example.project1.dto.subject.request;

import com.example.project1.common.enums.SubjectStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubjectStatusUpdateDto {
    @NotNull
    private SubjectStatus status;
}
