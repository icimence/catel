package tech.pinto.catel.hotel;

import lombok.Data;
import tech.pinto.catel.enums.BizRegion;

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
        private BizRegion region;
        private LocalDate inDate;
        private LocalDate outDate;
        private int[] stars;
    }
}
