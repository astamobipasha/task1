package mobi.asta.task1;

import java.util.concurrent.atomic.AtomicInteger;

public class Photo {

    private static final AtomicInteger countId = new AtomicInteger(0);


    private int id;

    private String fileName;

    private int takenAt;


    public Photo(String fileName, int takenAt) {
        this.id = countId.getAndIncrement();
        this.fileName = fileName;
        this.takenAt = takenAt;
    }


    public int getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public int getTakenAt() {
        return takenAt;
    }

}
