export function getCurWorkSpace() {
  let workspace = localStorage.getItem('zeppelinWorkspace');
  return workspace || '';
}
