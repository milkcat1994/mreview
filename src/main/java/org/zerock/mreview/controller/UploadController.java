package org.zerock.mreview.controller;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.mreview.dto.UploadResultDTO;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    @Value("${org.zerock.upload.path}") //application.properties의 변수
    private String uploadPath;

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles){

        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for(MultipartFile uploadFile : uploadFiles){

            // 이미지 파일만 업로드 가능
            if(uploadFile.getContentType().startsWith("image") == false){
                log.warn("this file is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            try{
                // 실제 파일 이름 IE나 Edge는 전체 경로가 들어온다.
                String originalName = uploadFile.getOriginalFilename();
                String fileName = originalName.substring(originalName.lastIndexOf("\\")+ 1);
                log.info("fileName: "+ fileName);

                // 날짜 폴더 생성
                String folderPath = makeFolder();

                // UUID
                String uuid = UUID.randomUUID().toString();

                // 저장할 파일 이름 중간에 "_"를 이용해서 구분
                String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_"+ fileName;

                Path savePath = Paths.get(saveName);

                try{
                    // 원본 파일 저장
                    uploadFile.transferTo(savePath);

                    // 섬네일 생성
                    String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
                    // 섬네일 파일 이름은 중간에 s_로 시작하도록 구성
                    File thumbnailFile = new File(thumbnailSaveName);
                    // 섬네일 생성
                    Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100);

                    resultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));
                }
                catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
            catch (NullPointerException npe){
                npe.printStackTrace();
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }

    // /display?fileName=xxxx URL로 호출시에 이미지가 전송 되도록 메서드를 추가한다.
    // URL 인코딩된 파일 이름을 파라미터로 받아 해당 파일을 byte[]로 만들어서 브라우저로 전송
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName){
        ResponseEntity<byte[]> result = null;

        try{
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("fileName: "+ srcFileName);

            File file = new File(uploadPath + File.separator + srcFileName);
            log.info("file: "+ file);

            HttpHeaders header = new HttpHeaders();

            // MIME 타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            // 파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){
        String srcFileName = null;
        try{
            srcFileName = URLDecoder.decode(fileName, "UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            boolean result = file.delete();

            File thumbnail = new File(file.getParent(), "s_" + file.getName());

            result = thumbnail.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (UnsupportedEncodingException uee){
            uee.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String makeFolder(){
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        // make folder --------
        File uploadPathFolder = new File(uploadPath, folderPath);

        if(uploadPathFolder.exists() == false){
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }
}
