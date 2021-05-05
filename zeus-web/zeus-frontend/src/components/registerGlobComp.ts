import Antd from 'ant-design-vue';
import { FormItemValidation } from '/@/components/FormItemValidation/index';
import { App } from 'vue';

const compList = [FormItemValidation];

export function registerGlobComp(app: App) {
  app.use(Antd);
  compList.forEach((comp: any) => {
    app.component(comp.name || comp.displayName, comp);
  });
}
