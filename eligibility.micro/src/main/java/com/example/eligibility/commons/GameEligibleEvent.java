package com.example.eligibility.commons;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GameEligibleEvent {
    private Integer id;
    private String name;
    private Integer userId;
    private boolean isElegible;
}
