package com.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateRequest {
    @NotBlank(message = "FirstName must not be Blank")
    @Size(min = 2, message = "FirstName must contain at least 2 characters")
    private String firstName;
    @NotBlank(message = "LastName must not be Blank")
    @Size(min = 2, message = "LastName must contain at least 2 characters")
    private String lastName;

}
