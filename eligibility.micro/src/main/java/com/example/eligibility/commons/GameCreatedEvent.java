package com.example.eligibility.commons;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GameCreatedEvent {
    private Integer id;
    private String name;
    private Integer userId;
}
