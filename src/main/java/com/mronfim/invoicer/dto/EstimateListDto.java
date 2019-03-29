package com.mronfim.invoicer.dto;

import java.util.List;
import java.util.ArrayList;

import com.mronfim.invoicer.model.Estimate;
import com.mronfim.invoicer.model.EstimateClient;
import com.mronfim.invoicer.model.Item;

public class EstimateListDto {

    private List<EstimateDto> estimates;

    public EstimateListDto() {}
    public EstimateListDto(List<Estimate> estimates) {
        this.estimates = new ArrayList<EstimateDto>();
        for (Estimate estimate : estimates) {
            this.estimates.add(new EstimateDto(estimate));
        }
    }

    public List<EstimateDto> getEstimates() { return estimates; }

    class EstimateDto {

        private Long id;
        private String title;
        private int estimateNumber;
        private EstimateClient client;
        private double total;

        public EstimateDto(Estimate estimate) {
            id = estimate.getId();
            title = estimate.getTitle();
            estimateNumber = estimate.getEstimateNumber();
            client = estimate.getClient();
            total = 0;
            estimate.getItems().forEach(item -> total += item.getPrice());
        }

        public Long getId() { return id; }
	
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public int getEstimateNumber() { return estimateNumber; }
        public void setEstimateNumber(int number) { estimateNumber = number; }
        
        public EstimateClient getClient() { return client; }
        public void setClient(EstimateClient client) { this.client = client; }

        public double getTotal() { return total; }
        public void setTotal(double total) { this.total = total; }
    }
}