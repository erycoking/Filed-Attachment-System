package com.egerton.bugs.Model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Deadlines")
public class Deadlines {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;

    @Column(name = "From_Date",nullable = false, columnDefinition = "Date")
    private Date from;

    @Column(name = "To_Date", nullable = false, columnDefinition = "Date")
    private Date to;

    public Deadlines() {
    }

    public Deadlines(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
