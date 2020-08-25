import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import MeetupService from '../meetup/meetup.service';
import { IMeetup } from '@/shared/model/meetup.model';

import AlertService from '@/shared/alert/alert.service';
import { IFollow, Follow } from '@/shared/model/follow.model';
import FollowService from './follow.service';

const validations: any = {
  follow: {}
};

@Component({
  validations
})
export default class FollowUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('followService') private followService: () => FollowService;
  public follow: IFollow = new Follow();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('meetupService') private meetupService: () => MeetupService;

  public meetups: IMeetup[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.followId) {
        vm.retrieveFollow(to.params.followId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.follow.id) {
      this.followService()
        .update(this.follow)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('meetupApp.follow.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.followService()
        .create(this.follow)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('meetupApp.follow.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveFollow(followId): void {
    this.followService()
      .find(followId)
      .then(res => {
        this.follow = res;
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
    this.meetupService()
      .retrieve()
      .then(res => {
        this.meetups = res.data;
      });
  }
}
