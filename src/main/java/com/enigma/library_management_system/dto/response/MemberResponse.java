package com.enigma.library_management_system.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponse {
    private String memberId;
    private String memberName;
    private String memberEmail;
    private String address;
    private Boolean memberStatus;
}
