package org.timur.roadmap.tennisscoreboard.dto;

import java.util.List;

public record FinishedMatchesResponse(
        List<FinishedMatchDto> matches,
        int currentPage,
        int totalPages
) {
}