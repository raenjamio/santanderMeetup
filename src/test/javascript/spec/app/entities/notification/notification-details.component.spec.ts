/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import NotificationDetailComponent from '@/entities/notification/notification-details.vue';
import NotificationClass from '@/entities/notification/notification-details.component';
import NotificationService from '@/entities/notification/notification.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Notification Management Detail Component', () => {
    let wrapper: Wrapper<NotificationClass>;
    let comp: NotificationClass;
    let notificationServiceStub: SinonStubbedInstance<NotificationService>;

    beforeEach(() => {
      notificationServiceStub = sinon.createStubInstance<NotificationService>(NotificationService);

      wrapper = shallowMount<NotificationClass>(NotificationDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { notificationService: () => notificationServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundNotification = { id: '123' };
        notificationServiceStub.find.resolves(foundNotification);

        // WHEN
        comp.retrieveNotification('123');
        await comp.$nextTick();

        // THEN
        expect(comp.notification).toBe(foundNotification);
      });
    });
  });
});
