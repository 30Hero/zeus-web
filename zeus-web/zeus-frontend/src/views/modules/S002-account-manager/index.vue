<template>
  <PageWrapper>
    <a-form
      layout="vertical"
      :class="[`${prefixCls}__search-group`]"
      :model="dataSearch"
      @keyup.enter="handleSearch"
    >
      <SearchGroup
        @onClear="clearDataSearch"
        @onSearch="handleSearch"
        :title="t('component.app.searchConditionSetting')"
      >
        <template #action>
          <a-button type="primary" class="ml-2" @click="handleDownloadExcel"
            ><Icon icon="ant-design:download-outlined" />{{ t('common.downloadExcelText') }}
          </a-button>
        </template>
        <template #body>
          <div :class="['grid', 'md:grid-cols-4', 'gap-4', 'sm:grid-cols-2', 'p-2']">
            <div>
              <a-form-item label="ID người dùng:" :class="['mb-0', 'font-medium']" name="id">
                <a-input
                  placeholder="ID người dùng"
                  class="w-full"
                  v-input-number
                  v-model:value="dataSearchBinding.id"
                  :maxLength="17"
                  :allowClear="true"
                />
              </a-form-item>
            </div>
            <div
              ><a-form-item
                label="Tên đăng nhập :"
                :class="['mb-0', 'font-medium']"
                name="userName"
              >
                <a-input
                  placeholder="Tên đăng nhập"
                  :maxLength="255"
                  v-model:value="dataSearchBinding.userName"
                  :allowClear="true"
                /> </a-form-item
            ></div>
            <div
              ><a-form-item label="Họ và tên :" :class="['mb-0', 'font-medium']" name="fullName">
                <a-input
                  placeholder="Họ và tên"
                  :maxLength="255"
                  v-model:value="dataSearchBinding.fullName"
                  :allowClear="true"
                /> </a-form-item
            ></div>
            <div
              ><a-form-item label="Phân quyền :" :class="['mb-0', 'font-medium']" name="roleIds">
                <a-select
                  placeholder="Phân quyền"
                  mode="multiple"
                  :maxTagCount="1"
                  :showArrow="true"
                  :allowClear="true"
                  :class="['font-normal']"
                  v-model:value="dataSearchBinding.roleIds"
                >
                  <a-select-option
                    v-for="(item, index) in roleOptions"
                    :key="index"
                    :value="item.id"
                  >
                    {{ item.name + ' - ' + item.description }}
                  </a-select-option>
                </a-select>
              </a-form-item></div
            >
          </div>
        </template>
      </SearchGroup>
    </a-form>
    <div class="my-3" />

    <div class="bg-white pb-1 mb-3 border-group">
      <div class="clearfix ml-2 pt-2">
        <div class="float-left inline-block">
          <a-button type="primary" class="ant-btn-warning" @click="handleCreate"
            ><Icon icon="ant-design:plus-outlined" />Thêm mới
          </a-button>
        </div>
        <Pagination
          class="float-right mr-1"
          :total="searchResult.total"
          :pageSize="searchResult.pageSize"
          :current="searchResult.pageNum"
          @change="handlePagination"
          @showSizeChange="handlePageSizeChange"
        />
      </div>

      <div class="m-2"
        ><a-table
          :data-source="searchResult.list"
          bordered
          rowKey="id"
          :pagination="false"
          :scroll="{ x: 1000, y: 800 }"
        >
          <a-table-column title="ID" width="100px" data-index="id" />
          <a-table-column title="Tên đăng nhập" data-index="userName" />
          <a-table-column title="Họ tên" data-index="fullName" />
          <a-table-column title="Số điện thoại" data-index="phone" />
          <a-table-column title="Email" data-index="email" />
          <a-table-column title="Phân quyền" data-index="roleName" />
          <a-table-column
            key="delFlag"
            title="Trạng thái"
            data-index="delFlag"
            align="center"
            width="170px"
          >
            <template v-slot="data">
              <a-tag class="user-status" :color="statusColorFilter(data.record.delFlag)">{{
                data.record.delFlag ? 'Đã xóa' : 'Đang hoạt động'
              }}</a-tag>
            </template>
          </a-table-column>
          <a-table-column title="Hành động" align="center" width="120px" fixed="right">
            <template v-slot="data">
              <a-tooltip placement="top" title="Chỉnh sửa">
                <a-button class="btn-edit" ghost @click="handleUpdate(data.record)">
                  <Icon icon="clarity:note-edit-line" />
                </a-button>
              </a-tooltip>
            </template>
          </a-table-column>
        </a-table>
      </div>
    </div>

    <AccountModal @register="registerModalAdd" :roleOptions="roleOptions" @ok="search" type="add" />

    <AccountModal
      @register="registerModalUpdate"
      :roleOptions="roleOptions"
      @ok="search"
      type="update"
      ref="modalUpdate"
    />
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent } from 'vue';

  import { PageWrapper } from '/@/components/Page';
  import { SearchGroup } from '/@/components/SearchGroup/index';
  import { Pagination } from '/@/components/Pagination/index';
  import AccountModal from './AccountModal.vue';
  import Icon from '/@/components/Icon/index';
  import { useI18n } from '/@/hooks/web/useI18n';
  import {
    downloadExcelF002,
    initScreenF002,
    searchF002,
  } from '/@/api/modules/F002AccountManager/index';
  import {
    F002DownloadExcelResponse,
    F002SearchUserRequest,
    F002SearchUserResponse,
    Role,
  } from '/@/api/modules/F002AccountManager/model';
  import { closePageLoading, formatData, showPageLoading } from '/@/utils';
  import { SearchResult } from '/@/api/model/baseModel';
  import { downloadByData } from '/@/utils/file/download';
  import { useModal } from '/@/components/Modal';

  export default defineComponent({
    name: 'AccountManagement',
    components: {
      PageWrapper,
      SearchGroup,
      Pagination,
      Icon,
      AccountModal,
    },
    data() {
      return {
        dataSearch: new F002SearchUserRequest() as F002SearchUserRequest,
        roleOptions: [] as Role[],
        searchResult: new SearchResult() as SearchResult<F002SearchUserResponse>,
        dataSearchBinding: new F002SearchUserRequest() as F002SearchUserRequest,
      };
    },
    methods: {
      clearDataSearch() {
        this.dataSearchBinding = new F002SearchUserRequest();
      },
      handleSearch() {
        this.dataSearch = formatData(this.dataSearchBinding);
        this.dataSearch.pageNum = 1;
        this.search();
      },
      handlePagination(page: number, pageSize: number) {
        this.dataSearch.pageNum = page;
        this.dataSearch.pageSize = pageSize;
        this.search();
      },
      handlePageSizeChange(_: any, size: number) {
        this.dataSearch.pageNum = 1;
        this.dataSearch.pageSize = size;
        this.search();
      },
      search() {
        showPageLoading();
        // Call API
        searchF002(this.dataSearch)
          .then((res) => {
            this.searchResult = res;
          })
          .finally(() => {
            // Close loading
            closePageLoading();
          });
      },
      handleDownloadExcel() {
        showPageLoading();
        // Call API
        downloadExcelF002(this.dataSearch)
          .then((res: F002DownloadExcelResponse) => {
            let raw: string;
            let rawLength: number;
            let base64: string | null = res.file;
            try {
              raw = atob(base64 ? base64 : '');
            } catch (err) {
              raw = '';
            }
            rawLength = raw.length;
            let uInt8Array = new Uint8Array(rawLength);

            for (let i = 0; i < rawLength; i += 1) {
              uInt8Array[i] = raw.charCodeAt(i);
            }

            let blob = new Blob([uInt8Array], { type: 'application/vnd.ms-excel,charset=UTF-8' });

            downloadByData(blob, 'List_User.xlsx');
          })
          .finally(() => {
            // Close loading
            closePageLoading();
          });
      },
      handleCreate() {
        this.openModalAdd(true);
      },
      statusColorFilter(value) {
        return value ? 'red' : 'green';
      },
      handleUpdate(record: F002SearchUserResponse) {
        (this.$refs.modalUpdate as any).setAccountUpdate(record);
        this.openModalUpdate(true);
      },
    },
    mounted() {
      showPageLoading();
      // Call API
      initScreenF002()
        .then((res) => {
          this.roleOptions = res.roles;
          this.search();
        })
        .catch(() => {
          closePageLoading();
        });
    },
    setup() {
      const { t } = useI18n();

      const [registerModalAdd, { openModal: openModalAdd }] = useModal();

      const [registerModalUpdate, { openModal: openModalUpdate }] = useModal();

      return {
        t,
        registerModalAdd,
        prefixCls: 's002',
        openModalAdd,
        registerModalUpdate,
        openModalUpdate,
      };
    },
  });
</script>
<style lang="less" scoped>
  .user-status {
    width: 120px;
  }

  .btn-edit,
  .btn-delete {
    padding-right: 5px;
    padding-left: 5px;
  }

  .btn-edit,
  .btn-edit:hover {
    color: #2a7dc9 !important;
    border-color: none !important;
    border-color: transparent;
  }

  .btn-delete,
  .btn-delete:hover {
    color: #f5222d !important;
    border-color: none !important;
    border-color: transparent;
  }
</style>
