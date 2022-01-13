package org.zerock.mreview.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
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

    @Mapping(target="imageDTOList", ignore = true)
    @Mapping(target="reviewCnt", ignore = true)
    @Mapping(target="avg", ignore = true)
    MovieDTO getMovieDtoFromEntity(Movie movie);

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


    default MovieDTO entitiesToDTO(Movie movie, List<MovieImage> movieImageList, Double avg, Long reviewCnt){
        MovieMapper mapper = Mappers.getMapper(MovieMapper.class);
        MovieDTO movieDTO = mapper.getMovieDtoFromEntity(movie);

        List<MovieImageDTO> movieImageDTOList = mapper.getMovieImageDtoFromEntity(movieImageList);

        movieDTO.setImageDTOList(movieImageDTOList);
        movieDTO.setAvg(avg);
        movieDTO.setReviewCnt(reviewCnt.intValue());

        return movieDTO;
    }

//    default MovieDTO getMovieDtoFromEntity(List<MovieImage> movieImageList, Movie movie){
//        MovieDTO movieDTO = MovieDTO.builder()
//                .mno(movie.getMno())
//
//        List<MovieImageDTO> movieImageDTOList = movieImageList.stream().map(movieImage ->
//                MovieImageDTO.builder()
//                        .imgName(movieImage.getImgName())
//                        .path(movieImage.getPath())
//                        .uuid(movieImage.getUuid())
//                        .build()
//        ).collect(Collectors.toList());
//
//
//    }

    List<MovieImageDTO> getMovieImageDtoFromEntity(List<MovieImage> movieImageList);
//    default List<MovieImage> getMovieImageDtoFromEntity(List<MovieImage> movieImageDTOList){
//        return movieImageDTOList.stream().map(movieImageDTO ->
//                MovieImage.builder()
//                        .path(movieImageDTO.getPath())
//                        .imgName(movieImageDTO.getImgName())
//                        .uuid(movieImageDTO.getUuid())
//                        .movie(movie)
//                        .build()
//        ).collect(Collectors.toList());
//    }

    default Map<String, Object> dtoToEntity(MovieDTO dto){
        Map<String, Object> entityMap = new HashMap<>();

        Movie movie = getMovieEntityFromDto(dto);
        entityMap.put("movie", movie);
        entityMap.put("imgList", getMovieImageEntityFromDto(dto.getImageDTOList(), movie));

        return entityMap;
    }
}
