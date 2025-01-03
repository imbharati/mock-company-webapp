package com.mockcompany.webapp.service;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private final ProductItemRepository productItemRepository;

    @Autowired
    public SearchService(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    public List<ProductItem> performSearch(String query) {
        if (query == null || query.isEmpty()) {
            return new ArrayList<>();
        }

        String queryLowerCase = query.toLowerCase();
        List<ProductItem> results = new ArrayList<>();
        Iterable<ProductItem> allItems = productItemRepository.findAll();

        for (ProductItem item : allItems) {
            if ((item.getName() != null && item.getName().toLowerCase().contains(queryLowerCase)) ||
                (item.getDescription() != null && item.getDescription().toLowerCase().contains(queryLowerCase))) {
                results.add(item);
            }
        }

        return results;
    }
  
    }
    

