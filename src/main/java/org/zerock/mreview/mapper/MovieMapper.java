package org.zerock.mreview.mapper;

import org.mapstruct.Mapper;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.MovieImageDTO;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface MovieMapper {

    Movie getMovieEntityFromDto(MovieDTO dto);

    default List<MovieImage> getMovieImageEntityFromDto(List<MovieImageDTO> movieImageDTOList, Movie movie){
        return movieImageDTOList.stream().map(movieImageDTO ->
                MovieImage.builder()
                   .path(movieImageDTO.getPath())
                   .imgName(movieImageDTO.getImgName())
                   .uuid(movieImageDTO.getUuid())
                   .movie(movie)
                   .build()
        ).collect(Collectors.toList());
    }

    default Map<String, Object> dtoToEntity(MovieDTO dto){
        Map<String, Object> entityMap = new HashMap<>();

        Movie movie = getMovieEntityFromDto(dto);
        entityMap.put("movie", movie);
        entityMap.put("imgList", getMovieImageEntityFromDto(dto.getImageDTOList(), movie));

        return entityMap;
    }
}
