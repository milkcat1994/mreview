package org.zerock.mreview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieImageDTO {
    private String uuid;
    private String imgName;
    private String path;

    // 전체 경로가 필요할 경우를 대비하여 구현한 함수
    public String getImageURL(){
        try{
            return URLEncoder
                    .encode(path + "/" + uuid + "_" + imgName, "UTF-8");
        }
        catch (UnsupportedEncodingException uee){
            uee.printStackTrace();
        }
        return "";
    }

    // 섬네일 url
    public String getThumbnailURL(){
        try{
            return URLEncoder
                    .encode(path + "/s_" + uuid + "_" + imgName, "UTF-8");
        }
        catch (UnsupportedEncodingException uee){
            uee.printStackTrace();
        }
        return "";
    }
}
