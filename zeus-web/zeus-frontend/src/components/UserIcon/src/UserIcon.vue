<template>
  <a-avatar class="user-icon-avatar" :size="size">{{acronym}}</a-avatar>
</template>
<script lang="ts">
  // components
  import { Avatar } from 'ant-design-vue';

  import { defineComponent, computed } from 'vue';

  import { userStore } from '/@/store/modules/user';

  import { propTypes } from '/@/utils/propTypes';

  export default defineComponent({
    name: 'UserIcon',
    components: {
      [Avatar.name]: Avatar,
    },
    props: {
      size: propTypes.number
    },
    setup() {
      const { fullName = '?' } = userStore.getUserInfoState || {};

      const acronym = computed(() => {
        return fullName.charAt(0).toUpperCase();
      });

      return {
        acronym,
      };
    },
  });
</script>

<style lang="less" scoped>
  .user-icon-avatar{
    background-color: #87d068;
  }
</style>
