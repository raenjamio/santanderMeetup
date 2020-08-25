/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MeetupDetailComponent from '@/entities/meetup/meetup-details.vue';
import MeetupClass from '@/entities/meetup/meetup-details.component';
import MeetupService from '@/entities/meetup/meetup.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Meetup Management Detail Component', () => {
    let wrapper: Wrapper<MeetupClass>;
    let comp: MeetupClass;
    let meetupServiceStub: SinonStubbedInstance<MeetupService>;

    beforeEach(() => {
      meetupServiceStub = sinon.createStubInstance<MeetupService>(MeetupService);

      wrapper = shallowMount<MeetupClass>(MeetupDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { meetupService: () => meetupServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMeetup = { id: '123' };
        meetupServiceStub.find.resolves(foundMeetup);

        // WHEN
        comp.retrieveMeetup('123');
        await comp.$nextTick();

        // THEN
        expect(comp.meetup).toBe(foundMeetup);
      });
    });
  });
});
