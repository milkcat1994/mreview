package org.zerock.mreview.mapper;

import org.mapstruct.Mapper;
import org.zerock.mreview.dto.MovieDTO;

import java.util.Map;

@Mapper
public interface MovieMapper {
    Map<String, Object> dtoToEntity(MovieDTO dto);

//    MovieDTO entityToDto(Guestbook entity);
}
