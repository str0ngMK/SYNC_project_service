package project.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project/api/v1")
@RequiredArgsConstructor
@Slf4j
public class TestController {
    int loadBalanceCount = 0;
    // existing code...
    @GetMapping("/test")
    public ResponseEntity<String> responseTest() {
        loadBalanceCount += 1;
        log.info("loadBalanceCount : " + loadBalanceCount);
        return ResponseEntity.ok("Hello spring boot!!!!!!!!!!!");
    }
}