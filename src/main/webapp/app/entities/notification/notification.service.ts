import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { INotification } from '@/shared/model/notification.model';

const baseApiUrl = 'api/notifications';

export default class NotificationService {
  public find(id: string): Promise<INotification> {
    return new Promise<INotification>(resolve => {
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

  public create(entity: INotification): Promise<INotification> {
    return new Promise<INotification>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: INotification): Promise<INotification> {
    return new Promise<INotification>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
