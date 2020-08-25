import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import UserService from '@/admin/user-management/user-management.service';

import AlertService from '@/shared/alert/alert.service';
import { IMeetup, Meetup } from '@/shared/model/meetup.model';
import MeetupService from './meetup.service';

const validations: any = {
  meetup: {
    description: {
      required,
      maxLength: maxLength(100)
    },
    dateMeetup: {
      required
    },
    locationDescription: {},
    numberOfPeopleConfirmed: {}
  }
};

@Component({
  validations
})
export default class MeetupUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('meetupService') private meetupService: () => MeetupService;
  public meetup: IMeetup = new Meetup();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.meetupId) {
        vm.retrieveMeetup(to.params.meetupId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.meetup.assistantsConfirmeds = [];
    this.meetup.enrolleds = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.meetup.id) {
      this.meetup.numberOfPeopleConfirmed = 0;
      this.meetupService()
        .update(this.meetup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('meetupApp.meetup.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.meetupService()
        .create(this.meetup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('meetupApp.meetup.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.meetup[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.meetup[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.meetup[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.meetup[field] = null;
    }
  }

  public retrieveMeetup(meetupId): void {
    this.meetupService()
      .find(meetupId)
      .then(res => {
        res.dateMeetup = new Date(res.dateMeetup);
        this.meetup = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
