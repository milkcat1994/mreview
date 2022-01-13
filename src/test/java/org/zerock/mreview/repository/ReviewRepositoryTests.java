package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertReviews(){

        // 200개의 리뷰 등록
        IntStream.rangeClosed(1,200).forEach(i -> {
            // 영화 번호
            Long mno = (long)(Math.random()* 100)+ 1;

            // 리뷰어 번호
            Long mid = ((long)(Math.random()* 100)+ 1);
            Member member = Member.builder()
                    .mid(mid)
                    .build();

            Review review = Review.builder()
                    .member(member)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random()* 5)+ 1)
                    .text("이 영화에 대한 느낌..."+ i)
                    .build();

            reviewRepository.save(review);
        });
    }

    // Review 클래스의 Member가 LAZY 방식이기 때문에 한번에 조회 할 수 없다.
    // 기존과 달리 Transactional을 적용하여도 내부 .getMember().getEmail() 처리시 Member객체를 로딩해야한다.
    // 해결방법
    // 1. @Query를 이용하여 조인 처리를 하거나
    // 2. @EntityGraph를 이용해서 Review 객체를 가져올 때 Member객체를 로딩한다.
    @Test
    public void testGetMovieReviews(){
        Movie movie = Movie.builder()
                .mno(92L)
                .build();

        List<Review> result = reviewRepository.findByMovie(movie);

        result.forEach(movieReview -> {
            System.out.println(movieReview.getReviewnum());
            System.out.println("\t"+ movieReview.getGrade());
            System.out.println("\t"+ movieReview.getText());
            System.out.println("\t"+ movieReview.getMember().getEmail());
            System.out.println("-----------------------");

        });
    }
}
