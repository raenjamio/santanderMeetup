<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('meetupApp.meetup.home.title')" id="meetup-heading">Meetups</span>
            <router-link :to="{name: 'MeetupCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-meetup">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('meetupApp.meetup.home.createLabel')">
                    Create a new Meetup
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && meetups && meetups.length === 0">
            <span v-text="$t('meetupApp.meetup.home.notFound')">No meetups found</span>
        </div>
        <div class="table-responsive" v-if="meetups && meetups.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('meetupApp.meetup.description')">Description</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('dateMeetup')"><span v-text="$t('meetupApp.meetup.dateMeetup')">Date Meetup</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('locationDescription')"><span v-text="$t('meetupApp.meetup.locationDescription')">Location Description</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('numberOfPeopleConfirmed')"><span v-text="$t('meetupApp.meetup.numberOfPeopleConfirmed')">Number Of People Confirmed</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="meetup in meetups"
                    :key="meetup.id">
                    <td>
                        <router-link :to="{name: 'MeetupView', params: {meetupId: meetup.id}}">{{meetup.id}}</router-link>
                    </td>
                    <td>{{meetup.description}}</td>
                    <td>{{meetup.dateMeetup | formatDate}}</td>
                    <td>{{meetup.locationDescription}}</td>
                    <td>{{meetup.numberOfPeopleConfirmed}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MeetupView', params: {meetupId: meetup.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'MeetupEdit', params: {meetupId: meetup.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(meetup)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="meetupApp.meetup.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-meetup-heading" v-bind:title="$t('meetupApp.meetup.delete.question')">Are you sure you want to delete this Meetup?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-meetup" v-text="$t('entity.action.delete')" v-on:click="removeMeetup()">Delete</button>
            </div>
        </b-modal>
        <div v-show="meetups && meetups.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./meetup.component.ts">
</script>
