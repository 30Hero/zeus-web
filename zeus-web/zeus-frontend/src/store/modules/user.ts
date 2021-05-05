import type {
  LoginRequest,
  UserInfoResponse,
  UserPermsResponse
} from '/@/api/sys/model/userModel';
import type { UserInfo } from '/@/store/types';

import store from '/@/store/index';
import { VuexModule, Module, getModule, Mutation, Action } from 'vuex-module-decorators';
import { hotModuleUnregisterModule } from '/@/utils/helper/vuexHelper';

import { PageEnum } from '/@/enums/pageEnum';
import { PERMS_KEY, TOKEN_KEY, USER_INFO_KEY } from '/@/enums/cacheEnum';

import { useMessage } from '/@/hooks/web/useMessage';

import router from '/@/router';

import { loginApi, getUserInfo, logout } from '/@/api/sys/user';

import { useI18n } from '/@/hooks/web/useI18n';
import { ErrorMessageMode } from '/@/utils/http/axios/types';
import { getAuthCache, setAuthCache, setToken, getToken } from '/@/utils/auth/index';
import { isNullOrUnDef } from '/@/utils/is';

const NAME = 'app-user';
hotModuleUnregisterModule(NAME);

@Module({ namespaced: true, name: NAME, dynamic: true, store })
class User extends VuexModule {
  // user info
  private userInfoState: UserInfo | null = null;

  // token
  private tokenState = '';

  // permissionList
  private permListState: UserPermsResponse[] = [];

  get getUserInfoState(): UserInfo {
    return this.userInfoState || getAuthCache<UserInfo>(USER_INFO_KEY) || {};
  }

  get getTokenState(): string {
    return this.tokenState || getToken();
  }

  get getPermListState(): UserPermsResponse[] {
    return this.permListState.length > 0 ? this.permListState : getAuthCache<UserPermsResponse[]>(PERMS_KEY);
  }

  @Mutation
  commitResetState(): void {
    this.userInfoState = null;
    this.tokenState = '';
    this.permListState = [];
  }

  @Mutation
  commitUserInfoState(info: UserInfo): void {
    this.userInfoState = info;
    setAuthCache(USER_INFO_KEY, info);
  }

  @Mutation
  commitPermListState(roleList: UserPermsResponse[]): void {
    this.permListState = roleList;
    setAuthCache(PERMS_KEY, roleList);
  }

  @Mutation
  commitTokenState(token: string): void {
    this.tokenState = token;
    setToken(token);
  }

  /**
   * @description: login
   */
  @Action
  async login(
    params: LoginRequest & {
      goHome?: boolean;
      mode?: ErrorMessageMode;
    }
  ): Promise<UserInfoResponse | null> {
    try {
      const { goHome = true, mode, ...loginParams } = params;
      const data = await loginApi(loginParams, mode);

      const { authToken, tokenType } = data;

      // save token
      this.commitTokenState(`${tokenType} ${authToken}`);

      // get user info
      const userInfo = await this.getUserInfoAction();

      if (isNullOrUnDef(userInfo)){
        this.logout();
        return null;
      }

      goHome && (await router.replace(PageEnum.BASE_HOME));
      return userInfo;
    } catch (error) {
      return null;
    }
  }

  @Action
  async getUserInfoAction() {
    let userInfo : UserInfoResponse | null = null;
    await getUserInfo().then((res) =>{
        userInfo = res;
        this.commitUserInfoState(userInfo);
        this.commitPermListState(userInfo.perms);
      }
    ).catch((e) => {
      console.log(e);
    });
    return userInfo;
  }

  /**
   * @description: logout
   */
  @Action
  async logout(goLogin = false) {
    logout().finally(()=>{
      goLogin && router.push(PageEnum.BASE_LOGIN);
    });
  }

  /**
   * @description: Confirm before logging out
   */
  @Action
  async confirmLoginOut() {
    const { createConfirm } = useMessage();
    const { t } = useI18n();
    createConfirm({
      iconType: 'warning',
      title: t('sys.app.logoutTip'),
      content: t('sys.app.logoutMessage'),

      onOk: async () => {
        await this.logout(true);
      },
    });
  }
}
export const userStore = getModule<User>(User);
