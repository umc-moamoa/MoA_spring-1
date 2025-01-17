package com.springboot.moa.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class GetUserInterestRes {
    long postId;
    int point;
    String title;
    // 등록된 질문의 개수
    int qCount;
    String status;
}

