export const timestamp = () => +Date.now();
import { unref } from 'vue';
import { isEmpty, isNullOrEmpty, isNullOrUnDef, isObject, isUnDef } from '/@/utils/is';
import { appStore } from '/@/store/modules/app';

export const clamp = (n: number, min: number, max: number) => Math.min(max, Math.max(min, n));
export const noop = () => {};
export const now = () => Date.now();


/**
 * @description:  Set ui mount node
 */
export function getPopupContainer(node?: HTMLElement): HTMLElement {
  return (node?.parentNode as HTMLElement) ?? document.body;
}

/**
 * Add the object as a parameter to the URL
 * @param baseUrl url
 * @param obj
 * @returns {string}
 * eg:
 *  let obj = {a: '3', b: '4'}
 *  setObjToUrlParams('www.baidu.com', obj)
 *  ==>www.baidu.com?a=3&b=4
 */
export function setObjToUrlParams(baseUrl: string, obj: any): string {
  let parameters = '';
  for (const key in obj) {
    parameters += key + '=' + encodeURIComponent(obj[key]) + '&';
  }
  parameters = parameters.replace(/&$/, '');
  return /\?$/.test(baseUrl) ? baseUrl + parameters : baseUrl.replace(/\/?$/, '?') + parameters;
}

export function deepMerge<T = any>(src: any = {}, target: any = {}): T {
  let key: string;
  for (key in target) {
    src[key] = isObject(src[key]) ? deepMerge(src[key], target[key]) : (src[key] = target[key]);
  }
  return src;
}

export function openWindow(
  url: string,
  opt?: { target?: TargetContext | string; noopener?: boolean; noreferrer?: boolean }
) {
  const { target = '__blank', noopener = true, noreferrer = true } = opt || {};
  const feature: string[] = [];

  noopener && feature.push('noopener=yes');
  noreferrer && feature.push('noreferrer=yes');

  window.open(url, target, feature.join(','));
}

// dynamic use hook props
export function getDynamicProps<T, U>(props: T): Partial<U> {
  const ret: Recordable = {};

  Object.keys(props).map((key) => {
    ret[key] = unref((props as Recordable)[key]);
  });

  return ret as Partial<U>;
}

/**
 * set page Title
 * @param {*} title  :page Title
 */
function setDocumentTitle(title: string) {
  document.title = title;
  const ua = navigator.userAgent;
  const regex = /\bMicroMessenger\/([\d.]+)/;
  // 兼容
  if (regex.test(ua) && /ip(hone|od|ad)/i.test(ua)) {
    const i = document.createElement('iframe');
    i.src = '/favicon.ico';
    i.style.display = 'none';
    i.onload = function () {
      setTimeout(function () {
        i.remove();
      }, 9);
    };
    document.body.appendChild(i);
  }
}

export function setTitle(title: string, appTitle?: string) {
  if (title) {
    const _title = title ? ` ${title} - ${appTitle} ` : `${appTitle}`;
    setDocumentTitle(_title);
  }
}

export function deepClone(source) : any {
  if (!source && typeof source !== 'object') {
    throw new Error('error arguments')
  }
  const targetObj = source.constructor === Array ? [] : {}
  Object.keys(source).forEach(keys => {
    if (source[keys] && typeof source[keys] === 'object') {
      targetObj[keys] = deepClone(source[keys])
    } else {
      targetObj[keys] = source[keys]
    }
  })
  return targetObj;
}

export function formatData(source) : any {
  if (!source && typeof source !== 'object') {
    throw new Error('error arguments')
  }
  const targetObj = source.constructor === Array ? [] : {}
  Object.keys(source).forEach(keys => {
    if (typeof source[keys] === 'string') {
      let value = source[keys];
      if (!isNullOrUnDef(value)){
        value = value.trim();
        if (!isEmpty(value)){
          targetObj[keys] = value;
        }
      }
    } else {
      targetObj[keys] = source[keys]
    }
  })
  return targetObj;
}

export function showPageLoading(){
  appStore.setPageLoadingAction(true);
}

export function closePageLoading(){
  appStore.setPageLoadingAction(false);
}

export function diffObject(source, destination) : any {
  if (!source && typeof source !== 'object') {
    throw new Error('error arguments')
  }

  if (!destination && typeof destination !== 'object') {
    throw new Error('error arguments')
  }
  const targetObj = source.constructor === Array ? [] : {}
  Object.keys(source).forEach(keys => {
    if (source[keys] && typeof source[keys] === 'object') {
      targetObj[keys] = diffObject(source[keys], destination[keys])
    } else if (isUnDef(destination[keys]) || source[keys] !== destination[keys]) {
      targetObj[keys] = source[keys]
    }
  })
  return targetObj;
}
