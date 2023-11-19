package com.enigma.library_management_system.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewMemberRequest {
    private String memberName;
    private String memberEmail;
    private String address;
    private Boolean memberStatus;
}
