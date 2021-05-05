export const settingList = [
  {
    key: '1',
    name: 'Thông tin cá nhân',
    component: 'BasicSetting',
  },
  {
    key: '2',
    name: 'Bảo mật',
    component: 'SecureSetting',
  }
];


export class UpdateBasicInfoValidateMessages {
  public fullName: string | null = null;
  public email: string | null = null;
  public phone: string | null = null;
}
