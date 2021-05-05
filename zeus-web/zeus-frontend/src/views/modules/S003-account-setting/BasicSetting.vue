<template>
  <CollapseContainer title="Cài đặt thông tin cá nhân" :canExpan="false">
    <div :class="['grid', 'md:grid-cols-2', 'gap-4', 'sm:grid-cols-1', 'p-2']">
      <div>
        <a-form :label-col="{ span: 7 }" :wrapper-col="{ span: 17 }">
          <a-form-item-validation label="Tên đăng nhập">
            <a-input
              v-model:value="dataBinding.userName"
              ref="userName"
              placeholder="Tên đăng nhập"
              :disabled="true"
              :allowClear="true"
            />
          </a-form-item-validation>
          <a-form-item-validation
            label="Họ và tên"
            :required="true"
            v-model:validateMsg="validateMessages.fullName"
          >
            <a-input
              v-model:value="dataBinding.fullName"
              ref="fullName"
              placeholder="Họ và tên"
              :maxLength="150"
              :allowClear="true"
            />
          </a-form-item-validation>
          <a-form-item-validation label="Email" v-model:validateMsg="validateMessages.email">
            <a-input
              v-model:value="dataBinding.email"
              placeholder="Email"
              :maxLength="255"
              :allowClear="true"
            />
          </a-form-item-validation>
          <a-form-item-validation
            label="Số điện thoại"
            v-model:validateMsg="validateMessages.phone"
          >
            <a-input
              v-model:value="dataBinding.phone"
              placeholder="Số điện thoại"
              v-input-number
              :maxLength="20"
              ref="phone"
              :allowClear="true"
            />
          </a-form-item-validation>
        </a-form>
      </div>
      <div>
        <div class="grid justify-items-center change-avatar">
          <div class="mb-2"> Ảnh đại diện </div>
          <img :src="getImgSrc(dataBinding.img)" />
          <a-upload
            :showUploadList="false"
            :multiple="false"
            :fileList="fileList"
            :beforeUpload="handleBeforeUploadAvatar"
            accept="image/png, image/jpeg, image/jpg"
          >
            <a-button class="mt-2">
              <Icon icon="feather:upload" />Thay đổi ảnh đại diện</a-button
            >
          </a-upload>
        </div>
      </div>
      <div>
        <div class="grid grid-cols-24">
          <div class="col-start-8">
            <a-button type="primary">Cập nhật thông tin cá nhân </a-button>
          </div>
        </div>
      </div>
    </div>
  </CollapseContainer>
</template>
<script lang="ts">
  import { PageWrapper } from '/@/components/Page';
  import { defineComponent } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { CollapseContainer } from '/@/components/Container/index';
  import { UpdateBasicInfoValidateMessages } from './type';
  import noAvatarImg from '/@/assets/images/no-avatar.png';
  import Icon from '/@/components/Icon/index';
  import { userStore } from '/@/store/modules/user';
  import { isNull } from '/@/utils/is';
  import { useMessage } from '/@/hooks/web/useMessage';

  export default defineComponent({
    components: {
      PageWrapper,
      CollapseContainer,
      Icon,
    },
    data() {
      return {
        dataBinding: {
          userName: null as string | null,
          fullName: null as string | null,
          email: null as string | null,
          phone: null as string | null,
          img: null as string | null,
        },
        validateMessages: new UpdateBasicInfoValidateMessages() as UpdateBasicInfoValidateMessages,
        fileList: [] as object[]
      };
    },
    methods: {
      getImgSrc(img) {
        if (isNull(img)) {
          return noAvatarImg;
        } else {
          return img;
        }
      },
      getBase64(img, callback) {
        const reader = new FileReader();
        reader.addEventListener('load', () => callback(reader.result));
        reader.readAsDataURL(img);
      },
      handleBeforeUploadAvatar(file) {
        let fileType: string = file.type;
        fileType = fileType.toLowerCase();
        const isJpgOrPng =
          fileType === 'image/jpeg' || fileType === 'image/png' || fileType === 'image/jpg';
        if (!isJpgOrPng) {
          this.createMessage.error("Ảnh upload phải có định dạng là 'jpeg/png/jpg'!");
          return false;
        }
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isLt2M) {
          this.createMessage.error('Ảnh upload phải có dung lượng nhỏ hơn 2MB!');
        }
        this.getBase64(file, imageUrl => {
          this.dataBinding.img = imageUrl;
        })
        return false;
      },
    },
    mounted() {
      const currentUser = userStore.getUserInfoState;
      this.dataBinding.userName = currentUser.userName;
      this.dataBinding.fullName = currentUser.fullName;
      this.dataBinding.email = currentUser.email;
      this.dataBinding.phone = currentUser.phone;
      this.dataBinding.img = currentUser.img;
    },
    setup() {
      const { t } = useI18n();
      const { createMessage } = useMessage();

      return {
        t,
        prefixCls: 's003__basic-setting',
        createMessage,
      };
    },
  });
</script>
<style lang="less" scoped>
  .change-avatar {
    img {
      display: block;
      width: 140px;
      height: 140px;
      border: 2px solid #d9d9d9;
      border-radius: 50%;
    }
  }
</style>
