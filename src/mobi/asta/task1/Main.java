package mobi.asta.task1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private static final int MAX_ITEMS_COUNT = 10_000;
    private static final String FILE_NAME_FORMAT = "IMG_%0" + String.valueOf(MAX_ITEMS_COUNT - 1).length() + "d.JPG";

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String MIN_POSSIBLE_DATE = "2016-01-01 00:00:00";
    private static final String MAX_POSSIBLE_DATE = "2017-12-31 23:59:59";
    private static final int SECONDS_IN_A_DAY = 86_400;

    private static Photo[] photos = new Photo[MAX_ITEMS_COUNT];


    public static void main(String[] args) {

        // region Timestamp initialization
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        int minTimestamp;
        int maxTimestamp;

        try {
            // Init min and max Unix timestamps
            minTimestamp = (int) (dateFormat.parse(MIN_POSSIBLE_DATE).getTime() / 1000);
            maxTimestamp = (int) (dateFormat.parse(MAX_POSSIBLE_DATE).getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        // endregion

        for (int i = 0; i < MAX_ITEMS_COUNT; i++) {
            photos[i] = new Photo(formatFileName(i), randomizeTimestamp(minTimestamp, maxTimestamp));
        }


        Photo[] filteredPhotos = findPhotos("2016-02-01");

        if (filteredPhotos != null) {
            for (Photo photo : filteredPhotos) {
                System.out.println(photo.getFileName() + " - " + new Date((long) photo.getTakenAt() * 1000));
            }
        }

    }

    private static String formatFileName(int number) {
        return String.format(FILE_NAME_FORMAT, number);
    }

    private static int randomizeTimestamp(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(minValue, maxValue + 1);
    }

    public static Photo[] findPhotos(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(SHORT_DATE_FORMAT);

        int startDateTimestamp;
        int endDateTimestamp;
        try {
            startDateTimestamp = (int) (dateFormat.parse(date).getTime() / 1000);
            endDateTimestamp = startDateTimestamp + SECONDS_IN_A_DAY - 1;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


        List<Photo> photosList = new ArrayList<>();

        for (Photo photo : photos) {
            if (photo.getTakenAt() > startDateTimestamp && photo.getTakenAt() < endDateTimestamp) {
                photosList.add(photo);
            }
        }

        return photosList.toArray(new Photo[photosList.size()]);
    }
}
