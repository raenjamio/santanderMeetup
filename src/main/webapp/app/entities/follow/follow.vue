<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('meetupApp.follow.home.title')" id="follow-heading">Follows</span>
            <router-link :to="{name: 'FollowCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-follow">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('meetupApp.follow.home.createLabel')">
                    Create a new Follow
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
        <div class="alert alert-warning" v-if="!isFetching && follows && follows.length === 0">
            <span v-text="$t('meetupApp.follow.home.notFound')">No follows found</span>
        </div>
        <div class="table-responsive" v-if="follows && follows.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('user.login')"><span v-text="$t('meetupApp.follow.user')">User</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('meetup.id')"><span v-text="$t('meetupApp.follow.meetup')">Meetup</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="follow in follows"
                    :key="follow.id">
                    <td>
                        <router-link :to="{name: 'FollowView', params: {followId: follow.id}}">{{follow.id}}</router-link>
                    </td>
                    <td>
                        {{follow.user ? follow.user.login : ''}}
                    </td>
                    <td>
                        <div v-if="follow.meetup">
                            <router-link :to="{name: 'MeetupView', params: {meetupId: follow.meetup.id}}">{{follow.meetup.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'FollowView', params: {followId: follow.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'FollowEdit', params: {followId: follow.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(follow)"
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
            <span slot="modal-title"><span id="meetupApp.follow.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-follow-heading" v-bind:title="$t('meetupApp.follow.delete.question')">Are you sure you want to delete this Follow?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-follow" v-text="$t('entity.action.delete')" v-on:click="removeFollow()">Delete</button>
            </div>
        </b-modal>
        <div v-show="follows && follows.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./follow.component.ts">
</script>
