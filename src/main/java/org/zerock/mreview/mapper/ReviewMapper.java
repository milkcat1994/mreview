package org.zerock.mreview.mapper;

import org.mapstruct.Mapper;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.Review;

@Mapper
public interface ReviewMapper {

    Review dtoToEntity(ReviewDTO reviewDTO);

    ReviewDTO entityToDto(Review review);
}
