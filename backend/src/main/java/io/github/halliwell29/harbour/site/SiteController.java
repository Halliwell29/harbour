package io.github.halliwell29.harbour.site;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sites")
public class SiteController {

    private final SiteService siteService;

    public SiteController(SiteService siteService){
        this.siteService = siteService;
    }

    @GetMapping
    public List<SiteResponse> getSites() {
        return siteService.findAll()
                .stream()
                .map(SiteResponse::from)
                .toList();
    }

}
