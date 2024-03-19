/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageListener, MessageListenersManager } from '@zeppelin/core';
import { RevisionListItem } from '@zeppelin/sdk';
import { MessageService, RevisionService } from '@zeppelin/services';
@Component({
  selector: 'zeppelin-notebook-revisions-comparator',
  templateUrl: './revisions-comparator.component.html',
  styleUrls: ['./revisions-comparator.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NotebookRevisionsComparatorComponent extends MessageListenersManager implements OnInit {
  @Input() noteRevisions: RevisionListItem[] = [];
  noteId: string;
  revisionId: string;
  preRevisionOption: string;
  curRevisionOption: string;
  opts: {
    theme: 'vs-dark';
    automaticLayout: true;
    renderIndicators: true;
    ignoreTrimWhitespace: true;
    renderSideBySide: true;
  };
  constructor(
    public messageService: MessageService,
    private activatedRoute: ActivatedRoute,
    private revisionService: RevisionService,
    private cdr: ChangeDetectorRef
  ) {
    super(messageService);
    this.activatedRoute.params.subscribe(params => {
      this.noteId = params.noteId;
    });
  }
  diffValue = {
    preVersion: '',
    curVersion: ''
  };
  onOptionChange(val: string, version: string) {
    this.revisionService.getRevisionNote(this.noteId, val).subscribe(
      res => {
        this.diffValue = {
          ...this.diffValue,
          [version === 'pre' ? 'preVersion' : 'curVersion']: res
        };
        this.cdr.detectChanges();
      },
      error => {
        console.log('error', error);
      }
    );
  }
  getApproval(val) {
    const url = `${window.location.origin}/#/home/taskPublishing?noteId=${this.noteId}`;
    const newWindow = window.open(url, window.opener.name);
    newWindow.focus();
  }
  formatRevisionDate = function(unixTime) {
    const date = new Date(unixTime * 1000); // 将 UNIX 时间戳转换为毫秒
    const monthNames = [
      'January',
      'February',
      'March',
      'April',
      'May',
      'June',
      'July',
      'August',
      'September',
      'October',
      'November',
      'December'
    ];
    const month = monthNames[date.getMonth()];
    const day = date.getDate();
    const year = date.getFullYear();
    let hours = date.getHours();
    const minutes = ('0' + date.getMinutes()).slice(-2); // 保证分钟数为两位数
    const seconds = ('0' + date.getSeconds()).slice(-2); // 保证秒数为两位数
    const ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12; // 处理午夜 12 点的情况

    return `${month} ${day} ${year}, ${hours}:${minutes}:${seconds} ${ampm}`;
  };
  ngOnInit() {
    // this.messageService.listRevisionHistory(this.noteId);
  }
}
