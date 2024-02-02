/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

angular.module('zeppelinWebApp').service('websocketMsgSrv', WebsocketMessageService);

function WebsocketMessageService($rootScope, websocketEvents) {
  'ngInject';

  return {

    getHomeNote: function(workspace) {
      websocketEvents.sendNewEvent({op: 'GET_HOME_NOTE', workspace: workspace});
    },

    createNotebook: function(noteName, defaultInterpreterGroup, workspace) {
      websocketEvents.sendNewEvent({
        op: 'NEW_NOTE',
        data: {
          name: workspace + '/' + $rootScope.ticket.principal + '/' + noteName,
          defaultInterpreterGroup: defaultInterpreterGroup,
        },
        workspace: workspace,
      });
    },

    moveNoteToTrash: function(noteId, workspace) {
      websocketEvents.sendNewEvent({op: 'MOVE_NOTE_TO_TRASH', data: {id: noteId}, workspace: workspace});
    },

    moveFolderToTrash: function(folderPath, workspace) {
      websocketEvents.sendNewEvent({op: 'MOVE_FOLDER_TO_TRASH', data: {id: folderPath}, workspace: workspace});
    },

    restoreNote: function(noteId, workspace) {
      websocketEvents.sendNewEvent({op: 'RESTORE_NOTE', data: {id: noteId}, workspace: workspace});
    },

    restoreFolder: function(folderPath, workspace) {
      websocketEvents.sendNewEvent({op: 'RESTORE_FOLDER', data: {id: folderPath}, workspace: workspace});
    },

    restoreAll: function(workspace) {
      websocketEvents.sendNewEvent({op: 'RESTORE_ALL', workspace: workspace});
    },

    deleteNote: function(noteId, workspace) {
      websocketEvents.sendNewEvent({op: 'DEL_NOTE', data: {id: noteId}, workspace: workspace});
    },

    removeFolder: function(folderPath, workspace) {
      websocketEvents.sendNewEvent({op: 'REMOVE_FOLDER', data: {id: folderPath}, workspace: workspace});
    },

    emptyTrash: function(workspace) {
      websocketEvents.sendNewEvent({op: 'EMPTY_TRASH', workspace: workspace});
    },

    cloneNote: function(noteIdToClone, newNoteName, workspace) {
      websocketEvents.sendNewEvent({
        op: 'CLONE_NOTE',
        data: {
          id: noteIdToClone,
          name: workspace + '/' + $rootScope.ticket.principal + '/' + newNoteName,
        },
        workspace: workspace});
    },

    getNoteList: function(workspace) {
      websocketEvents.sendNewEvent({op: 'LIST_NOTES', workspace: workspace});
    },

    reloadAllNotesFromRepo: function(workspace) {
      websocketEvents.sendNewEvent({op: 'RELOAD_NOTES_FROM_REPO', workspace: workspace});
    },

    getNote: function(noteId, workspace) {
      websocketEvents.sendNewEvent({op: 'GET_NOTE', data: {id: noteId}, workspace: workspace});
    },

    reloadNote: function(noteId, workspace) {
      websocketEvents.sendNewEvent({op: 'RELOAD_NOTE', data: {id: noteId}, workspace: workspace});
    },

    updateNote: function(noteId, noteName, noteConfig, workspace) {
      websocketEvents.sendNewEvent({op: 'NOTE_UPDATE', data: {id: noteId, name: noteName, config: noteConfig},
        workspace: workspace});
    },

    updatePersonalizedMode: function(noteId, modeValue, workspace) {
      websocketEvents.sendNewEvent({op: 'UPDATE_PERSONALIZED_MODE', data: {id: noteId, personalized: modeValue},
        workspace: workspace});
    },

    renameNote: function(noteId, noteName, relative, workspace) {
      websocketEvents.sendNewEvent({op: 'NOTE_RENAME', data: {id: noteId, name: noteName, relative: relative},
        workspace: workspace});
    },

    renameFolder: function(folderId, folderPath, workspace) {
      websocketEvents.sendNewEvent({op: 'FOLDER_RENAME', data: {id: folderId, name: folderPath},
        workspace: workspace});
    },

    moveParagraph: function(paragraphId, newIndex, workspace) {
      websocketEvents.sendNewEvent({op: 'MOVE_PARAGRAPH', data: {id: paragraphId, index: newIndex},
        workspace: workspace});
    },

    insertParagraph: function(newIndex, workspace) {
      websocketEvents.sendNewEvent({op: 'INSERT_PARAGRAPH', data: {index: newIndex}, workspace: workspace});
    },

    copyParagraph: function(newIndex, paragraphTitle, paragraphData,
                            paragraphConfig, paragraphParams, workspace) {
      websocketEvents.sendNewEvent({
        op: 'COPY_PARAGRAPH',
        data: {
          index: newIndex,
          title: paragraphTitle,
          paragraph: paragraphData,
          config: paragraphConfig,
          params: paragraphParams,
        },
        workspace: workspace,
      });
    },

    updateAngularObject: function(noteId, paragraphId, name, value, interpreterGroupId, workspace) {
      websocketEvents.sendNewEvent({
        op: 'ANGULAR_OBJECT_UPDATED',
        data: {
          noteId: noteId,
          paragraphId: paragraphId,
          name: name,
          value: value,
          interpreterGroupId: interpreterGroupId,
        },
        workspace: workspace,
      });
    },

    clientBindAngularObject: function(noteId, name, value, paragraphId, workspace) {
      websocketEvents.sendNewEvent({
        op: 'ANGULAR_OBJECT_CLIENT_BIND',
        data: {
          noteId: noteId,
          name: name,
          value: value,
          paragraphId: paragraphId,
        },
        workspace: workspace,
      });
    },

    clientUnbindAngularObject: function(noteId, name, paragraphId, workspace) {
      websocketEvents.sendNewEvent({
        op: 'ANGULAR_OBJECT_CLIENT_UNBIND',
        data: {
          noteId: noteId,
          name: name,
          paragraphId: paragraphId,
        },
        workspace: workspace,
      });
    },

    cancelParagraphRun: function(paragraphId, workspace) {
      websocketEvents.sendNewEvent({op: 'CANCEL_PARAGRAPH', data: {id: paragraphId}, workspace: workspace});
    },

    paragraphExecutedBySpell: function(paragraphId, paragraphTitle,
                                        paragraphText, paragraphResultsMsg,
                                        paragraphStatus, paragraphErrorMessage,
                                        paragraphConfig, paragraphParams,
                                        paragraphDateStarted, paragraphDateFinished, workspace) {
      websocketEvents.sendNewEvent({
        op: 'PARAGRAPH_EXECUTED_BY_SPELL',
        data: {
          id: paragraphId,
          title: paragraphTitle,
          paragraph: paragraphText,
          results: {
            code: paragraphStatus,
            msg: paragraphResultsMsg.map((dataWithType) => {
              let serializedData = dataWithType.data;
              return {type: dataWithType.type, data: serializedData};
            }),
          },
          status: paragraphStatus,
          errorMessage: paragraphErrorMessage,
          config: paragraphConfig,
          params: paragraphParams,
          dateStarted: paragraphDateStarted,
          dateFinished: paragraphDateFinished,
        },
        workspace: workspace,
      });
    },

    runParagraph: function(paragraphId, paragraphTitle, paragraphData, paragraphConfig, paragraphParams, workspace) {
      // short circuit update paragraph status for immediate visual feedback without waiting for server response
      $rootScope.$broadcast('updateStatus', {
        id: paragraphId,
        status: 'PENDING',
      });

      // send message to server
      websocketEvents.sendNewEvent({
        op: 'RUN_PARAGRAPH',
        data: {
          id: paragraphId,
          title: paragraphTitle,
          paragraph: paragraphData,
          config: paragraphConfig,
          params: paragraphParams,
        },
        workspace: workspace,
      });
    },

    runAllParagraphs: function(noteId, paragraphs, workspace) {
      // short circuit update paragraph status for immediate visual feedback without waiting for server response
      paragraphs.forEach((p) => {
        $rootScope.$broadcast('updateStatus', {
          id: p.id,
          status: 'PENDING',
        });
      });

      // send message to server
      websocketEvents.sendNewEvent({
        op: 'RUN_ALL_PARAGRAPHS',
        data: {
          noteId: noteId,
          paragraphs: JSON.stringify(paragraphs),
        },
        workspace: workspace,
      });
    },

    removeParagraph: function(paragraphId, workspace) {
      websocketEvents.sendNewEvent({op: 'PARAGRAPH_REMOVE', data: {id: paragraphId}, workspace: workspace});
    },

    clearParagraphOutput: function(paragraphId, workspace) {
      websocketEvents.sendNewEvent({op: 'PARAGRAPH_CLEAR_OUTPUT', data: {id: paragraphId}, workspace: workspace});
    },

    clearAllParagraphOutput: function(noteId, workspace) {
      websocketEvents.sendNewEvent({op: 'PARAGRAPH_CLEAR_ALL_OUTPUT', data: {id: noteId}, workspace: workspace});
    },

    completion: function(paragraphId, buf, cursor, workspace) {
      websocketEvents.sendNewEvent({
        op: 'COMPLETION',
        data: {
          id: paragraphId,
          buf: buf,
          cursor: cursor,
        },
        workspace: workspace,
      });
    },

    commitParagraph: function(paragraphId, paragraphTitle, paragraphData, paragraphConfig,
      paragraphParams, noteId, workspace) {
      return websocketEvents.sendNewEvent({
        op: 'COMMIT_PARAGRAPH',
        data: {
          id: paragraphId,
          noteId: noteId,
          title: paragraphTitle,
          paragraph: paragraphData,
          config: paragraphConfig,
          params: paragraphParams,
        },
        workspace: workspace,
      });
    },

    patchParagraph: function(paragraphId, noteId, patch, workspace) {
      // javascript add "," if change contains several patches
      // but java library requires patch list without ","
      patch = patch.replace(/,@@/g, '@@');
      return websocketEvents.sendNewEvent({
        op: 'PATCH_PARAGRAPH',
        data: {
          id: paragraphId,
          noteId: noteId,
          patch: patch,
        },
        workspace: workspace,
      });
    },

    importNote: function(note, workspace) {
      websocketEvents.sendNewEvent({
        op: 'IMPORT_NOTE',
        data: {
          note: note,
        },
        workspace: workspace,
      });
    },

    convertNote: function(noteId, noteName, workspace) {
      websocketEvents.sendNewEvent({
        op: 'CONVERT_NOTE_NBFORMAT',
        data: {
          noteId: noteId,
          noteName: noteName,
        },
        workspace: workspace,
      });
    },

    checkpointNote: function(noteId, commitMessage, workspace) {
      websocketEvents.sendNewEvent({
        op: 'CHECKPOINT_NOTE',
        data: {
          noteId: noteId,
          commitMessage: commitMessage,
        },
        workspace: workspace,
      });
    },

    setNoteRevision: function(noteId, revisionId, workspace) {
      websocketEvents.sendNewEvent({
        op: 'SET_NOTE_REVISION',
        data: {
          noteId: noteId,
          revisionId: revisionId,
        },
        workspace: workspace,
      });
    },

    listRevisionHistory: function(noteId, workspace) {
      websocketEvents.sendNewEvent({
        op: 'LIST_REVISION_HISTORY',
        data: {
          noteId: noteId,
        },
        workspace: workspace,
      });
    },

    getNoteByRevision: function(noteId, revisionId, workspace) {
      websocketEvents.sendNewEvent({
        op: 'NOTE_REVISION',
        data: {
          noteId: noteId,
          revisionId: revisionId,
        },
        workspace: workspace,
      });
    },

    getNoteByRevisionForCompare: function(noteId, revisionId, position, workspace) {
      websocketEvents.sendNewEvent({
        op: 'NOTE_REVISION_FOR_COMPARE',
        data: {
          noteId: noteId,
          revisionId: revisionId,
          position: position,
        },
        workspace: workspace,
      });
    },

    getEditorSetting: function(paragraphId, pararaphText, workspace) {
      websocketEvents.sendNewEvent({
        op: 'EDITOR_SETTING',
        data: {
          paragraphId: paragraphId,
          paragraphText: pararaphText,
        },
        workspace: workspace,
      });
    },

    isConnected: function() {
      return websocketEvents.isConnected();
    },

    getJobs: function(workspace) {
      websocketEvents.sendNewEvent({op: 'LIST_NOTE_JOBS', workspace: workspace});
    },

    disconnectJobEvent: function(workspace) {
      websocketEvents.sendNewEvent({op: 'UNSUBSCRIBE_UPDATE_NOTE_JOBS', workspace: workspace});
    },

    getUpdateNoteJobsList: function(lastUpdateServerUnixTime, workspace) {
      websocketEvents.sendNewEvent(
        {op: 'LIST_UPDATE_NOTE_JOBS', data: {lastUpdateUnixTime: lastUpdateServerUnixTime * 1}, workspace: workspace}
      );
    },

    getInterpreterBindings: function(noteId, workspace) {
      websocketEvents.sendNewEvent({op: 'GET_INTERPRETER_BINDINGS', data: {noteId: noteId}, workspace: workspace});
    },

    saveInterpreterBindings: function(noteId, selectedSettingIds, workspace) {
      websocketEvents.sendNewEvent({op: 'SAVE_INTERPRETER_BINDINGS',
        data: {noteId: noteId, selectedSettingIds: selectedSettingIds}, workspace: workspace});
    },

    listConfigurations: function(workspace) {
      websocketEvents.sendNewEvent({op: 'LIST_CONFIGURATIONS', workspace: workspace});
    },

    getInterpreterSettings: function(workspace) {
      websocketEvents.sendNewEvent({op: 'GET_INTERPRETER_SETTINGS', workspace: workspace});
    },

    saveNoteForms: function(note, workspace) {
      websocketEvents.sendNewEvent({op: 'SAVE_NOTE_FORMS',
        data: {
          noteId: note.id,
          noteParams: note.noteParams,
        },
        workspace: workspace,
      });
    },

    removeNoteForms: function(note, formName, workspace) {
      websocketEvents.sendNewEvent({op: 'REMOVE_NOTE_FORMS',
        data: {
          noteId: note.id,
          formName: formName,
        },
        workspace: workspace,
      });
    },

  };
}
