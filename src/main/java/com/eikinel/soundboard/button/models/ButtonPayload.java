package com.eikinel.soundboard.button.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ButtonPayload {
	public String name;
	public String category;
	public String description;
	public MultipartFile file;
	public String color;
}
