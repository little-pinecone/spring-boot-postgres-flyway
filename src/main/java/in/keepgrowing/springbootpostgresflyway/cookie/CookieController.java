package in.keepgrowing.springbootpostgresflyway.cookie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/cookies", produces = "application/json")
public class CookieController {

    private final CookieRepository cookieRepository;

    public CookieController(CookieRepository cookieRepository) {
        this.cookieRepository = cookieRepository;
    }

    @PostMapping
    public ResponseEntity<Cookie> save(@Valid @RequestBody Cookie cookie) {
        return ResponseEntity.ok().body(cookieRepository.save(cookie));
    }
}
