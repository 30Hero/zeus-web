/**
 * @description: Login request
 */
export interface LoginRequest {
  username: string;
  password: string;
}

export interface RoleInfo {
  roleName: string;
  value: string;
}

export interface UserInfoResponse {
  id: number;
  userName: string;
  roleId: number;
  fullName: string;
  phone: string;
  email: string;
  img: string;
  perms: UserPermsResponse[];
}

export interface UserPermsResponse {
  screenId: string;
  functionId: string;
  actionId: string;
  permissionId: number;
  displayOperationControl: number;
}

/**
 * @description: Login response
 */
export interface LoginResponse {
  authToken: string;
  tokenType: string;
}
