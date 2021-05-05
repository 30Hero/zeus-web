<template>
  <BasicModal
    v-bind="$attrs"
    :title="type == 'add' ? 'Thêm mới người dùng' : 'Cập nhật thông tin người dùng'"
    :okText="type == 'add' ? 'Thêm mới' : 'Cập nhật'"
    @register="register"
    :dialogStyle="{ top: '20px' }"
    @visible-change="visibleChange"
    @ok="handleSubmit"
  >
    <a-form :label-col="{ span: 7 }" :wrapper-col="{ span: 17 }">
      <a-form-item-validation
        label="Tên đăng nhập"
        :required="type == 'add' ? true : false"
        v-model:validateMsg="validateMessages.userName"
      >
        <a-input
          v-model:value="accountBinding.userName"
          ref="userName"
          placeholder="Tên đăng nhập"
          :maxLength="50"
          :disabled="type == 'update'"
          :allowClear="true"
        />
      </a-form-item-validation>
      <a-form-item-validation label="Trạng thái" v-if="type == 'update'">
        <a-radio-group v-model:value="accountBinding.delFlag" ref="delFlag">
          <a-radio :value="false" class="text-green-500"> Đang hoạt động </a-radio>
          <a-radio :value="true" class="text-red-500"> Đã xóa </a-radio>
        </a-radio-group>
      </a-form-item-validation>
      <a-form-item-validation
        label="Họ và tên"
        :required="true"
        v-model:validateMsg="validateMessages.fullName"
      >
        <a-input
          v-model:value="accountBinding.fullName"
          ref="fullName"
          placeholder="Họ và tên"
          :maxLength="150"
          :allowClear="true"
        />
      </a-form-item-validation>
      <a-form-item-validation
        label="Phân quyền"
        :required="true"
        v-model:validateMsg="validateMessages.roleId"
      >
        <a-select placeholder="Phân quyền" v-model:value="accountBinding.roleId" ref="roleId">
          <a-select-option v-for="(item, index) in roleOptions" :key="index" :value="item.id">
            {{ item.name + ' - ' + item.description }}
          </a-select-option>
        </a-select>
      </a-form-item-validation>
      <a-form-item-validation
        label="Mật khẩu"
        :required="true"
        v-model:validateMsg="validateMessages.password"
      >
        <a-input-password
          v-model:value="accountBinding.password"
          placeholder="Mật khẩu"
          :maxLength="150"
          ref="password"
          :allowClear="true"
        />
      </a-form-item-validation>
      <a-form-item-validation
        label="Nhập lại mật khẩu"
        :required="true"
        v-model:validateMsg="validateMessages.repeatPassword"
      >
        <a-input-password
          v-model:value="accountBinding.repeatPassword"
          placeholder="Nhập lại mật khẩu"
          :maxLength="150"
          ref="repeatPassword"
          :allowClear="true"
        />
      </a-form-item-validation>
      <a-form-item-validation label="Email" v-model:validateMsg="validateMessages.email">
        <a-input
          v-model:value="accountBinding.email"
          placeholder="Email"
          :maxLength="255"
          :allowClear="true"
        />
      </a-form-item-validation>
      <a-form-item-validation label="Số điện thoại" v-model:validateMsg="validateMessages.phone">
        <a-input
          v-model:value="accountBinding.phone"
          placeholder="Số điện thoại"
          v-input-number
          :maxLength="20"
          ref="phone"
          :allowClear="true"
        />
      </a-form-item-validation>
    </a-form>
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent, nextTick, PropType } from 'vue';
  import { AccountValidateMessages } from './type';
  import { createNewAccount, updateAccount } from '/@/api/modules/F002AccountManager';
  import {
    F002CreateOrUpdateAccountRequest,
    F002SearchUserResponse,
    Role,
  } from '/@/api/modules/F002AccountManager/model';
  import { useLoading } from '/@/components/Loading';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { deepClone, diffObject, formatData } from '/@/utils';
  import { isEmpty } from '/@/utils/is';
  import { propTypes } from '/@/utils/propTypes';
  import { scrollIntoView } from '/@/utils/scrollTo';

  export default defineComponent({
    name: 'AccountModal',
    components: {
      BasicModal,
    },
    emits: ['ok'],
    props: {
      roleOptions: {
        type: Array as PropType<Role[]>,
        default: [],
      },
      type: propTypes.oneOf(['add', 'update']).def('add'),
    },
    data() {
      return {
        validateMessages: new AccountValidateMessages(),
        accountBinding: new F002CreateOrUpdateAccountRequest(),
        accountUpdate: new F002CreateOrUpdateAccountRequest(),
      };
    },
    methods: {
      handleSubmit() {
        this.openFullLoading();

        if (this.type == 'add') {
          createNewAccount(formatData(this.accountBinding))
            .then((_) => {
              this.closeModal();
              this.$emit('ok');
              this.createMessage.success(this.t('common.success'));
            })
            .catch((err) => {
              if (err.response.status == 422) {
                let fieldErrs = err.response.data.message.fieldErrs;
                this.validateMessages = fieldErrs;
                nextTick(() => {
                  scrollIntoView(this.$refs[Object.keys(fieldErrs)[0]]);
                });
              }
            })
            .finally(() => {
              this.closeFullLoading();
            });
        } else {
          let dataUpdate = diffObject(
            formatData(this.accountBinding),
            this.accountUpdate
          ) as F002CreateOrUpdateAccountRequest;
          dataUpdate.id = this.accountUpdate.id;
          if (isEmpty(dataUpdate)) {
            this.createMessage.warning('Chưa nhập thông tin thay đổi!');
            this.closeFullLoading();
            return;
          }

          updateAccount(dataUpdate)
            .then((_) => {
              this.closeModal();
              this.$emit('ok');
              this.createMessage.success(this.t('common.success'));
            })
            .catch((err) => {
              if (err.response.status == 422) {
                let fieldErrs = err.response.data.message.fieldErrs;
                this.validateMessages = fieldErrs;
                nextTick(() => {
                  scrollIntoView(this.$refs[Object.keys(fieldErrs)[0]]);
                });
              }
            })
            .finally(() => {
              this.closeFullLoading();
            });
        }
      },
      resetFields() {
        this.accountBinding = new F002CreateOrUpdateAccountRequest();

        this.validateMessages = new AccountValidateMessages();
      },
      visibleChange(isVisible: boolean) {
        if (!isVisible) {
          this.resetFields();
        }
      },
      setAccountUpdate(record: F002SearchUserResponse) {
        this.accountUpdate.id = record.id;
        this.accountUpdate.userName = record.userName;
        this.accountUpdate.fullName = record.fullName;
        this.accountUpdate.email = record.email;
        this.accountUpdate.phone = record.phone;
        this.accountUpdate.roleId = record.roleId;
        this.accountUpdate.delFlag = record.delFlag;

        this.accountBinding = deepClone(this.accountUpdate);
      },
    },
    setup() {
      const { t } = useI18n();

      const { createMessage } = useMessage();

      const [openFullLoading, closeFullLoading] = useLoading({
        tip: t('common.processingText'),
      });

      const [register, { closeModal }] = useModalInner();

      return {
        t,
        openFullLoading,
        closeFullLoading,
        createMessage,
        register,
        closeModal,
      };
    },
  });
</script>

<style lang="less" scoped></style>
