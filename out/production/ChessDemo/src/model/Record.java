package model;


import java.util.List;

public class Record {
    private List<String> recordString;

    public List<String> getRecordString() {
        return recordString;
    }

    public void setRecordString(List<String> recordString) {
        this.recordString = recordString;
    }

    @Override
    public String toString() {
        return "Record{" +
                "recordString=" + recordString +
                '}';
    }
}
