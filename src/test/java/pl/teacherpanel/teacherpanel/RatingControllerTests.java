package pl.teacherpanel.teacherpanel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.teacherpanel.teacherpanel.Rating;
import pl.teacherpanel.teacherpanel.RatingController;
import pl.teacherpanel.teacherpanel.RatingRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RatingController.class)
public class RatingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RatingRepository ratingRepository;

    @Captor
    private ArgumentCaptor<List<Rating>> ratingsCaptor;

    // 9)POST: /api/rating - dodaje ocenę dla grupy nauczycielskiej
    @Test
    public void testAddRating() throws Exception {
        Rating rating = new Rating(1, 2);

        mockMvc.perform(post("/api/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Arrays.asList(rating))))
                .andExpect(status().isOk());

        verify(ratingRepository).addRating(ratingsCaptor.capture());

        assertEquals(1, ratingsCaptor.getValue().size());
        assertEquals(rating.getGroupID(), ratingsCaptor.getValue().get(0).getGroupID());
        assertEquals(rating.getRating(), ratingsCaptor.getValue().get(0).getRating());
    }
}