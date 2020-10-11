package com.eikinel.soundboard.fileManager.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class File {
    private String fileName;
    private String fileType;
    private long size;
}
