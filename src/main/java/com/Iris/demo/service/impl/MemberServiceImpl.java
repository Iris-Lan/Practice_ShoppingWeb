package com.Iris.demo.service.impl;

import com.Iris.demo.entity.Members;
import com.Iris.demo.repository.MemberRepository;
import com.Iris.demo.service.MemberService;
import com.Iris.demo.utils.JsonUtil;
import com.Iris.demo.utils.PageRequestUtil;
import com.Iris.demo.utils.UUIDGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    /**
     * Save Member.
     *
     * @param member Member data request
     * @return String Result
     */
    @Override
    @Transactional
    public String saveMember(Members member) {

        try{
            member.setMemberId(UUIDGenerator.createUUID());
            memberRepository.save(member);
            return "Insert OK";
        }catch (RuntimeException e){
            log.error("saveMember RuntimeException:{}", e.getMessage());
            return "Runtime error: " + e.getMessage();
        }catch (Exception e){
            log.error("saveMember Exception:{}", e.getMessage());
            return "Exception error: " + e.getMessage();
        }
    }

    /**
     * Delete Member by memberId.
     *
     * @param memberId Member data request
     * @return String Result
     */
    @Override
    @Transactional
    public String deleteMember(String memberId) {
        memberRepository.deleteByMemberId(memberId);
        return "Delete OK";
    }


    /**
     * Update Member by memberId.
     *
     * @param member Member data request
     * @return String Result
     */
    @Override
    @Transactional
    public String updateMember(Members member) {
        Members newMemberData = memberRepository.getById(member.getId());
        newMemberData.setMemberName(member.getMemberName());
        newMemberData.setGender(member.getGender());
        memberRepository.save(newMemberData);
        return "Update OK";
    }

    /**
     * Get Member by id.
     *
     * @param id Member data request
     * @return one Member data
     */
    @Override
    @Transactional
    public Members getMember(String id) {
        return memberRepository.getById(id);
    }

    /**
     * Get Members by gender with Page setting.
     * Gender: Female or Male.
     *
     * @param gender Member data request
     * @return Female or Male members
     */
    @Override
    @Transactional
    public List<Members> getMembers(
            @NotBlank String gender,
            @NotBlank String ordering,
            @NotBlank int displayPageNumber,
            @NotBlank int numberOfRowsPerPage){
        try{
            PageRequest pageRequest = PageRequestUtil.of(displayPageNumber-1,10, ordering, gender);
            Page<Members> page = memberRepository.findByGender(gender, pageRequest);
            return new ArrayList<>(page.getContent());
        }catch (RuntimeException e){
            log.error("Runtime error:{}", e.getMessage());
            return null;
        }catch (Exception e){
            log.error("Exception error:{}", e.getMessage());
            return null;
        }
    }


}
