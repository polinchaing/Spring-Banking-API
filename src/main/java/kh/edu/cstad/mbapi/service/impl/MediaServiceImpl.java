package kh.edu.cstad.mbapi.service.impl;

import kh.edu.cstad.mbapi.domain.Media;
import kh.edu.cstad.mbapi.dto.MediaResponse;
import kh.edu.cstad.mbapi.repository.MediaRepository;
import kh.edu.cstad.mbapi.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    @Value("${media.server-path}")
    private String serverPath;

    @Value("${media.base-uri}")
    private String baseUri;

    @Override
    public MediaResponse uploadMedia(MultipartFile file) {

        //1.save file to server path
        String name = UUID.randomUUID().toString();
        int lastIndex = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        String extension = file.getOriginalFilename().substring(lastIndex + 1);

        //create path object
        Path path = Paths.get(serverPath + String.format("%s/%s", name, extension));

        try{
            Files.copy(file.getInputStream(),path);
        }catch(IOException e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File upload failed"
                    );
        }

        //2. save metadata of media into database
        Media media  = new Media();
        media.setName(name);
        media.setExtension(extension);
        media.setMimeTypeFile(file.getContentType());
        media.setIsDeleted(false);

        media = mediaRepository.save(media);

        return MediaResponse.builder()
                .name(media.getName())
                .extension(media.getExtension())
                .mimeTypeFile(media.getMimeTypeFile())
                .uri(baseUri + String.format("%s.%s", name, extension))
                .size(file.getSize())
                .build();
    }
    @Override
    public List<MediaResponse> upload(List<MultipartFile> files) {
        return files.stream()
                .map(this::uploadMedia)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByName(String fileName) {
        Media media = mediaRepository.findByName(fileName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found")
        );
        mediaRepository.delete(media);
    }

    @Override
    public ResponseEntity<Resource> downloadByName(String fileName) {
        Media media = mediaRepository.findByName(fileName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found"));

        Path filePath = Paths.get(serverPath).resolve(media.getName()).normalize();
        Path serverRoot = Paths.get(serverPath).toAbsolutePath().normalize();

        if (!filePath.toAbsolutePath().startsWith(serverRoot)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file path");
        }

        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
            }
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading file");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + media.getName() + "\"")
                .body(resource);
    }


}
