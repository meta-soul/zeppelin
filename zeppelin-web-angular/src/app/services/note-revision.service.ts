import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, tap } from 'rxjs/operators';

import { NzMessageService } from 'ng-zorro-antd/message';

import { BaseUrlService } from './base-url.service';

@Injectable({
  providedIn: 'root'
})
export class RevisionService {
  getRevisionNote(noteId: string, revisionId: string) {
    return this.httpClient
      .post(`${this.baseUrlService.getRestApiBase()}/notebook/getByRevisionId/`,
      {
        noteId:noteId,
        revisionId:revisionId
      }).pipe(
        tap(
          data => {
            return data;
          },
          error => {
            this.nzMessageService.warning(error);
          }
        )
      );
  }

  constructor(
    private httpClient: HttpClient,
    private baseUrlService: BaseUrlService,
    private nzMessageService: NzMessageService
  ) {}
}
