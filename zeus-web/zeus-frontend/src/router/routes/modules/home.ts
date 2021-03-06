import type { AppRouteModule } from '/@/router/types';

import { LAYOUT } from '/@/router/constant';
import { t } from '/@/hooks/web/useI18n';
import { ActionIdEnum } from '/@/enums/actionIdEnum';

const dashboard: AppRouteModule = {
  path: '/home',
  name: 'Home',
  component: LAYOUT,
  redirect: '/home/welcome',
  meta: {
    icon: 'ion:home-outline',
    title: t('routes.dashboard.welcome'),
  },
  children: [
    {
      path: 'welcome',
      name: 'Welcome',
      component: () => import('/@/views/dashboard/welcome/index.vue'),
      meta: {
        title: t('routes.dashboard.welcome'),
        affix: true,
        icon: 'bx:bx-home',
        actionId: ActionIdEnum.S001_A001
      },
    },
  ],
};

export default dashboard;
