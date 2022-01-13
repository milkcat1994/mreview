package org.zerock.mreview.service;

import org.mapstruct.factory.Mappers;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.MovieImageDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.dto.PageResultDTO;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;
import org.zerock.mreview.mapper.MovieMapper;

import java.util.List;

public interface MovieService {


    Long register(MovieDTO movieDTO);

    // 목록 처리
    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

}
