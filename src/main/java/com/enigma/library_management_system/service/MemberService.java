package com.enigma.library_management_system.service;

import com.enigma.library_management_system.dto.request.NewMemberRequest;
import com.enigma.library_management_system.dto.request.UpdateMemberRequest;
import com.enigma.library_management_system.dto.response.MemberResponse;
import com.enigma.library_management_system.entity.Member;

import java.util.List;

public interface MemberService {
    MemberResponse createNew(NewMemberRequest request);
    MemberResponse update(UpdateMemberRequest request);
    MemberResponse getById(String id);
    Member findById(String id);
    List<MemberResponse> getAll();
    void delete(String id);
}
