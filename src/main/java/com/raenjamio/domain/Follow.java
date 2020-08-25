package com.raenjamio.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

/**
 * A Follow.
 */
@Document(collection = "follow")
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @DBRef
    @Field("user")
    private User user;

    @DBRef
    @Field("meetup")
    private Meetup meetup;

    public Follow() {
        //no-arg constructor
    }

    public Follow(User user, Meetup meetup) {
        this.user = user;
        this.meetup = meetup;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Follow user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Meetup getMeetup() {
        return meetup;
    }

    public Follow meetup(Meetup meetup) {
        this.meetup = meetup;
        return this;
    }

    public void setMeetup(Meetup meetup) {
        this.meetup = meetup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Follow)) {
            return false;
        }
        return id != null && id.equals(((Follow) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Follow{" +
            "id=" + getId() +
            "}";
    }
}
