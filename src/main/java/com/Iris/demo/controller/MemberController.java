package com.Iris.demo.controller;

import com.Iris.demo.entity.Members;
import com.Iris.demo.service.MemberService;
import com.Iris.demo.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Ans2: Create/Delete/Update/Get member data
 *
 * @author Iris Lan
 * @since 2022.1.16
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemberController {

    private final MemberService memberService;

    /**
     * Get Member by id.
     */
    @GetMapping(value = "/getMember")
    public Members getMember(@NotBlank @RequestBody String id){
        log.info("getMember id:{}", id);
        return memberService.getMember(id);
    }

    /**
     * Get Members by gender with Page setting.
     * Gender: Female or Male.
     */
    @GetMapping(value = "/getMembers")
    public List<Members> getMembers(
            @NotBlank @RequestBody String gender,
            @NotBlank @RequestBody String ordering,
            @NotBlank @RequestBody int displayPageNumber,
            @NotBlank @RequestBody int numberOfRowsPerPage){
        log.info("getMembers gender:{}, ordering:{}, displayPageNumber:{}, numberOfRowsPerPage:{}",
                gender, ordering, displayPageNumber, numberOfRowsPerPage);
        return memberService.getMembers(gender, ordering, displayPageNumber, numberOfRowsPerPage);
    }

    /**
     * Save Member.
     */
    @PostMapping(value = "/saveMember")
    public String saveMember(@Valid @RequestBody Members member){
        log.info("saveMember data:{}", JsonUtil.getJsonString(member));
        return memberService.saveMember(member);
    }

    /**
     * Delete Member by memberId.
     */
    @PostMapping(value = "/deleteMember")
    public String deleteMember(@NotBlank @RequestBody String memberId){
        log.info("deleteMember memberId:{}", memberId);
        return memberService.deleteMember(memberId);
    }

    /**
     * Update Member by memberId.
     */
    @PostMapping(value = "/updateMember")
    public String updateMember(@Valid @RequestBody Members member){
        log.info("updateMember member data:{}", member);
        return memberService.updateMember(member);
    }
}
