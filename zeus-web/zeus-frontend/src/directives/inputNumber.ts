import type { Directive, App } from 'vue';

const inputNumberDirective: Directive = {
  mounted(el, binding) {
    el.addEventListener('keyup', () => {
      let regex = /^[0-9]*$/
      if (!regex.test(el.value)) {
        el.value = el.value.slice(0, -1)
      }
    })
  }
};

export function setupInputNumberDirective(app: App) {
  app.directive('input-number', inputNumberDirective);
}

export default inputNumberDirective;
