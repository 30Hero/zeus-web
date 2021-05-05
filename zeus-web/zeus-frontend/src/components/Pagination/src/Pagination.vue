<template>
  <a-pagination
    :total="total"
    :show-total="(total, range) => t('component.table.total', {total})"
    :page-size="pageSize"
    :current="current"
    :size="size"
    show-size-changer
    show-quick-jumper
    show-less-items
  />
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useDesign } from '/@/hooks/web/useDesign';
import { propTypes } from '/@/utils/propTypes';

  export default defineComponent({
    props: {
      size: propTypes.string.def('small'),
      total: propTypes.number.def(0),
      pageSize: propTypes.number.def(10),
      current: propTypes.number.def(1)
    },
    setup() {
      const { t } = useI18n();

      const { prefixCls } = useDesign('pagination');

      return {
        t,
        prefixCls
      }
    },
  });
</script>

<style lang="less" scoped></style>
