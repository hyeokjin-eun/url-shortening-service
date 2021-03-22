package com.musinsa.urlshortening.domain.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(unique = true, columnDefinition = "VARCHAR(1000) COMMENT '원래 URL'", nullable = false)
    private String originUrl;

    @Column(columnDefinition = "BIGINT COMMENT '접속 횟수'", nullable = false)
    private Long count;

    @Column(columnDefinition = "DATETIME COMMENT '생성 일자'", nullable = false)
    private LocalDateTime created;

    public void countIncrease() {
        this.count++;
    }
}
