import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IFollow } from '@/shared/model/follow.model';

const baseApiUrl = 'api/follows';

export default class FollowService {
  public find(id: string): Promise<IFollow> {
    return new Promise<IFollow>(resolve => {
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

  public create(entity: IFollow): Promise<IFollow> {
    return new Promise<IFollow>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IFollow): Promise<IFollow> {
    return new Promise<IFollow>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
