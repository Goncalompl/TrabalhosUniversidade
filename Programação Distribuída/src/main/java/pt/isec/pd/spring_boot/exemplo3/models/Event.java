package pt.isec.pd.spring_boot.exemplo3.models;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Event {
    private int id;
    private String name;
    private String address;
    private Date date;
    private Time startTime;
    private Time endTime;
    private int code;
    private Timestamp codeTimestamp;

    public Event(int id, String name, String address, Date date, Time startTime, Time endTime, int code, Timestamp codeTimestamp) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.code = code;
        this.codeTimestamp = codeTimestamp;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public int getCode() {
        return code;
    }

    public Timestamp getCodeTimestamp() {
        return codeTimestamp;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Event{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", date=").append(date);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", code=").append(code);
        sb.append(", codeTimestamp=").append(codeTimestamp);
        sb.append('}');
        return sb.toString();
    }
}
