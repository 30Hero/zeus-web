import type { MenuModule } from '/@/router/types';
import { t } from '/@/hooks/web/useI18n';
import { ActionIdEnum } from '/@/enums/actionIdEnum';

const menu: MenuModule = {
  orderNo: 2000,
  menu: {
    name: t('routes.system.moduleName'),
    path: '/system',
    children: [
      {
        path: 'account',
        name: t('routes.system.account')
      }
    ],
  },
};
export default menu;
