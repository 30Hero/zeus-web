export interface LockInfo {
  pwd: string | undefined;
  isLock: boolean;
}

export interface UserInfo {
  id: number;
  userName: string;
  roleId: number;
  fullName: string;
  phone: string;
  email: string;
  img: string;
}
