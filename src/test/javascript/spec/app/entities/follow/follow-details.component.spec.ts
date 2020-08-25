/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import FollowDetailComponent from '@/entities/follow/follow-details.vue';
import FollowClass from '@/entities/follow/follow-details.component';
import FollowService from '@/entities/follow/follow.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Follow Management Detail Component', () => {
    let wrapper: Wrapper<FollowClass>;
    let comp: FollowClass;
    let followServiceStub: SinonStubbedInstance<FollowService>;

    beforeEach(() => {
      followServiceStub = sinon.createStubInstance<FollowService>(FollowService);

      wrapper = shallowMount<FollowClass>(FollowDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { followService: () => followServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFollow = { id: '123' };
        followServiceStub.find.resolves(foundFollow);

        // WHEN
        comp.retrieveFollow('123');
        await comp.$nextTick();

        // THEN
        expect(comp.follow).toBe(foundFollow);
      });
    });
  });
});
