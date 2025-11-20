package com.browserstack.incoming_webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * DTO representing the full, nested structure of the incoming webhook JSON payload.
 * Using Java Records for concise, immutable data classes (requires Java 16+).
 */
public record WebhookPayload(
        String name,
        String status,
        String duration,
        String user,
        String tags,
        @JsonProperty("build_id") String buildId,
        @JsonProperty("build_number") String buildNumber,
        @JsonProperty("original_name") String originalName,
        @JsonProperty("finished_at") String finishedAt,
        @JsonProperty("started_at") String startedAt,
        @JsonProperty("quality_gate_result") String qualityGateResult,
        @JsonProperty("observability_url") String observabilityUrl,
        @JsonProperty("status_stats") StatusStats statusStats,
        @JsonProperty("failure_categories") FailureCategories failureCategories,
        @JsonProperty("smart_tags") SmartTags smartTags,
        @JsonProperty("unique_errors") UniqueErrors uniqueErrors
) {
    // Nested Record for "status_stats"
    public record StatusStats(
            String passed,
            String failed,
            String pending,
            String skipped,
            String unknown
    ) {}

    // Nested Record for "failure_categories"
    // Note: Since JSON keys have spaces, we use @JsonProperty to map them to camelCase fields.
    public record FailureCategories(
            @JsonProperty("To be Investigated") String toBeInvestigated,
            @JsonProperty("Automation Bug") String automationBug,
            @JsonProperty("Product Bug") String productBug,
            @JsonProperty("No Defect") String noDefect,
            @JsonProperty("Environment Issue") String environmentIssue
    ) {}

    // Nested Record for "smart_tags"
    // Note: Since JSON keys use underscores, we use @JsonProperty to map them to camelCase fields.
    public record SmartTags(
            @JsonProperty("is_flaky") String isFlaky,
            @JsonProperty("is_always_failing") String isAlwaysFailing,
            @JsonProperty("is_performance_anomaly") String isPerformanceAnomaly,
            @JsonProperty("is_new_failure") String isNewFailure
    ) {}

    // Nested Record for "unique_errors"
    public record UniqueErrors(
            Overview overview,
            @JsonProperty("top_unique_errors") List<TopUniqueError> topUniqueErrors
    ) {}

    // Nested Record for "unique_errors.overview"
    public record Overview(
            String insight,
            String count
    ) {}

    // Nested Record for items in "unique_errors.top_unique_errors" list
    public record TopUniqueError(
            String error,
            @JsonProperty("impacted_tests") String impactedTests
    ) {}
}
