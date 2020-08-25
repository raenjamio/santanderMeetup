import { IMeetup } from '@/shared/model/meetup.model';
import { IUser } from '@/shared/model/user.model';

export interface INotification {
  id?: string;
  description?: string;
  topic?: string;
  meetup?: IMeetup;
  user?: IUser;
}

export class Notification implements INotification {
  constructor(public id?: string, public description?: string, public topic?: string, public meetup?: IMeetup, public user?: IUser) {}
}
