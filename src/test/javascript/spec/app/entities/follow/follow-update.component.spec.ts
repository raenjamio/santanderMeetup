/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import FollowUpdateComponent from '@/entities/follow/follow-update.vue';
import FollowClass from '@/entities/follow/follow-update.component';
import FollowService from '@/entities/follow/follow.service';

import UserService from '@/admin/user-management/user-management.service';

import MeetupService from '@/entities/meetup/meetup.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Follow Management Update Component', () => {
    let wrapper: Wrapper<FollowClass>;
    let comp: FollowClass;
    let followServiceStub: SinonStubbedInstance<FollowService>;

    beforeEach(() => {
      followServiceStub = sinon.createStubInstance<FollowService>(FollowService);

      wrapper = shallowMount<FollowClass>(FollowUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          followService: () => followServiceStub,

          userService: () => new UserService(),

          meetupService: () => new MeetupService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.follow = entity;
        followServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(followServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.follow = entity;
        followServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(followServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
