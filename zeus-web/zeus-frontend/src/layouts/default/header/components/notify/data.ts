export interface ListItem {
  id: string;
  avatar: string;
  title: string;
  datetime: string;
  type: string;
  read?: boolean;
  description: string;
  clickClose?: boolean;
  extra?: string;
  color?: string;
}

export interface TabItem {
  key: string;
  name: string;
  list: ListItem[];
  unreadlist?: ListItem[];
}

export const tabListData: TabItem[] = [
  {
    key: '1',
    name: 'Thông báo',
    list: [
      // {
      //   id: '000000001',
      //   avatar: 'https://gw.alipayobjects.com/zos/rmsportal/ThXAXghbEsBCCSDihZxY.png',
      //   title: '你收到了 14 份新周报',
      //   description: '',
      //   datetime: '2017-08-09',
      //   type: '1',
      // }
    ],
  },
];
