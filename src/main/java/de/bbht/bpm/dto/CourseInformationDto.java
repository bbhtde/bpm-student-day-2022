package de.bbht.bpm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseInformationDto {

    private Long id;
    private String courseNumber;
    private String courseTitle;
    private String description;
    private LocalDateTime start;
    private Integer maxSeats;
    private Integer currentSeats;
    private String pricing;

}
