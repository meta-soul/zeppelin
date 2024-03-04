import { Component, ElementRef, Input, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
// import * as monaco from 'monaco-editor';
import * as monaco from 'monaco-editor/esm/vs/editor/editor.api';
// Import the specific module for SQL language support
import 'monaco-editor/esm/vs/basic-languages/sql/sql.contribution';

monaco.languages.register({
  id: 'sql',
  extensions: ['.sql'],
  aliases: ['SQL', 'sql'],
  mimetypes: ['application/sql']
});
// monaco.editor.remeasureFonts()
@Component({
  selector: 'zeppelin-revision-diff',
  templateUrl: './revision-diff.component.html',
  styleUrls: ['./revision-diff.component.less']
})
export class RevisionDiffComponent implements OnChanges {
  @ViewChild('revisionDiffRef', { static: true }) revisionDiffRef!: ElementRef;
  @Input() focus = false;
  @Input() opts: monaco.editor.IDiffEditorConstructionOptions = {
    theme: 'vs-dark',
    automaticLayout: true,
    renderIndicators: true,
    ignoreTrimWhitespace: true,
    renderSideBySide: true
    // autoIndent: true,
    // readOnly: true,
  };
  @Input() diffValue: { preVersion: string; curVersion: string } = { preVersion: '', curVersion: '' };
  @Input() language = '';
  @Input() cursorPosition: monaco.Position | undefined;
  private editor: monaco.editor.IStandaloneDiffEditor | undefined;

  setPosition(position: monaco.Position): void {
    if (this.editor) {
      // 设置光标位置
      this.editor.setPosition(position);
    }
  }
  ngOnChanges(changes: SimpleChanges): void {
    // const { text, interpreterBindings, language, readOnly, focus, lineNumbers, fontSize } = changes;
    if (changes.cursorPosition && this.cursorPosition && this.editor) {
      this.setPosition(this.cursorPosition);
    }
    if (changes.diffValue) {
      this.init();
    }
  }

  init(): void {
    const language = this.language || 'text/plain';
    const originalModel = monaco.editor.createModel(this.diffValue.preVersion, language);
    const modifiedModel = monaco.editor.createModel(this.diffValue.curVersion, language);
    if (this.editor) {
      this.editor.dispose(); // Dispose previous editor instance
    }
    this.editor = monaco.editor.createDiffEditor(this.revisionDiffRef.nativeElement, this.opts);
    this.editor.setModel({
      original: originalModel,
      modified: modifiedModel
    });
    // this.initEditorFocus();
  }
}
