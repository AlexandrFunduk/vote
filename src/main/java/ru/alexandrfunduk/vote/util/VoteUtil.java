package ru.alexandrfunduk.vote.util;

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

    public static void setVotingTime(LocalTime votingTime) {
        VoteUtil.votingTime = votingTime;
    }

    public static void setImitation(boolean imitation) {
        VoteUtil.imitation = imitation;
    }

    public static LocalTime getVotingTime() {
        return votingTime;
    }

    public static boolean isImitation() {
        return imitation;
    }

    public static boolean isEnableCreateAndUpdate() {
        return enableCreateAndUpdate;
    }

    public static void setEnableCreateAndUpdate(boolean before) {
        VoteUtil.enableCreateAndUpdate = before;
    }
}
