import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import MeetupService from '../meetup/meetup.service';
import { IMeetup } from '@/shared/model/meetup.model';

import UserService from '@/admin/user-management/user-management.service';

import AlertService from '@/shared/alert/alert.service';
import { INotification, Notification } from '@/shared/model/notification.model';
import NotificationService from './notification.service';

const validations: any = {
  notification: {
    description: {},
    topic: {}
  }
};

@Component({
  validations
})
export default class NotificationUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('notificationService') private notificationService: () => NotificationService;
  public notification: INotification = new Notification();

  @Inject('meetupService') private meetupService: () => MeetupService;

  public meetups: IMeetup[] = [];

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.notificationId) {
        vm.retrieveNotification(to.params.notificationId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.notification.id) {
      this.notificationService()
        .update(this.notification)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('meetupApp.notification.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.notificationService()
        .create(this.notification)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('meetupApp.notification.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveNotification(notificationId): void {
    this.notificationService()
      .find(notificationId)
      .then(res => {
        this.notification = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.meetupService()
      .retrieve()
      .then(res => {
        this.meetups = res.data;
      });
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }
}
