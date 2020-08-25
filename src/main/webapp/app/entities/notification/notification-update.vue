<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="meetupApp.notification.home.createOrEditLabel" v-text="$t('meetupApp.notification.home.createOrEditLabel')">Create or edit a Notification</h2>
                <div>
                    <div class="form-group" v-if="notification.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="notification.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('meetupApp.notification.description')" for="notification-description">Description</label>
                        <input type="text" class="form-control" name="description" id="notification-description"
                            :class="{'valid': !$v.notification.description.$invalid, 'invalid': $v.notification.description.$invalid }" v-model="$v.notification.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('meetupApp.notification.topic')" for="notification-topic">Topic</label>
                        <input type="text" class="form-control" name="topic" id="notification-topic"
                            :class="{'valid': !$v.notification.topic.$invalid, 'invalid': $v.notification.topic.$invalid }" v-model="$v.notification.topic.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('meetupApp.notification.meetup')" for="notification-meetup">Meetup</label>
                        <select class="form-control" id="notification-meetup" name="meetup" v-model="notification.meetup">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="notification.meetup && meetupOption.id === notification.meetup.id ? notification.meetup : meetupOption" v-for="meetupOption in meetups" :key="meetupOption.id">{{meetupOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('meetupApp.notification.user')" for="notification-user">User</label>
                        <select class="form-control" id="notification-user" name="user" v-model="notification.user">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="notification.user && userOption.id === notification.user.id ? notification.user : userOption" v-for="userOption in users" :key="userOption.id">{{userOption.login}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.notification.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./notification-update.component.ts">
</script>
