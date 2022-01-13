package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.entity.Member;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMembers(){

        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .email("r"+ i+"@zerock.org")
                    .pw("1111")
                    .nickname("reviewer"+ i)
                    .build();

            memberRepository.save(member);
        });
    }

    // FK로 참조하고 있는 상태이기에 PK쪽을 먼저 삭제할 수 없다.
    // 1. FK쪽(Review)을 먼저 삭제하고
    // 2. 메서드 선언부에 @Transactional, @Commit을 추가한다.
    // 추가 문제점!
    // review를 지울 때 마다 계속 Query를 호출 하게 된다.
    @Commit
    @Transactional
    @Test
    public void testDeleteMember(){
        Long mid = 1L;

        Member member = Member.builder()
                .mid(mid)
                .build();

        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);
    }
}
