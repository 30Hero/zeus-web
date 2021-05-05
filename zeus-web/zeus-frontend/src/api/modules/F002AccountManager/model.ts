import { SearchPagingRequest } from "../../model/baseModel";

export interface Role {
  id: number;
  name: string;
  description: string;
  delFlag: boolean
}

export interface F002InitScreenResponse {
  roles: Role[];
}

export class F002SearchUserRequest extends SearchPagingRequest {
  public id: number | null = null;
  public userName: string | null = null;
  public fullName: string | null = null;
  public roleIds: number[] | null = [];
}

export class F002SearchUserResponse {
  public id: number | null = null;
  public userName: string | null = null;
  public roleId: number | null = null;
  public fullName: string | null = null;
  public phone: string | null = null;
  public email: string | null = null;
  public delFlag: boolean | null = null;
  public roleName: string | null = null;
}

export class F002DownloadExcelResponse {
  public file: string | null = null;
}

export class F002CreateOrUpdateAccountRequest {
  public id: number | null = null;
  public userName: string | null = null;
  public fullName: string | null = null;
  public email: string | null = null;
  public phone: string | null = null;
  public password: string | null = null;
  public repeatPassword: string | null = null;
  public roleId: number | null = null;
  public delFlag: boolean | null = false;
}
