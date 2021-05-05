import { defHttp } from '/@/utils/http/axios';
import { F002CreateOrUpdateAccountRequest, F002DownloadExcelResponse, F002InitScreenResponse, F002SearchUserRequest, F002SearchUserResponse } from './model';
import { SearchResult } from '../../model/baseModel';

enum Api {
  InitScreen = '/v1/f002/initScreen',
  Search = '/v1/f002/search',
  DownloadExcel = '/v1/f002/downloadExcel',
  CreateNewAccount = '/v1/f002/createNewAccount',
  UpdateAccount = '/v1/f002/updateAccount'
}

export const initScreenF002 = () => {
  return defHttp.get<F002InitScreenResponse>({ url: Api.InitScreen });
};

export const searchF002 = (params: F002SearchUserRequest) => {
  return defHttp.post<SearchResult<F002SearchUserResponse>>({ url: Api.Search, params });
};

export const downloadExcelF002 = (params: F002SearchUserRequest) => {
  return defHttp.post<F002DownloadExcelResponse>(
    { url: Api.DownloadExcel, params },
  );
};

export const createNewAccount = (params: F002CreateOrUpdateAccountRequest) => {
  return defHttp.post(
    { url: Api.CreateNewAccount, params },
  );
};

export const updateAccount = (params: F002CreateOrUpdateAccountRequest) => {
  return defHttp.put(
    { url: Api.UpdateAccount, params },
  );
};
