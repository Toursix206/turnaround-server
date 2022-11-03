package com.toursix.turnaround.service.image.provider.dto.request;

import com.toursix.turnaround.common.type.FileType;

public interface UploadFileRequest {

    FileType getType();

    default void validateAvailableContentType(String contentType) {
        getType().validateAvailableContentType(contentType);
    }

    default String getFileNameWithBucketDirectory(String originalFileName) {
        return getType().createUniqueFileNameWithExtension(originalFileName);
    }
}
