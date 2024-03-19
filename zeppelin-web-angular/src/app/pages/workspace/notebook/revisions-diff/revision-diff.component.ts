import { Component, ElementRef, Input, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
// Import the specific module for SQL language support
import 'monaco-editor/esm/vs/basic-languages/sql/sql.contribution';
// import * as monaco from 'monaco-editor';
import * as monaco from 'monaco-editor/esm/vs/editor/editor.api';

monaco.languages.register({
  id: 'sql',
  extensions: ['.sql'],
  aliases: ['SQL', 'sql'],
  mimetypes: ['application/sql']
});
@Component({
  selector: 'zeppelin-revision-diff',
  templateUrl: './revision-diff.component.html',
  styleUrls: ['./revision-diff.component.less']
})
export class RevisionDiffComponent implements OnChanges {
  @ViewChild('revisionDiffRef', { static: true }) revisionDiffRef!: ElementRef;
  @Input() diffValue: { preVersion: string; curVersion: string } = { preVersion: '', curVersion: '' };
  @Input() language = '';
  @Input() opts: monaco.editor.IDiffEditorConstructionOptions = {
    theme: 'vs-dark',
    autoIndent: true,
    readOnly:true,
  };
  private editor: monaco.editor.IStandaloneDiffEditor | undefined;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.diffValue) {
      this.init();
    }
  }
  init(): void {
    const language = this.language || 'text/plain';
    const opts = this.opts;
    const originalModel = monaco.editor.createModel(this.diffValue.preVersion, language);
    const modifiedModel = monaco.editor.createModel(this.diffValue.curVersion, language);
    if (!this.editor) {
      this.editor = monaco.editor.createDiffEditor(this.revisionDiffRef.nativeElement, opts);
    }
    this.editor.setModel({
      original: originalModel,
      modified: modifiedModel
    });
  }
}
