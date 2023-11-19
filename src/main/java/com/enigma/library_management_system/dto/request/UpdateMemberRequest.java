package com.enigma.library_management_system.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UpdateMemberRequest {
    private String memberId;
    private String memberName;
    private String memberEmail;
    private String address;
    private Boolean memberStatus;
}
