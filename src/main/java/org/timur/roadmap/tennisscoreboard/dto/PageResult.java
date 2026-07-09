package org.timur.roadmap.tennisscoreboard.dto;

import java.util.List;

public record PageResult<T>(
        List<T> items,
        int currentPage,
        int totalPages,
        long totalItems) {
}