/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import MeetupService from '@/entities/meetup/meetup.service';
import { Meetup } from '@/shared/model/meetup.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('Meetup Service', () => {
    let service: MeetupService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new MeetupService();
      currentDate = new Date();

      elemDefault = new Meetup('ID', 'AAAAAAA', currentDate, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dateMeetup: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find('123').then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a Meetup', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            dateMeetup: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateMeetup: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a Meetup', async () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            dateMeetup: format(currentDate, DATE_TIME_FORMAT),
            locationDescription: 'BBBBBB',
            numberOfPeopleConfirmed: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateMeetup: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of Meetup', async () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            dateMeetup: format(currentDate, DATE_TIME_FORMAT),
            locationDescription: 'BBBBBB',
            numberOfPeopleConfirmed: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateMeetup: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a Meetup', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete('123').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
