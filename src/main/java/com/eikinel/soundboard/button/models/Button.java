package com.eikinel.soundboard.button.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Button {
    public String name;
    public String description;
    public File file;
    public String color;
}
