package com.musinsa.urlshortening.repository;

import com.musinsa.urlshortening.domain.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByOriginUrl(String url);
}
