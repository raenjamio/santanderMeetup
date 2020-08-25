import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMeetup } from '@/shared/model/meetup.model';
import MeetupService from './meetup.service';

@Component
export default class MeetupDetails extends Vue {
  @Inject('meetupService') private meetupService: () => MeetupService;
  public meetup: IMeetup = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.meetupId) {
        vm.retrieveMeetup(to.params.meetupId);
      }
    });
  }

  public retrieveMeetup(meetupId) {
    this.meetupService()
      .find(meetupId)
      .then(res => {
        this.meetup = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
