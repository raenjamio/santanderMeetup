<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="meetupApp.meetup.home.createOrEditLabel" v-text="$t('meetupApp.meetup.home.createOrEditLabel')">Create or edit a Meetup</h2>
                <div>
                    <div class="form-group" v-if="meetup.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="meetup.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('meetupApp.meetup.description')" for="meetup-description">Description</label>
                        <input type="text" class="form-control" name="description" id="meetup-description"
                            :class="{'valid': !$v.meetup.description.$invalid, 'invalid': $v.meetup.description.$invalid }" v-model="$v.meetup.description.$model"  required/>
                        <div v-if="$v.meetup.description.$anyDirty && $v.meetup.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.meetup.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.meetup.description.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 100 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('meetupApp.meetup.dateMeetup')" for="meetup-dateMeetup">Date Meetup</label>
                        <div class="d-flex">
                            <input id="meetup-dateMeetup" type="datetime-local" class="form-control" name="dateMeetup" :class="{'valid': !$v.meetup.dateMeetup.$invalid, 'invalid': $v.meetup.dateMeetup.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.meetup.dateMeetup.$model)"
                            @change="updateZonedDateTimeField('dateMeetup', $event)"/>
                        </div>
                        <div v-if="$v.meetup.dateMeetup.$anyDirty && $v.meetup.dateMeetup.$invalid">
                            <small class="form-text text-danger" v-if="!$v.meetup.dateMeetup.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.meetup.dateMeetup.ZonedDateTimelocal" v-text="$t('entity.validation.ZonedDateTimelocal')">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('meetupApp.meetup.locationDescription')" for="meetup-locationDescription">Location Description</label>
                        <input type="text" class="form-control" name="locationDescription" id="meetup-locationDescription"
                            :class="{'valid': !$v.meetup.locationDescription.$invalid, 'invalid': $v.meetup.locationDescription.$invalid }" v-model="$v.meetup.locationDescription.$model" />
                    </div>
                    <div class="form-group">
                        <label v-text="$t('meetupApp.meetup.assistantsConfirmed')" for="meetup-assistantsConfirmed">Assistants Confirmed</label>
                        <select class="form-control" id="meetup-assistantsConfirmed" multiple name="assistantsConfirmed" v-model="meetup.assistantsConfirmeds">
                            <option v-bind:value="getSelected(meetup.assistantsConfirmeds, userOption)" v-for="userOption in users" :key="userOption.id">{{userOption.login}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label v-text="$t('meetupApp.meetup.enrolleds')" for="meetup-enrolleds">Enrolleds</label>
                        <select class="form-control" id="meetup-enrolleds" multiple name="enrolleds" v-model="meetup.enrolleds">
                            <option v-bind:value="getSelected(meetup.enrolleds, userOption)" v-for="userOption in users" :key="userOption.id">{{userOption.login}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.meetup.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./meetup-update.component.ts">
</script>
