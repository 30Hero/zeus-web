<template>
  <PageWrapper>
    <div>Secure setting</div>
  </PageWrapper>
</template>
<script lang="ts">
  import { PageWrapper } from '/@/components/Page';
  import { defineComponent } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';

  export default defineComponent({
    components: {
      PageWrapper
    },
    data() {
      return {
      };
    },
    setup() {
      const { t } = useI18n();

      return {
        t,
        prefixCls: 's003__secure-setting',
      };
    },
  });
</script>
<style lang="less" scoped></style>
