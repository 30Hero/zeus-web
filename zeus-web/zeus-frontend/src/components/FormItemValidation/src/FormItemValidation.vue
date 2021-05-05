<template>
  <a-form-item
    v-bind="$attrs"
    :help="validateMsg"
    :validateStatus="validateStatus"
    @input="handleChange"
  >
    <slot></slot>
  </a-form-item>
</template>
<script lang="ts">
  import { defineComponent, PropType } from 'vue';
  import { isNullOrEmpty } from '/@/utils/is';

  export default defineComponent({
    name: 'a-form-item-validation',
    props: {
      validateMsg: {
        type: String as PropType<string | null>,
        default: null
      }
    },
    computed: {
      validateStatus() {
        if (isNullOrEmpty(this.validateMsg)) {
          return 'success';
        }
        return 'error';
      },
    },
    methods: {
      handleChange() {
        this.$emit('update:validateMsg', '');
      },
    },
    setup() {
      return {};
    },
  });
</script>
<style lang="less" scoped></style>
