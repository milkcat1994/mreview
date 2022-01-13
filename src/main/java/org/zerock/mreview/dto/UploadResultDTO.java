package org.zerock.mreview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO implements Serializable {
    private String fileName;
    private String uuid;
    private String folderPath;

    // 전체 경로가 필요할 경우를 대비하여 구현한 함수
    public String getImageURL(){
        try{
            return URLEncoder
                    .encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
        }
        catch (UnsupportedEncodingException uee){
            uee.printStackTrace();
        }
        return "";
    }
}
