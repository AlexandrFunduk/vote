package ru.alexandrfunduk.vote.util;

import ru.alexandrfunduk.vote.model.Vote;
import ru.alexandrfunduk.vote.to.VoteTo;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class VoteUtil {
    private static LocalTime votingTime = LocalTime.of(11, 0);
    private static boolean imitation = false;
    private static boolean enableCreateAndUpdate = true;

    public static boolean enableCreateAndUpdate(LocalDateTime dateTime) {
        if (imitation) {
            return enableCreateAndUpdate;
        }
        return dateTime.toLocalTime().isBefore(votingTime);
    }

    public static void setImitation(boolean imitation) {
        VoteUtil.imitation = imitation;
    }

    public static void setEnableCreateAndUpdate(boolean before) {
        VoteUtil.enableCreateAndUpdate = before;
    }

    public static VoteTo asTo(Vote vote) {
        return new VoteTo(vote.id(), vote.getDate(), vote.getUser().getId(), vote.getRestaurant().getId());
    }

    public static VoteTo asTo(Vote vote, int userId) {
        return new VoteTo(vote.id(), vote.getDate(), userId, vote.getRestaurant().getId());
    }
}
