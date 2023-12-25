package pl.teacherpanel.teacherpanel;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

@Repository
public class RatingRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Rating> getRatings() {
        String sql = "SELECT id, groupID, rating FROM ratings";
        List<Rating> ratings = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Rating.class));

        for (Rating rating : ratings) {
            String groupName = getGroupNameById(rating.getGroupID());
            rating.setGroupName(groupName);
        }

        return ratings;
    }

    private String getGroupNameById(int groupID) {
        try {
            String sql = "SELECT name FROM groups WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, String.class, groupID);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public ResponseEntity<Integer> addRating(List<Rating> ratings) {
        try {
            ratings.forEach(rating -> jdbcTemplate.update(
                    "INSERT INTO ratings(groupID, rating) VALUES (?, ?)",
                    rating.getGroupID(), rating.getRating()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
