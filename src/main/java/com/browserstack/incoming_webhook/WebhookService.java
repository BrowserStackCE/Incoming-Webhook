package com.browserstack.incoming_webhook;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for business logic, specifically converting
 * the nested WebhookPayload into a flat FlattenedBuildReport.
 */
@Service
public class WebhookService {

    private static final Logger log = LoggerFactory.getLogger(WebhookService.class);

    /**
     * Converts the nested incoming payload into a flat report structure.
     * @param payload The incoming nested JSON payload.
     * @return A flattened version of the report data.
     */
    public FlattenedBuildReport flattenPayload(WebhookPayload payload) {
        log.info("Starting flattening process for build ID: {}", payload.buildId());

        // Helper to safely get top error data, handles cases where the list is too short.
        List<WebhookPayload.TopUniqueError> topErrors = payload.uniqueErrors().topUniqueErrors();
        WebhookPayload.TopUniqueError error1 = topErrors.size() > 0 ? topErrors.get(0) : null;
        WebhookPayload.TopUniqueError error2 = topErrors.size() > 1 ? topErrors.get(1) : null;
        WebhookPayload.TopUniqueError error3 = topErrors.size() > 2 ? topErrors.get(2) : null;

        // Build and return the flattened report
        return new FlattenedBuildReport(
                // Core Build Info
                payload.name(),
                payload.status(),
                payload.duration(),
                payload.user(),
                payload.tags(),
                payload.buildId(),
                payload.buildNumber(),
                payload.originalName(),
                payload.finishedAt(),
                payload.startedAt(),
                payload.qualityGateResult(),
                payload.observabilityUrl(),

                // Status Stats (Nested -> Flat)
                payload.statusStats().passed(),
                payload.statusStats().failed(),
                payload.statusStats().pending(),
                payload.statusStats().skipped(),
                payload.statusStats().unknown(),

                // Failure Categories (Nested -> Flat)
                payload.failureCategories().toBeInvestigated(),
                payload.failureCategories().automationBug(),
                payload.failureCategories().productBug(),
                payload.failureCategories().noDefect(),
                payload.failureCategories().environmentIssue(),

                // Smart Tags (Nested -> Flat)
                payload.smartTags().isFlaky(),
                payload.smartTags().isAlwaysFailing(),
                payload.smartTags().isPerformanceAnomaly(),
                payload.smartTags().isNewFailure(),

                // Unique Errors Overview (Nested -> Flat)
                payload.uniqueErrors().overview().insight(),
                payload.uniqueErrors().overview().count(),

                // Top 3 Unique Errors (List elements -> Discrete fields)
                error1 != null ? error1.error() : "N/A",
                error1 != null ? error1.impactedTests() : "N/A",
                error2 != null ? error2.error() : "N/A",
                error2 != null ? error2.impactedTests() : "N/A",
                error3 != null ? error3.error() : "N/A",
                error3 != null ? error3.impactedTests() : "N/A"
        );
    }
}
