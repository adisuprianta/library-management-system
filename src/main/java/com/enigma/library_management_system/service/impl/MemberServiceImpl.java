package com.enigma.library_management_system.service.impl;

import com.enigma.library_management_system.dto.request.NewMemberRequest;
import com.enigma.library_management_system.dto.request.UpdateMemberRequest;
import com.enigma.library_management_system.dto.response.MemberResponse;
import com.enigma.library_management_system.entity.Member;
import com.enigma.library_management_system.repository.MemberRepository;
import com.enigma.library_management_system.service.MemberService;
import com.enigma.library_management_system.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final ValidationUtil validationUtil;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public MemberResponse createNew(NewMemberRequest request) {
        try{
            validationUtil.validate(request);
            Member member = Member.builder()
                    .status(request.getMemberStatus())
                    .email(request.getMemberEmail())
                    .address(request.getAddress())
                    .name(request.getMemberName())
                    .build();
            memberRepository.saveAndFlush(member);
            return mapToRespose(member);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Member already exist");
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public MemberResponse update(UpdateMemberRequest request) {
        try{
            validationUtil.validate(request);
            Member member = getMember(request.getMemberId());
            member.setAddress(request.getAddress());
            member.setName(request.getMemberName());
            member.setEmail(request.getMemberEmail());
            member.setStatus(request.getMemberStatus());
            memberRepository.saveAndFlush(member);
            return mapToRespose(member);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Member already exist");
        }
    }


    @Override
    public MemberResponse getById(String id) {
        Member member = getMember(id);
        return mapToRespose(member);
    }

    @Override
    public Member findById(String id) {
        return getMember(id);
    }

    @Override
    public List<MemberResponse> getAll() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(this::mapToRespose).collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        Member member = getMember(id);
        memberRepository.delete(member);
    }
    private MemberResponse mapToRespose(Member member) {
        return MemberResponse.builder()
                .memberId(member.getId())
                .address(member.getAddress())
                .memberEmail(member.getEmail())
                .memberStatus(member.getStatus())
                .memberName(member.getName())
                .build();
    }
    private Member getMember(String id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "member not found")
        );
    }
}
