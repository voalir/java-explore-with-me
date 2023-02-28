package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Hit;
import ru.practicum.model.ViewStat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<Hit, Long> {
    @Query(name = "getViewStatsUniqueByUrlsQuery", nativeQuery = true)
    List<ViewStat> getViewStatsUniqueByUrls(LocalDateTime start, LocalDateTime end, List<String> urls);

    @Query(name = "getViewStatsByUrlsQuery", nativeQuery = true)
    List<ViewStat> getViewStatsByUrls(LocalDateTime start, LocalDateTime end, List<String> urls);

    @Query(name = "getViewStatsUniqueQuery", nativeQuery = true)
    List<ViewStat> getViewStatsUnique(LocalDateTime start, LocalDateTime end);

    @Query(name = "getViewStatsQuery", nativeQuery = true)
    List<ViewStat> getViewStats(LocalDateTime start, LocalDateTime end);
}

