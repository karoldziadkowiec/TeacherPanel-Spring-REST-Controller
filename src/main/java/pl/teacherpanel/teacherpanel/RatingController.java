package pl.teacherpanel.teacherpanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingController {
    @Autowired
    RatingRepository ratingRepository;

    @GetMapping("")
    public List<Rating> getAll() {
        return ratingRepository.getRatings();
    }

    @PostMapping("")
    public ResponseEntity<Integer> add(@RequestBody List<Rating> ratings) {
        return ratingRepository.addRating(ratings);
    }
}
