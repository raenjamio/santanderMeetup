<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="meetup">
                <h2 class="jh-entity-heading"><span v-text="$t('meetupApp.meetup.detail.title')">Meetup</span> {{meetup.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('meetupApp.meetup.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{meetup.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('meetupApp.meetup.dateMeetup')">Date Meetup</span>
                    </dt>
                    <dd>
                        <span>{{meetup.dateMeetup | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('meetupApp.meetup.locationDescription')">Location Description</span>
                    </dt>
                    <dd>
                        <span>{{meetup.locationDescription}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('meetupApp.meetup.numberOfPeopleConfirmed')">Number Of People Confirmed</span>
                    </dt>
                    <dd>
                        <span>{{meetup.numberOfPeopleConfirmed}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('meetupApp.meetup.assistantsConfirmed')">Assistants Confirmed</span>
                    </dt>
                    <dd>
                        <span v-for="(assistantsConfirmed, i) in meetup.assistantsConfirmeds" :key="assistantsConfirmed.id">{{i > 0 ? ', ' : ''}}
                            {{assistantsConfirmed.login}}
                        </span>
                    </dd>
                    <dt>
                        <span v-text="$t('meetupApp.meetup.enrolleds')">Enrolleds</span>
                    </dt>
                    <dd>
                        <span v-for="(enrolleds, i) in meetup.enrolleds" :key="enrolleds.id">{{i > 0 ? ', ' : ''}}
                            {{enrolleds.login}}
                        </span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="meetup.id" :to="{name: 'MeetupEdit', params: {meetupId: meetup.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./meetup-details.component.ts">
</script>
