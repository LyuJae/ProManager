package com.example.ryu.promanager;

/**
 * Created by YM on 2017-11-29.
 */
public class schedule_item {
    private String schedule_name;
    private String schedule_work;
    private String schedule_date;

    public schedule_item(String schedule_name, String schedule_work, String schedule_date) {
        this.schedule_name = schedule_name;
        this.schedule_work = schedule_work;
        this.schedule_date = schedule_date;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
    }

    public String getSchedule_work() {
        return schedule_work;
    }

    public void setSchedule_work(String schedule_work) {
        this.schedule_work = schedule_work;
    }
}
