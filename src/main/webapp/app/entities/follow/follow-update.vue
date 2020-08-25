<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="meetupApp.follow.home.createOrEditLabel" v-text="$t('meetupApp.follow.home.createOrEditLabel')">Create or edit a Follow</h2>
                <div>
                    <div class="form-group" v-if="follow.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="follow.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('meetupApp.follow.user')" for="follow-user">User</label>
                        <select class="form-control" id="follow-user" name="user" v-model="follow.user">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="follow.user && userOption.id === follow.user.id ? follow.user : userOption" v-for="userOption in users" :key="userOption.id">{{userOption.login}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('meetupApp.follow.meetup')" for="follow-meetup">Meetup</label>
                        <select class="form-control" id="follow-meetup" name="meetup" v-model="follow.meetup">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="follow.meetup && meetupOption.id === follow.meetup.id ? follow.meetup : meetupOption" v-for="meetupOption in meetups" :key="meetupOption.id">{{meetupOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.follow.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./follow-update.component.ts">
</script>
