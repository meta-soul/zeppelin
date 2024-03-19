export function getCurWorkSpace() {
  let workspace = localStorage.getItem('zeppelinWorkspace');
  return workspace || '';
}
export function extractLastSegment(path){
  const lastIndex = path.lastIndexOf('/');
  let part1;
  let part2;
  if (lastIndex !== -1){
    part1 = path.slice(0,lastIndex + 1);
    part2 = path.slice(lastIndex + 1);
  }else{
    part1 = path;
    part2 = path;
  }
  return {part1:part1,part2:part2}
}