package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.api.SearchReportResponse;
import com.mockcompany.webapp.model.ProductItem;
import com.mockcompany.webapp.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.regex.Pattern;

@RestController
public class ReportController {

    private final EntityManager entityManager;
    private final SearchService searchService;

    @Autowired
    public ReportController(EntityManager entityManager, SearchService searchService) {
        this.entityManager = entityManager;
        this.searchService = searchService;
    }

    @GetMapping("/api/products/report")
    public SearchReportResponse runReport() {
        Map<String, Integer> hits = new HashMap<>();
        SearchReportResponse response = new SearchReportResponse();
        response.setSearchTermHits(hits);

        int count = this.entityManager.createQuery("SELECT item FROM ProductItem item").getResultList().size();
        response.setProductCount(count);

        List<ProductItem> allItems = entityManager.createQuery("SELECT item FROM ProductItem item").getResultList();

        // Perform searches using the SearchService
        hits.put("Cool", searchService.performSearch("cool").size());
        hits.put("Amazing", searchService.performSearch("amazing").size());

        int kidCount = 0;
        int perfectCount = 0;
        Pattern kidPattern = Pattern.compile("(.*)[kK][iI][dD][sS](.*)");
        for (ProductItem item : allItems) {
            if (kidPattern.matcher(item.getName()).matches() || kidPattern.matcher(item.getDescription()).matches()) {
                kidCount++;
            }
            if (item.getName().toLowerCase().contains("perfect") || item.getDescription().toLowerCase().contains("perfect")) {
                perfectCount++;
            }
        }

        hits.put("Kids", kidCount);
        hits.put("Perfect", perfectCount);

        return response;
    }
}
