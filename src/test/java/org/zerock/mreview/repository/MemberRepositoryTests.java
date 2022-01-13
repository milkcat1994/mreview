package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    @Test
    public void testDeleteMember(){
        Long mid = 1L;

        Member member = Member.builder()
                .mid(mid)
                .build();

        memberRepository.deleteById(mid);
        reviewRepository.deleteByMember(member);
    }
}
