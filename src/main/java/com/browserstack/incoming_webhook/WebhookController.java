package com.browserstack.incoming_webhook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller to receive the incoming webhook POST request.
 */
@RestController
@RequestMapping("/api/webhooks")
public class WebhookController {

    private static final Logger log = LoggerFactory.getLogger(WebhookController.class);
    private final WebhookService webhookService;

    // Constructor Injection (Spring automatically provides the WebhookService bean)
    public WebhookController(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @GetMapping("/hello")
    public void helloWorld()
    {
        System.out.println("Webhook Processor Service is Running!");
    }
    /**
     * Endpoint to receive the JSON webhook payload.
     * @param payload The incoming nested JSON payload, automatically mapped by Spring.
     * @return A ResponseEntity indicating the status of the operation.
     */
    @PostMapping("/build-report")
    public ResponseEntity<FlattenedBuildReport> processBuildReport(@RequestBody WebhookPayload payload) {
        // 1. Log the receipt of the payload
        log.info("Received webhook for build: {} ({})", payload.name(), payload.buildId());

        try {
            // 2. Flatten the nested data structure using the service
            FlattenedBuildReport flattenedReport = webhookService.flattenPayload(payload);

            // 3. Log the result and return it (for demonstration)
            log.info("Successfully flattened report. Status: {}", flattenedReport.status());
            log.info("Results from the build "+ flattenedReport.toString());

            // In a real application, you would now save 'flattenedReport' to a database (e.g., using Spring Data JPA).
            // Example: reportRepository.save(flattenedReport);

            // Return the flattened report with a 200 OK status
            return ResponseEntity.ok(flattenedReport);

        } catch (Exception e) {
            log.error("Error processing webhook payload for build ID: {}", payload.buildId(), e);
            e.printStackTrace();
            log.error(String.valueOf(e.getStackTrace()));
            // Return a 500 Internal Server Error if processing fails
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
