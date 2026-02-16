package com.ncatania.statisticsservice.infraestructure.adapters.in.web;

import com.ncatania.statisticsservice.application.dto.ClickStatisticResponse;
import com.ncatania.statisticsservice.application.dto.TopUrlStatisticResponse;
import com.ncatania.statisticsservice.application.ports.in.ClickStatisticUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final ClickStatisticUseCase clickStatisticUseCase;

    @GetMapping("/url/{urlId}")
    public ResponseEntity<List<ClickStatisticResponse>> getStatisticsByUrlId(
            @PathVariable Long urlId) {
        List<ClickStatisticResponse> statistics = clickStatisticUseCase.getStatisticsByUrlId(urlId);
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<TopUrlStatisticResponse>> getTopClickedUrls() {
        List<TopUrlStatisticResponse> ranking = clickStatisticUseCase.getTopClickedUrls();
        return ResponseEntity.ok(ranking);
    }
}
