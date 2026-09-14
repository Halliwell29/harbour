package io.github.halliwell29.harbour.site;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteService {
    private final SiteRepository siteRepository;

    public SiteService(SiteRepository siteRepository){
        this.siteRepository = siteRepository;
    }

    public List<Site> findAll(){
        return siteRepository.findAll();
    }
}
