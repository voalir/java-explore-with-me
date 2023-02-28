package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Hit;
import ru.practicum.model.ViewStat;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<Hit, Long> {

    @Query(value = "select new ru.practicum.model.ViewStat(h.app, h.uri, count(distinct h.ip)) " +
            "from Hit as h where h.timestamp between ?1 and ?2 " +
            "and h.uri in (?3) " +
            "group by h.app, h.uri")
    List<ViewStat> getViewStatsUniqueByUrls(LocalDateTime start, LocalDateTime end, List<String> urls);

    @Query(value = "select new ru.practicum.model.ViewStat(h.app, h.uri, count(h.id)) " +
            "from Hit as h where h.timestamp between ?1 and ?2 " +
            "and h.uri in (?3) " +
            "group by h.app, h.uri")
    List<ViewStat> getViewStatsByUrls(LocalDateTime start, LocalDateTime end, List<String> urls);

    @Query(value = "select new ru.practicum.model.ViewStat(h.app, h.uri, count(distinct h.ip)) " +
            "from Hit as h where h.uri in (?3) " +
            "group by h.app, h.uri")
    List<ViewStat> getViewStatsUnique(LocalDateTime start, LocalDateTime end);

    @Query(value = "select new ru.practicum.model.ViewStat(h.app, h.uri, count(h.id)) " +
            "from Hit as h where h.uri in (?3) " +
            "group by h.app, h.uri")
    List<ViewStat> getViewStats(LocalDateTime start, LocalDateTime end);
}

