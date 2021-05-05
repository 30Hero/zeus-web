import { defHttp } from '/@/utils/http/axios';
import {
  LoginRequest,
  LoginResponse,
  UserInfoResponse,
} from './model/userModel';

import { ErrorMessageMode } from '/@/utils/http/axios/types';

enum Api {
  Login = '/v1/login',
  GetUserInfo = '/v1/me',
  Logout = '/v1/logout',
}

/**
 * @description: user login api
 */
export function loginApi(params: LoginRequest, mode: ErrorMessageMode = 'modal') {
  return defHttp.post<LoginResponse>(
    {
      url: Api.Login,
      params,
    },
    {
      errorMessageMode: mode,
    }
  );
}

/**
 * @description: getUserInfo
 */
export function getUserInfo() {
  return defHttp.get<UserInfoResponse>({
    url: Api.GetUserInfo
  });
}

/**
 *
 * @description: logout
 */
export function logout() {
  return defHttp.post({
    url: Api.Logout
  });
}
