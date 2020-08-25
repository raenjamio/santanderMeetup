package com.raenjamio.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Meetup.
 */
@Document(collection = "meetup")
public class Meetup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 100)
    @Field("description")
    private String description;

    @NotNull
    @Field("date_meetup")
    private ZonedDateTime dateMeetup;

    @Field("location_description")
    private String locationDescription;

    @Field("number_of_people_confirmed")
    private Long numberOfPeopleConfirmed;

    @DBRef
    @Field("assistantsConfirmeds")
    private Set<User> assistantsConfirmeds = new HashSet<>();

    @DBRef
    @Field("enrolleds")
    private Set<User> enrolleds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Meetup description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDateMeetup() {
        return dateMeetup;
    }

    public Meetup dateMeetup(ZonedDateTime dateMeetup) {
        this.dateMeetup = dateMeetup;
        return this;
    }

    public void setDateMeetup(ZonedDateTime dateMeetup) {
        this.dateMeetup = dateMeetup;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public Meetup locationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
        return this;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public Long getNumberOfPeopleConfirmed() {
        return numberOfPeopleConfirmed;
    }

    public Meetup numberOfPeopleConfirmed(Long numberOfPeopleConfirmed) {
        this.numberOfPeopleConfirmed = numberOfPeopleConfirmed;
        return this;
    }

    public void setNumberOfPeopleConfirmed(Long numberOfPeopleConfirmed) {
        this.numberOfPeopleConfirmed = numberOfPeopleConfirmed;
    }

    public Set<User> getAssistantsConfirmeds() {
        return assistantsConfirmeds;
    }

    public Meetup assistantsConfirmeds(Set<User> users) {
        this.assistantsConfirmeds = users;
        return this;
    }

    public Meetup addAssistantsConfirmed(User user) {
        this.assistantsConfirmeds.add(user);
        return this;
    }

    public Meetup removeAssistantsConfirmed(User user) {
        this.assistantsConfirmeds.remove(user);
        return this;
    }

    public void setAssistantsConfirmeds(Set<User> users) {
        this.assistantsConfirmeds = users;
    }

    public Set<User> getEnrolleds() {
        return enrolleds;
    }

    public Meetup enrolleds(Set<User> users) {
        this.enrolleds = users;
        return this;
    }

    public Meetup addEnrolleds(User user) {
        this.enrolleds.add(user);
        return this;
    }

    public Meetup removeEnrolleds(User user) {
        this.enrolleds.remove(user);
        return this;
    }

    public void setEnrolleds(Set<User> users) {
        this.enrolleds = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Meetup)) {
            return false;
        }
        return id != null && id.equals(((Meetup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Meetup{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", dateMeetup='" + getDateMeetup() + "'" +
            ", locationDescription='" + getLocationDescription() + "'" +
            ", numberOfPeopleConfirmed=" + getNumberOfPeopleConfirmed() +
            "}";
    }
}
