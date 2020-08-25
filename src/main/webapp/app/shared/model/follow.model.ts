import { IUser } from '@/shared/model/user.model';
import { IMeetup } from '@/shared/model/meetup.model';

export interface IFollow {
  id?: string;
  user?: IUser;
  meetup?: IMeetup;
}

export class Follow implements IFollow {
  constructor(public id?: string, public user?: IUser, public meetup?: IMeetup) {}
}
