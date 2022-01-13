package org.zerock.mreview.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.Review;

@Mapper
public interface ReviewMapper {

    @Mapping(target="movie.mno", source = "reviewDTO.mno")
    @Mapping(target="member.mid", source = "reviewDTO.mid")
    Review dtoToEntity(ReviewDTO reviewDTO);

//    @Mapping(target = "movie", ignore = true)
//    @Mapping(target = "member", ignore = true)

//    Review getEntityFromDto(ReviewDTO reviewDTO);

//    default Review dtoToEntity(ReviewDTO reviewDTO){
//        Review review = getEntityFromDto(reviewDTO);
//        review.
//    }

    @Mapping(target="mno", source = "review.movie.mno")
    @Mapping(target="mid", source = "review.member.mid")
    @Mapping(target="nickname", source = "review.member.nickname")
    @Mapping(target="email", source = "review.member.email")
    ReviewDTO entityToDto(Review review);
}
