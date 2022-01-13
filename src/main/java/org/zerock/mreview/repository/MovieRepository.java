package org.zerock.mreview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    // 페이지 처리
    // max() 사용대신 mi를 출력하여 가장 낮은 번호를 자동으로 연결할 수 있다.
    @Query("select m, mi, avg(coalesce(r.grade,0)), count(distinct r) " +
            "from Movie m " +
            "left outer join MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie = m group by m")
    Page<Object[]> getListPage(Pageable pageable);
    // 가장 나중에 추가된 이미지의 경우 아래와 같은 쿼리를 적용할 수 있다.
    /*
    select m, i, count(r)
    from Movie m
    left join MovieImage i on i.movie = m
    and i.inum = (select max(i2.inum) from MovieImage i2 where i2.movie = m)
    left outer join Review r on r.movie = m group by m
     */

    // 특정 영화 조회
    @Query("select m, mi " +
            "from Movie m left outer join MovieImage mi on mi.movie = m " +
            "where m.mno = :mno")
    List<Object[]> getMovieWithAll(Long mno);
}
