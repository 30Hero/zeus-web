<template>
  <div :class="[`${prefixCls}`, 'border-group']">
    <div :class="['p-2', `${prefixCls}__header`]">
      <BasicTitle :helpMessage="helpMessage" :class="[`${prefixCls}__title`, 'underline', 'pl-0']">
        <span @click="handleExpand"
          >{{ t('component.app.searchConditionSetting')
          }}<Icon :class="['align-middle']" :icon="getIconExpand(show)"
        /></span>
      </BasicTitle>

      <div :class="`${prefixCls}__action`">
        <a-button type="primary" class="right-2" @click="handleSearch"
          ><Icon icon="ant-design:search-outlined" />{{ t('common.searchText') }}
        </a-button>
        <a-button :class="[`${prefixCls}__reset`, 'ant-btn-info']" @click="handleClear"
          ><Icon icon="ant-design:delete-outlined" />{{ t('common.resetText') }}
        </a-button>
        <slot name="action"> </slot>
      </div>
    </div>
    <div class="p-2">
      <CollapseTransition :enable="canExpan">
        <div :class="[`${prefixCls}__body`]" v-show="show">
          <slot name="body"></slot>
        </div>
      </CollapseTransition>
    </div>
  </div>
</template>
<script lang="ts">
  import type { PropType } from 'vue';

  import { defineComponent, ref, computed } from 'vue';

  // component
  import { Skeleton } from 'ant-design-vue';
  import { CollapseTransition } from '/@/components/Transition/index';
  import { CollapseHeader, LazyContainer } from '/@/components/Container/index';
  import Icon from '/@/components/Icon/index';
  import { BasicTitle } from '/@/components/Basic';

  import { triggerWindowResize } from '/@/utils/event';
  // hook
  import { useTimeoutFn } from '/@/hooks/core/useTimeout';
  import { propTypes } from '/@/utils/propTypes';
  import { useDesign } from '/@/hooks/web/useDesign';
  import { useI18n } from '/@/hooks/web/useI18n';

  export default defineComponent({
    name: 'SearchGroup',
    components: {
      Skeleton,
      LazyContainer,
      CollapseHeader,
      CollapseTransition,
      Icon,
      BasicTitle,
    },
    props: {
      title: propTypes.string.def(''),
      // Can it be expanded
      canExpan: propTypes.bool.def(true),
      // Warm reminder on the right side of the title
      helpMessage: {
        type: [Array, String] as PropType<string[] | string>,
        default: '',
      },
      // Whether to trigger window.resize when expanding and contracting,
      // Can adapt to tables and forms, when the form shrinks, the form triggers resize to adapt to the height
      triggerWindowResize: propTypes.bool,
      loading: propTypes.bool,
      // Delayed loading
      lazy: propTypes.bool,
      // Delayed loading time
      lazyTime: propTypes.number.def(0),
    },
    setup(props, { emit }) {
      const show = ref(true);
      const { t } = useI18n();

      const { prefixCls } = useDesign('search-group');

      /**
       * @description: Handling development events
       */
      function handleExpand() {
        show.value = !show.value;
        if (props.triggerWindowResize) {
          // 200 milliseconds here is because the expansion has animation,
          useTimeoutFn(triggerWindowResize, 200);
        }
      }

      function handleSearch() {
        emit('onSearch');
      }

      function handleClear() {
        emit('onClear');
      }

      const getBindValues = computed((): any => {
        return props;
      });

      function getIconExpand(isShow: boolean): string {
        if (isShow) {
          return 'feather:chevrons-down';
        }
        return 'feather:chevrons-up';
      }

      return {
        t,
        show,
        handleExpand,
        prefixCls,
        getBindValues,
        getIconExpand,
        handleSearch,
        handleClear,
      };
    },
  });
</script>
<style lang="less" scoped>
  @prefix-cls: ~'@{namespace}-search-group';

  .@{prefix-cls} {
    background: #fff;
    border-radius: 2px;
    transition: all 0.3s ease-in-out;

    &__header {
      display: flex;
      height: 50px;
      // margin-bottom: 10px;
      justify-content: space-between;
      align-items: center;
      background-color: rgba(0, 0, 0, 0.02);
      border-bottom: 1px solid #e6ebf5;
    }

    &__title {
      display: flex;
      height: 32px;
      // margin-bottom: 10px;
      justify-content: space-between;
      align-items: center;
      color: @primary-color !important;
    }

    &__action {
      display: flex;
      text-align: right;
      flex: 1;
      align-items: center;
      justify-content: flex-end;
    }
  }
</style>
