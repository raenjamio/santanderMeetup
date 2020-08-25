import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate' // for vue-router 2.2+
]);
import Router from 'vue-router';
const Home = () => import('../core/home/home.vue');
const Error = () => import('../core/error/error.vue');
const Register = () => import('../account/register/register.vue');
const Activate = () => import('../account/activate/activate.vue');
const ResetPasswordInit = () => import('../account/reset-password/init/reset-password-init.vue');
const ResetPasswordFinish = () => import('../account/reset-password/finish/reset-password-finish.vue');
const ChangePassword = () => import('../account/change-password/change-password.vue');
const Settings = () => import('../account/settings/settings.vue');
const JhiUserManagementComponent = () => import('../admin/user-management/user-management.vue');
const JhiUserManagementViewComponent = () => import('../admin/user-management/user-management-view.vue');
const JhiUserManagementEditComponent = () => import('../admin/user-management/user-management-edit.vue');
const Sessions = () => import('../account/sessions/sessions.vue');
const JhiConfigurationComponent = () => import('../admin/configuration/configuration.vue');
const JhiDocsComponent = () => import('../admin/docs/docs.vue');
const JhiHealthComponent = () => import('../admin/health/health.vue');
const JhiLogsComponent = () => import('../admin/logs/logs.vue');
const JhiAuditsComponent = () => import('../admin/audits/audits.vue');
const JhiMetricsComponent = () => import('../admin/metrics/metrics.vue');
/* tslint:disable */
// prettier-ignore
const Meetup = () => import('../entities/meetup/meetup.vue');
// prettier-ignore
const MeetupUpdate = () => import('../entities/meetup/meetup-update.vue');
// prettier-ignore
const MeetupDetails = () => import('../entities/meetup/meetup-details.vue');
// prettier-ignore
const Follow = () => import('../entities/follow/follow.vue');
// prettier-ignore
const FollowUpdate = () => import('../entities/follow/follow-update.vue');
// prettier-ignore
const FollowDetails = () => import('../entities/follow/follow-details.vue');
// prettier-ignore
const Notification = () => import('../entities/notification/notification.vue');
// prettier-ignore
const NotificationUpdate = () => import('../entities/notification/notification-update.vue');
// prettier-ignore
const NotificationDetails = () => import('../entities/notification/notification-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

Vue.use(Router);

// prettier-ignore
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      meta: { error403: true }
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      meta: { error404: true }
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/activate',
      name: 'Activate',
      component: Activate
    },
    {
      path: '/reset/request',
      name: 'ResetPasswordInit',
      component: ResetPasswordInit
    },
    {
      path: '/reset/finish',
      name: 'ResetPasswordFinish',
      component: ResetPasswordFinish
    },
    {
      path: '/account/password',
      name: 'ChangePassword',
      component: ChangePassword,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/account/sessions',
      name: 'Sessions',
      component: Sessions,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/account/settings',
      name: 'Settings',
      component: Settings,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/admin/user-management',
      name: 'JhiUser',
      component: JhiUserManagementComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/new',
      name: 'JhiUserCreate',
      component: JhiUserManagementEditComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/:userId/edit',
      name: 'JhiUserEdit',
      component: JhiUserManagementEditComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/:userId/view',
      name: 'JhiUserView',
      component: JhiUserManagementViewComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/docs',
      name: 'JhiDocsComponent',
      component: JhiDocsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/audits',
      name: 'JhiAuditsComponent',
      component: JhiAuditsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-health',
      name: 'JhiHealthComponent',
      component: JhiHealthComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/logs',
      name: 'JhiLogsComponent',
      component: JhiLogsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-metrics',
      name: 'JhiMetricsComponent',
      component: JhiMetricsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-configuration',
      name: 'JhiConfigurationComponent',
      component: JhiConfigurationComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    }
    ,
    {
      path: '/entity/meetup',
      name: 'Meetup',
      component: Meetup,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/meetup/new',
      name: 'MeetupCreate',
      component: MeetupUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/meetup/:meetupId/edit',
      name: 'MeetupEdit',
      component: MeetupUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/meetup/:meetupId/view',
      name: 'MeetupView',
      component: MeetupDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/follow',
      name: 'Follow',
      component: Follow,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/follow/new',
      name: 'FollowCreate',
      component: FollowUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/follow/:followId/edit',
      name: 'FollowEdit',
      component: FollowUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/follow/:followId/view',
      name: 'FollowView',
      component: FollowDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/notification',
      name: 'Notification',
      component: Notification,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/notification/new',
      name: 'NotificationCreate',
      component: NotificationUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/notification/:notificationId/edit',
      name: 'NotificationEdit',
      component: NotificationUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/notification/:notificationId/view',
      name: 'NotificationView',
      component: NotificationDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ]
});
