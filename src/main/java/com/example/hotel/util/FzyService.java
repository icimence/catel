package com.example.hotel.util;

import com.example.hotel.enums.HotelStar;
import com.example.hotel.po.Hotel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FzyService {

    public double similarity(String keywords, Hotel hotel) {
        String[] words = keywords.split(" ");
        double res = 0;
        for (String word : words) {
            String regex = ".*" + word + ".*";
            if (hotel.getName().matches(regex)) res += 1;
            else if (hotel.getDescription().matches(regex)) res += 1;
            else if (relatedName(hotel.getHotelStar()).stream().anyMatch(name -> name.matches(regex))) res += 0.8;
        }
        return res / words.length;
    }

    private List<String> relatedName(Object o) {
        List<String> related = new ArrayList<>();
        if (o instanceof HotelStar) {
            String star = o.toString();
            switch (star) {
                case "Five":
                    related.add("五星级");
                    related.add("5星级");
                    break;
                case "Four":
                    related.add("四星级");
                    related.add("4星级");
                    break;
                case "Three":
                    related.add("三星级");
                    related.add("3星级");
                    break;
            }
        }
        return related;
    }

}
