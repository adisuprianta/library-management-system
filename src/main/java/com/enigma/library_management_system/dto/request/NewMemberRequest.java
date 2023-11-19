package com.enigma.library_management_system.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewMemberRequest {
    @NotBlank(message = "member name is required")
    private String memberName;
    @NotBlank(message = "member email is required")
    @Pattern(regexp =  "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "invalid email")
    private String memberEmail;
    @NotBlank(message = "addres is required")
    private String address;
    @NotNull(message = "member status is required")
    private Boolean memberStatus;
}
