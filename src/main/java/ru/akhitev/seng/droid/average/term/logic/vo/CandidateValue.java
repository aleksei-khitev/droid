package ru.akhitev.seng.droid.average.term.logic.vo;

import java.time.LocalDate;

public class CandidateValue {
    private final String client;
    private final LocalDate groupDate;
    private final LocalDate startDate;
    private final String manager;

    public CandidateValue(String client, LocalDate groupDate, LocalDate startDate, String manager) {
        this.client = client;
        this.groupDate = groupDate;
        this.startDate = startDate;
        this.manager = manager;
    }

    public String client() {
        return client;
    }

    public LocalDate groupDate() {
        return groupDate;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public String manager() {
        return manager;
    }

    @Override
    public String toString() {
        return "CandidateValue{" +
                "client='" + client + '\'' +
                ", groupDate=" + groupDate +
                ", startDate=" + startDate +
                ", manager='" + manager + '\'' +
                '}';
    }
}
