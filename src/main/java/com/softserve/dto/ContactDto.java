package com.softserve.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@With
public class ContactDto {
    private Integer id;

    @Size(min = 2, max = 150, message = "firstname should be between 2 and 150 chars")
    @NotBlank(message = "firstname cannot be empty")
    private String firstname;

    private String lastname;

    @NotBlank(message = "email cannot be empty")
    private String email;

    private String telephone;
}
