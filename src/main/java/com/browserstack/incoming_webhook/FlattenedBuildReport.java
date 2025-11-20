package com.browserstack.incoming_webhook;


public record FlattenedBuildReport(
        // Core Build Info
        String name,
        String status,
        String duration,
        String user,
        String tags,
        String buildId,
        String buildNumber,
        String originalName,
        String finishedAt,
        String startedAt,
        String qualityGateResult,
        String observabilityUrl,

        // Status Stats (Flattened)
        String statusStatsPassed,
        String statusStatsFailed,
        String statusStatsPending,
        String statusStatsSkipped,
        String statusStatsUnknown,

        // Failure Categories (Flattened)
        String failureCategoryToBeInvestigated,
        String failureCategoryAutomationBug,
        String failureCategoryProductBug,
        String failureCategoryNoDefect,
        String failureCategoryEnvironmentIssue,

        // Smart Tags (Flattened)
        String smartTagIsFlaky,
        String smartTagIsAlwaysFailing,
        String smartTagIsPerformanceAnomaly,
        String smartTagIsNewFailure,

        // Unique Errors Overview (Flattened)
        String uniqueErrorsInsight,
        String uniqueErrorsCount,

        // Top 3 Unique Errors (Flattened into discrete fields)
        String topError1,
        String topError1Impact,
        String topError2,
        String topError2Impact,
        String topError3,
        String topError3Impact
) {}