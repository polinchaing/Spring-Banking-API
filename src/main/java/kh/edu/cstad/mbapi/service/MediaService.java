package kh.edu.cstad.mbapi.service;

import kh.edu.cstad.mbapi.dto.MediaResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    MediaResponse uploadMedia(MultipartFile file);


    List<MediaResponse> upload(List<MultipartFile> files);

    void deleteByName(String fileName);

    ResponseEntity<Resource> downloadByName(String fileName);

}
