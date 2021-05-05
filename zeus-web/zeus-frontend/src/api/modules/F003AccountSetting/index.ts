import { F003UpdateUserBasicInfoRequest } from './model';
import { defHttp } from '/@/utils/http/axios';

enum Api {
  InitScreen = '/v1/f003/initScreen',
  UpdateBasicInfo = '/v1/f003/updateBasicInfo'
}

export const updateBasicInfo = (params: F003UpdateUserBasicInfoRequest) => {
  return defHttp.put(
    { url: Api.UpdateBasicInfo, params },
  );
};
