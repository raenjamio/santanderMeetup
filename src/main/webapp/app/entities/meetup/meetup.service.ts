import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IMeetup } from '@/shared/model/meetup.model';

const baseApiUrl = 'api/meetups';

export default class MeetupService {
  public find(id: string): Promise<IMeetup> {
    return new Promise<IMeetup>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public delete(id: string): Promise<any> {
    return new Promise<any>(resolve => {
      axios.delete(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public create(entity: IMeetup): Promise<IMeetup> {
    return new Promise<IMeetup>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IMeetup): Promise<IMeetup> {
    return new Promise<IMeetup>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
