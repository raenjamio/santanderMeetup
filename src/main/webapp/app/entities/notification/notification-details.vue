<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="notification">
                <h2 class="jh-entity-heading"><span v-text="$t('meetupApp.notification.detail.title')">Notification</span> {{notification.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('meetupApp.notification.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{notification.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('meetupApp.notification.topic')">Topic</span>
                    </dt>
                    <dd>
                        <span>{{notification.topic}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('meetupApp.notification.meetup')">Meetup</span>
                    </dt>
                    <dd>
                        <div v-if="notification.meetup">
                            <router-link :to="{name: 'MeetupView', params: {meetupId: notification.meetup.id}}">{{notification.meetup.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('meetupApp.notification.user')">User</span>
                    </dt>
                    <dd>
                        {{notification.user ? notification.user.login : ''}}
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="notification.id" :to="{name: 'NotificationEdit', params: {notificationId: notification.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./notification-details.component.ts">
</script>
