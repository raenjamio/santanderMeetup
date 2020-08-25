<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="follow">
                <h2 class="jh-entity-heading"><span v-text="$t('meetupApp.follow.detail.title')">Follow</span> {{follow.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('meetupApp.follow.user')">User</span>
                    </dt>
                    <dd>
                        {{follow.user ? follow.user.login : ''}}
                    </dd>
                    <dt>
                        <span v-text="$t('meetupApp.follow.meetup')">Meetup</span>
                    </dt>
                    <dd>
                        <div v-if="follow.meetup">
                            <router-link :to="{name: 'MeetupView', params: {meetupId: follow.meetup.id}}">{{follow.meetup.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="follow.id" :to="{name: 'FollowEdit', params: {followId: follow.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./follow-details.component.ts">
</script>
