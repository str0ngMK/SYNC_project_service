package sync.example;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project/api/v1")
@RequiredArgsConstructor
public class TestController {

    // existing code...

    @GetMapping("/test")
    public ResponseEntity<String> responseTest() {
        return ResponseEntity.ok("Hello spring");
    }
}