package com.egerton.bugs.Model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "companyFeedback")
public class CompanyFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @OneToOne
    @JoinColumn(name = "town", nullable = false)
    private Town town;

    @NotEmpty(message = "Feedback cannot be empty")
    @Column(name = "feedback", nullable = false, columnDefinition = "TEXT")
    private String feedback;

    @NotEmpty(message = "date cannot be null")
    @Column(name = "dateTimestamp", nullable = false, length = 255, columnDefinition = "BIGINT")
    private long dateInMilliSeconds;

    public CompanyFeedback(Town town, String feedback, long dateInMilliSeconds) {
        this.town = town;
        this.feedback = feedback;
        this.dateInMilliSeconds = dateInMilliSeconds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public long getDateInMilliSeconds() {
        return dateInMilliSeconds;
    }

    public void setDateInMilliSeconds(long dateInMilliSeconds) {
        this.dateInMilliSeconds = dateInMilliSeconds;
    }

    @Override
    public String toString() {
        return "CompanyFeedback{" +
                "id=" + id +
                ", town=" + town +
                ", feedback='" + feedback + '\'' +
                ", dateInMilliSeconds=" + dateInMilliSeconds +
                '}';
    }
}
