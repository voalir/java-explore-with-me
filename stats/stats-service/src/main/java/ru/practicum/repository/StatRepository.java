package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Hit;
import ru.practicum.model.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<Hit, Long> {
    @Query(name = "getViewStatsUniqueByUrls", nativeQuery = true)
    List<ViewStatsDto> getViewStatsUniqueByUrls(LocalDateTime start, LocalDateTime end, List<String> urls);

    @Query(name = "getViewStatsByUrls", nativeQuery = true)
    List<ViewStatsDto> getViewStatsByUrls(LocalDateTime start, LocalDateTime end, List<String> urls);

    @Query(name = "getViewStatsUnique", nativeQuery = true)
    List<ViewStatsDto> getViewStatsUnique(LocalDateTime start, LocalDateTime end);

    @Query(name = "getViewStats", nativeQuery = true)
    List<ViewStatsDto> getViewStats(LocalDateTime start, LocalDateTime end);
}

