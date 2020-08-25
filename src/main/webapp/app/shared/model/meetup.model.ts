import { IUser } from '@/shared/model/user.model';

export interface IMeetup {
  id?: string;
  description?: string;
  dateMeetup?: Date;
  locationDescription?: string;
  numberOfPeopleConfirmed?: number;
  assistantsConfirmeds?: IUser[];
  enrolleds?: IUser[];
}

export class Meetup implements IMeetup {
  constructor(
    public id?: string,
    public description?: string,
    public dateMeetup?: Date,
    public locationDescription?: string,
    public numberOfPeopleConfirmed?: number,
    public assistantsConfirmeds?: IUser[],
    public enrolleds?: IUser[]
  ) {}
}
