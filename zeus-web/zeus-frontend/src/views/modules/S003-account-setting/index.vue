<template>
  <PageWrapper>
    <a-tabs tabPosition="left" :tabBarStyle="tabBarStyle" class="bg-white">
      <a-tab-pane v-for="item in settingList" :key="item.key" :tab="item.name">
        <component :is="item.component" />
      </a-tab-pane>
    </a-tabs>
  </PageWrapper>
</template>
<script lang="ts">
  import { PageWrapper } from '/@/components/Page';
  import { defineComponent } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { settingList } from './type';
  import BasicSetting from './BasicSetting.vue';
  import SecureSetting from './SecureSetting.vue';

  export default defineComponent({
    components: {
      PageWrapper,
      BasicSetting,
      SecureSetting
    },
    data() {
      return {
        tabBarStyle: {
          width: '220px',
        },
        settingList: settingList,
      };
    },
    setup() {
      const { t } = useI18n();

      return {
        t,
        prefixCls: 's003',
      };
    },
  });
</script>
<style lang="less" scoped></style>
