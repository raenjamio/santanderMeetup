<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('meetupApp.meetup.home.title')" id="meetup-heading">Meetups</span>
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
                    <td class="text-right">
                        <div class="btn-group">
                            <b-button v-on:click="beerCount(meetup)"
                                   variant="danger"
                                   class="btn btn-sm details">
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.beer.count')">Beer count</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
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

<script lang="ts" src="./beer.component.ts">
</script>
