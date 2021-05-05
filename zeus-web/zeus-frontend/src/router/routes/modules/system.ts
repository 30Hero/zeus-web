import type { AppRouteModule } from '/@/router/types';

import { LAYOUT } from '/@/router/constant';
import { t } from '/@/hooks/web/useI18n';
import { ActionIdEnum } from '/@/enums/actionIdEnum';

const system: AppRouteModule = {
  path: '/system',
  name: 'System',
  component: LAYOUT,
  redirect: '/system/account',
  meta: {
    icon: 'ion:settings-outline',
    title: t('routes.system.moduleName'),
  },
  children: [
    {
      path: 'account',
      name: 'AccountManagement',
      meta: {
        title: t('routes.system.account'),
        ignoreKeepAlive: true,
        actionId: ActionIdEnum.S001_A004
      },
      component: () => import('/@/views/modules/S002-account-manager/index.vue'),
    }
  ],
};

export default system;
