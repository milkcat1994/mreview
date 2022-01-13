package org.zerock.mreview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    // reivew num
    private Long reviewnum;

    // Movie mno
    private Long mno;

    // Member id, nickname, email
    private Long mid;
    private String nickname;
    private String email;

    private int grade;
    private String text;
    private LocalDateTime regDate, modDate;
}
