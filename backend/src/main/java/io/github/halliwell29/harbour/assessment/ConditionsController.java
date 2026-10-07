package io.github.halliwell29.harbour.assessment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConditionsController {

    private final ConditionsService conditionsService;

    public ConditionsController(ConditionsService conditionsService) {
        this.conditionsService = conditionsService;
    }

    @GetMapping("/api/sites/{siteId}/conditions")
    public ConditionsResponse conditions(@PathVariable Long siteId) {
        return conditionsService.conditionsFor(siteId);
    }
}
