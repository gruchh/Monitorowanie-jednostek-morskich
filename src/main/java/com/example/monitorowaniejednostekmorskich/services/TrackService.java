package com.example.monitorowaniejednostekmorskich.services;
import com.example.monitorowaniejednostekmorskich.model.Point;
import com.example.monitorowaniejednostekmorskich.model.Track;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TrackService {

    private static final String restUrl = "https://www.barentswatch.no/bwapi/v2/geodata/ais/openpositions?Xmin=10.09094&Xmax=10.67047&Ymin=63.3989&Ymax=63.58645";

    public List<Point> getTracks() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IjBCM0I1NEUyRkQ5OUZCQkY5NzVERDMxNDBDREQ4OEI1QzA5RkFDRjMiLCJ0eXAiOiJhdCtqd3QiLCJ4NXQiOiJDenRVNHYyWi03LVhYZE1VRE4ySXRjQ2ZyUE0ifQ.eyJuYmYiOjE2NDQ0MzU0MzQsImV4cCI6MTY0NDQzOTAzNCwiaXNzIjoiaHR0cHM6Ly9pZC5iYXJlbnRzd2F0Y2gubm8iLCJhdWQiOiJhcGkiLCJjbGllbnRfaWQiOiJrdWJvbDJAZ21haWwuY29tOmdydWNoaCIsInNjb3BlIjpbImFwaSJdfQ.fiEIFuhDI4MqS3k-OjSc_m1egS3E8WZbKJYw3pCk8k89qv4DUwJLyqp9kojMWsa_Kx3Zfl5puqzd5l-jO0L94Wt2GmwtpCK6yRNNt_ZU7xhVeAKppCeGAfBMsOWE2BQh8_bfW8HGtp9maHD7TuB_yMoxNcSBSlwq05N3oRus2O5ZXzSHCAT1eCUuX6Yxb_G8nicWVeCrGwCRI70gvXCbaF2nEg7L7NVHCJhy4GaqumCAwMCPvdhZPxHwiCilRdXp8xfnURjDvBYzXCqmR6LT32c_7N7oACcnsNo5kL4V5GdtUpT0sYVWlBju2htmpRtO6TGggAqnL7CBn928S2oD_LKVaxCdWfLEqA0FhHb1vOM2JWGHYqTNzBRvPIkynoxyVAdRA8Sdb70zG93PVaid9eBcweSgV9dgd9toMfu0Qe5JJGJ3nKulghrM1JYHykX5ZfbFUtrNKO1luZNJZ6m_zSKCe31HFqd5zQ2KJy_yPf7d9SRiszo2dBWyz9hDUpoHejIXz57OoZvwM43LvsbnStpz2MI_a7Y8R4LafD5pdYREICThpXH_UzOluntiCztC8TREczk-URASG36hROKmS4etYppaI88tdX39fbN5YSfQKLPfXgdlYrhwvI_VaZHLQQqkaRJIhtbw8L9wauGqiwKJmcDGGxKqdJkJYgxDu50");
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<Track[]> exchange = restTemplate.exchange(restUrl,
                HttpMethod.GET,
                httpEntity,
                Track[].class
        );

        List<Point> collect = Stream.of(exchange.getBody()).map(track -> new Point(
                track.getGeometry().getCoordinates().get(1),
                track.getGeometry().getCoordinates().get(0),
                track.getName(),
                track.getDestination()
        )).collect(Collectors.toList());

        return collect;
    }
}
