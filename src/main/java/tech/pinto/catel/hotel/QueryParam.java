package tech.pinto.catel.hotel;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QueryParam {
    private Integer limit;
    private Integer page;
    private Filter filter;

    @Data
    public static class Filter {
        private Double priceLower;
        private Double priceUpper;
        private Double rate;
        private String name;
        private BizRegion region;
        private LocalDate inDate;
        private LocalDate outDate;
        private int[] stars;
    }
}
