import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IVisit } from '@/shared/model/visit.model';
import { Store } from 'vuex';

import { EventBus } from '../../event-bus.js';

const baseApiUrl = 'api/visits';

export default class VisitService {
  constructor(private store: Store<any>) {}

  public waitedVisits: IVisit[] = [];

  public find(id: number): Promise<IVisit> {
    return new Promise<IVisit>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveByPatientId(patientId, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + '/patientId/' + patientId + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveWaited(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + '/waited')
        .then(res => {
          EventBus.$emit('waitedVisits', res.data);
          //this.store.commit('waitedVisits', res.data);
          // this.waitedVisits = res.data;
          console.log(this.waitedVisits, 'service');

          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveByDate(date, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + '/date/' + date + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IVisit): Promise<IVisit> {
    return new Promise<IVisit>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IVisit): Promise<IVisit> {
    return new Promise<IVisit>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: IVisit): Promise<IVisit> {
    return new Promise<IVisit>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
