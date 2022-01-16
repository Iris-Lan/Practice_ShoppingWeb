package com.Iris.demo.service;


import com.Iris.demo.entity.Members;
import java.util.List;

public interface MemberService {

    String saveMember(Members member);

    String deleteMember(String memberId);

    String updateMember(Members member);

    Members getMember(String id);

    List<Members> getMembers(String gender, String ordering, int displayPageNumber, int numberOfRowsPerPage);
}
