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
import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';

@Component({
  selector: 'zeppelin-notebook-revisions-comparator',
  templateUrl: './revisions-comparator.component.html',
  styleUrls: ['./revisions-comparator.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NotebookRevisionsComparatorComponent implements OnInit {
  diffValue = {
    preVersion: 'show databases;\nshow tables from test_cdc;\nselect * from test_cdc.video_game_sales;',
    curVersion: 'show databasess;\nshow tables from;\nshow databases;'
  } 
  tableData: any[] = [
    { date: '2024-01-01', owner: 'John Doe', status: '未审批', selected: false },
    { date: '2024-02-01', owner: 'Jane Doe', status: '已审批', selected: false },
    { date: '2024-03-01', owner: 'Alice', status: '已审批', selected: false }
  ];

  selectedVersions: any[] = [];

  // constructor() {}

  onRowClick(item: any): void {
    if (this.selectedVersions.length < 2) {
      item.selected = !item.selected;
      if (item.selected) {
        this.selectedVersions.push(item);
      } else {
        this.selectedVersions = this.selectedVersions.filter(v => v !== item);
      }
    } else {
      if (item.selected) {
        item.selected = !item.selected;
        this.selectedVersions = this.selectedVersions.filter(v => v !== item);
      } else {
        alert('最多只能对比当前两个版本');
      }
    }
  }

  approve(item: any): void {
    // Logic for approving item
  }

  modifyStatus(item: any): void {
    // Logic for modifying status of item
  } 
  ngOnInit() {}
}
