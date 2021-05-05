import { genMessage } from '../helper';
import antdLocale from 'ant-design-vue/es/locale/vi_VN';
import momentLocale from 'moment/dist/locale/eu';

const modules = import.meta.globEager('./vi_VN/**/*.ts');
export default {
  message: {
    ...genMessage(modules, 'vi_VN'),
    antdLocale,
  },
  momentLocale,
  momentLocaleName: 'vi_VN',
};
