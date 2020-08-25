package com.raenjamio.service.dto;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A DTO for the {@link com.raenjamio.domain.Meetup} entity.
 */
public class MeetupDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 100)
    private String description;

    @NotNull
    private ZonedDateTime dateMeetup;

    private String locationDescription;

    private Long numberOfPeopleConfirmed = NumberUtils.LONG_ZERO;

    private Set<UserDTO> assistantsConfirmeds = new HashSet<>();

    private Set<UserDTO> enrolleds = new HashSet<>();

    public MeetupDTO() {
        //no-arg construct
    }

    public MeetupDTO(String id, @NotNull @Size(max = 100) String description, @NotNull ZonedDateTime dateMeetup, @NotNull String locationDescription, Long numberOfPeopleConfirmed) {
        this.id = id;
        this.description = description;
        this.dateMeetup = dateMeetup;
        this.locationDescription = locationDescription;
        this.numberOfPeopleConfirmed = numberOfPeopleConfirmed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDateMeetup() {
        return dateMeetup;
    }

    public void setDateMeetup(ZonedDateTime dateMeetup) {
        this.dateMeetup = dateMeetup;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public Long getNumberOfPeopleConfirmed() {
        return numberOfPeopleConfirmed;
    }

    public void setNumberOfPeopleConfirmed(Long numberOfPeopleConfirmed) {
        this.numberOfPeopleConfirmed = numberOfPeopleConfirmed;
    }

    public Set<UserDTO> getAssistantsConfirmeds() {
        return assistantsConfirmeds;
    }

    public void setAssistantsConfirmeds(Set<UserDTO> users) {
        this.assistantsConfirmeds = users;
    }

    public Set<UserDTO> getEnrolleds() {
        return enrolleds;
    }

    public void setEnrolleds(Set<UserDTO> users) {
        this.enrolleds = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MeetupDTO meetupDTO = (MeetupDTO) o;
        if (meetupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), meetupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MeetupDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", dateMeetup='" + getDateMeetup() + "'" +
            ", locationDescription='" + getLocationDescription() + "'" +
            ", numberOfPeopleConfirmed=" + getNumberOfPeopleConfirmed() +
            "}";
    }

    public void incrementAssistant() {
        Long numberUpdated = Optional.ofNullable(getNumberOfPeopleConfirmed()).map(number -> number + 1)
            .orElse(NumberUtils.LONG_ZERO);

        setNumberOfPeopleConfirmed(numberUpdated);
    }

    public static class Builder{
        public String id;
        public String description;
        public ZonedDateTime dateMeetup;
        public String locationDescription;
        public Long numnerOfPeopleConfirmed;

        public Builder with(Consumer<Builder> builderConsumer) {
            builderConsumer.accept(this);
            return this;
        }

        public MeetupDTO createMeetup() {
            return new MeetupDTO(id, description, dateMeetup, locationDescription, numnerOfPeopleConfirmed);
        }
    }
}
