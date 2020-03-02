package mpplibrary.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CheckoutRecordEntry implements Serializable {
    private LendableCopy copy;
    private LocalDate checkoutDate;
    private LocalDate dueDate;

    public CheckoutRecordEntry(LendableCopy copy, LocalDate checkoutDate, LocalDate dueDate) {
        this.copy = copy;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }
}