<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('meetupApp.notification.home.title')" id="notification-heading">Notifications</span>
            <router-link :to="{name: 'NotificationCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-notification">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('meetupApp.notification.home.createLabel')">
                    Create a new Notification
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
        <div class="alert alert-warning" v-if="!isFetching && notifications && notifications.length === 0">
            <span v-text="$t('meetupApp.notification.home.notFound')">No notifications found</span>
        </div>
        <div class="table-responsive" v-if="notifications && notifications.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('meetupApp.notification.description')">Description</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('topic')"><span v-text="$t('meetupApp.notification.topic')">Topic</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('meetup.id')"><span v-text="$t('meetupApp.notification.meetup')">Meetup</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('user.login')"><span v-text="$t('meetupApp.notification.user')">User</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="notification in notifications"
                    :key="notification.id">
                    <td>
                        <router-link :to="{name: 'NotificationView', params: {notificationId: notification.id}}">{{notification.id}}</router-link>
                    </td>
                    <td>{{notification.description}}</td>
                    <td>{{notification.topic}}</td>
                    <td>
                        <div v-if="notification.meetup">
                            <router-link :to="{name: 'MeetupView', params: {meetupId: notification.meetup.id}}">{{notification.meetup.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        {{notification.user ? notification.user.login : ''}}
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'NotificationView', params: {notificationId: notification.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'NotificationEdit', params: {notificationId: notification.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(notification)"
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
            <span slot="modal-title"><span id="meetupApp.notification.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-notification-heading" v-bind:title="$t('meetupApp.notification.delete.question')">Are you sure you want to delete this Notification?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-notification" v-text="$t('entity.action.delete')" v-on:click="removeNotification()">Delete</button>
            </div>
        </b-modal>
        <div v-show="notifications && notifications.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./notification.component.ts">
</script>
