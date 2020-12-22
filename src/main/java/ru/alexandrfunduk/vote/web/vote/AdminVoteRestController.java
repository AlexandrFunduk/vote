package ru.alexandrfunduk.vote.web.vote;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.alexandrfunduk.vote.to.VoteTo;
import ru.alexandrfunduk.vote.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteRestController extends AbstractVoteController {
    static final String REST_URL = "/rest/admin/vote";

    @GetMapping
    public List<VoteTo> getAll() {
        log.info("getAll");
        return repository.getAll();
    }

    @GetMapping("/today")
    public List<VoteTo> getToday() {
        log.info("getToday");
        return repository.getByDay(LocalDate.now());
    }

    @GetMapping("/filter")
    public List<VoteTo> getFiltered(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                    @RequestParam @Nullable LocalDate startDate,
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                    @RequestParam @Nullable LocalDate endDate,
                                    @RequestParam @Nullable Integer userId) {
        log.info("getBetween dates {} - {} for user {}", startDate, endDate, userId);
        return userId == null ?
                repository.getBetween(DateTimeUtil.atStartOfDayOrMin(startDate), DateTimeUtil.atStartOfNextDayOrMax(endDate))
                : repository.getBetween(userId, DateTimeUtil.atStartOfDayOrMin(startDate), DateTimeUtil.atStartOfNextDayOrMax(endDate));
    }
}
