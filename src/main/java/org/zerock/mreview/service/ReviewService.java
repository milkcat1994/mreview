package org.zerock.mreview.service;

import org.zerock.mreview.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {

    // 영화의 모든 리뷰를 가져온다.
    List<ReviewDTO> getListOfMovie(Long mno);

    // 영화 리뷰를 추가
    Long register(ReviewDTO reviewDTO);

    // 특정한 영화리뷰 수정
    void modify(ReviewDTO reviewDTO);

    // 영화 리뷰 삭제
    void remove(Long reviewnum);
}
