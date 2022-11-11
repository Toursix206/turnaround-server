package com.toursix.turnaround.service.image.provider;

import static com.toursix.turnaround.common.exception.ErrorCode.VALIDATION_IMAGE_SIZE_EXCEPTION;
import static com.toursix.turnaround.common.exception.ErrorCode.VALIDATION_REQUEST_MISSING_EXCEPTION;

import com.toursix.turnaround.common.exception.ValidationException;
import com.toursix.turnaround.service.image.client.S3FileStorageClient;
import com.toursix.turnaround.service.image.provider.dto.request.UploadFileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class S3Provider {

    private static final int MAX_FILE_SIZE = 51840; // 720 * 720

    private final S3FileStorageClient fileStorageClient;

    public String uploadFile(UploadFileRequest request, MultipartFile file) {
        if (file == null) {
            throw new ValidationException("이미지 파일이 없습니다.", VALIDATION_REQUEST_MISSING_EXCEPTION);
        }
        request.validateAvailableContentType(file.getContentType());
        String fileName = request.getFileNameWithBucketDirectory(file.getOriginalFilename());
        validateImageMaxSize(file);
        fileStorageClient.uploadFile(file, fileName);
        return fileStorageClient.getFileUrl(fileName);
    }

    public void deleteFile(String fileName) {
        if (fileName != null) {
            String[] image = fileName.split(".com/");
            fileStorageClient.deleteFile(image[1]);
        }
    }

    private void validateImageMaxSize(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ValidationException(
                    String.format("이미지 크기 (%s) 가 최대 사이즈 (%s) 보다 큽니다.", file.getSize(), MAX_FILE_SIZE),
                    VALIDATION_IMAGE_SIZE_EXCEPTION);
        }
    }
}
