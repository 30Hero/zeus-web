import type { AppRouteModule } from '/@/router/types';

import { LAYOUT } from '/@/router/constant';
import { t } from '/@/hooks/web/useI18n';

const setting: AppRouteModule = {
  path: '/setting',
  name: 'Setting',
  component: LAYOUT,
  redirect: '/setting/account',
  meta: {
    icon: 'ion:settings-outline',
    title: t('routes.setting.moduleName'),
  },
  children: [
    {
      path: 'account',
      name: 'AccountSetting',
      meta: {
        title: t('routes.setting.account'),
        hideMenu: true
      },
      component: () => import('/@/views/modules/S003-account-setting/index.vue'),
    }
  ],
};

export default setting;
