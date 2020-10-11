package com.eikinel.soundboard.button.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "buttons")
public class ButtonDto {
    public String id;
    public String name;
    public String description;
    public String fileName;
    public String color;
}
