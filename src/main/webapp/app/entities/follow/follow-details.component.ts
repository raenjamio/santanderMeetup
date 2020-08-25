import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFollow } from '@/shared/model/follow.model';
import FollowService from './follow.service';

@Component
export default class FollowDetails extends Vue {
  @Inject('followService') private followService: () => FollowService;
  public follow: IFollow = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.followId) {
        vm.retrieveFollow(to.params.followId);
      }
    });
  }

  public retrieveFollow(followId) {
    this.followService()
      .find(followId)
      .then(res => {
        this.follow = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
