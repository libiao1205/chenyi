<template>
  <a-card :bordered="false">

    <div class="table-page-search-wrapper" v-if="hasPerm('metadata:metadataTable:page')">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="24">
            <a-form-item label="类型名称" >
              <a-input v-model="queryParam.name" allow-clear placeholder="请输入类型名称"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="唯一编码" >
              <a-input v-model="queryParam.code" allow-clear placeholder="请输入唯一编码"/>
            </a-form-item>
          </a-col>
          <template v-if="advanced">
            <a-col :md="8" :sm="24">
              <a-form-item label="元数据类型">
                <a-select v-model="queryParam.typeCode" placeholder="请选择元数据类型" >
                  <a-select-option v-for="(item,index) in typeList" :key="index" :value="item.code" >{{ item.value }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="!advanced && 8 || 24" :sm="24">
            <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
              <a @click="toggleAdvanced" style="margin-left: 8px">
                {{ advanced ? '收起' : '展开' }}
                <a-icon :type="advanced ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <div class="table-operator" v-if="hasPerm('metadata:metadataTable:add') || hasPerm('metadata:metadataTable:exportExcel') || hasPerm('metadata:metadataTable:downloadTemplate') || hasPerm('metadata:metadataTable:importExcel')" >
      <a-button type="primary" v-if="hasPerm('metadata:metadataTable:add')" icon="plus" @click="$refs.addForm.add()">新增元数据表</a-button>
      <a-button type="primary" v-if="hasPerm('metadata:metadataTable:exportExcel')" icon="download" @click="exportInfo">导出excel</a-button>
      <a-button type="primary" v-if="hasPerm('metadata:metadataTable:downloadTemplate')" icon="download" @click="downloadTemplate">下载导入模板</a-button>
      <a-upload
        :action="uploadProps.action"
        :headers="{Authorization: 'Bearer ' + token}"
        :showUploadList="false"
        @change="uploadProps.onChange">
        <a-button type="primary" v-if="hasPerm('metadata:metadataTable:importExcel')" icon="upload">批量导入</a-button>
      </a-upload>

    </div>

    <s-table
      ref="table"
      size="default"
      :columns="columns"
      :data="loadData"
      :alert="false"
      :rowKey="(record) => record.code"
    >
      <span slot="status" slot-scope="text">
        {{ statusFilter(text) }}
      </span>
      <span slot="typeName" slot-scope="text">
        {{ typeFilter(text) }}
      </span>

      <span slot="action" slot-scope="text, record">
        <router-link :to="{ path: '/metadata/column', query: {typeCode: record.typeCode, tableCode: record.code, tableName: record.name}}">字段</router-link>
        <a-divider type="vertical" v-if="hasPerm('metadata:metadataTable:edit') || hasPerm('metadata:metadataTable:delete')"/>
        <a-dropdown v-if="hasPerm('metadata:metadataTable:edit') || hasPerm('metadata:metadataTable:delete')">
          <a class="ant-dropdown-link">
            更多 <a-icon type="down" />
          </a>
          <a-menu slot="overlay">

            <a-menu-item v-if="hasPerm('metadata:metadataTable:edit')">
              <a @click="$refs.editForm.edit(record)">编辑</a>
            </a-menu-item>

            <a-menu-item v-if="hasPerm('metadata:metadataTable:delete')">
              <a-popconfirm placement="topRight" title="确认删除？" @confirm="() => metadataTableDelete(record)">
                <a>删除</a>
              </a-popconfirm>
            </a-menu-item>
          </a-menu>
        </a-dropdown>
      </span>

    </s-table>

    <add-form ref="addForm" @ok="handleOk" />
    <edit-form ref="editForm" @ok="handleOk" />
  </a-card>
</template>

<script>
  import { Upload } from 'ant-design-vue/es'
  import { STable } from '@/components'
  import { sysDictTypeDropDown } from '@/api/modular/system/dictManage'
  import { metadataTablePage, metadataTableDelete, metadataTableExportExcel, metadataTableDownloadTemplate } from '@/api/modular/main/metadataTable'
  import addForm from './addForm'
  import editForm from './editForm'
  import { mapState } from 'vuex'
  export default {
    computed: {
      ...mapState({
        token: state => state.user.token
      })
    },
    components: {
      STable,
      addForm,
      editForm,
      Upload
    },

    data () {
      const _this = this
      return {
        // 高级搜索 展开/关闭
        advanced: false,
        // 查询参数
        queryParam: {},
        // 表头
        columns: [
          {
            title: '名称',
            dataIndex: 'name'
          },
          {
            title: '唯一编码',
            dataIndex: 'code',
            width: 150
          },
          {
            title: '元数据类型',
            dataIndex: 'typeName',
            scopedSlots: { customRender: 'typeName' }
          },
          {
            title: '排序',
            dataIndex: 'sort'
          },
          {
            title: '状态',
            dataIndex: 'status',
            scopedSlots: { customRender: 'status' }
          },
          {
            title: '备注',
            dataIndex: 'remark',
            width: 200
          },
          {
            title: '操作',
            width: '150px',
            dataIndex: 'action',
            scopedSlots: { customRender: 'action' }
          }
        ],
        dataCount: 0,
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          return metadataTablePage(Object.assign(parameter, this.queryParam)).then((res) => {
            res.data.rows.forEach(item => {
              item.typeName = item.typeCode
            })
            this.dataCount = res.data.rows.length
            return res.data
          })
        },
        statusDict: [],
        typeList: [],
        uploadProps: {
          name: 'file',
          action: process.env.VUE_APP_API_BASE_URL + '/main/metadata/metadataTable/importExcel',
          onChange(info) {
            if (info.file.status !== 'uploading') {
              console.log(info.file, info.fileList)
            }
            if (info.file.status === 'done') {
              const res = info.file.response.data
              const message = '表执行：' + res.tableSuccessCount + '/' + res.tableCount +
                '，字段执行：' + res.columnSuccessCount + '/' + res.columnCount
              _this.$message.success(message)
              _this.$refs.table.refresh()
            } else if (info.file.status === 'error') {
              _this.$message.error(`导入失败,请使用下载模板！`)
            }
          }
        }
      }
    },
    created () {
      this.sysDictTypeDropDown()
    },

    methods: {
      statusFilter (status) {
        // eslint-disable-next-line eqeqeq
        const values = this.statusDict.filter(item => item.code == status)
        if (values.length > 0) {
          return values[0].value
        }
      },
      typeFilter (typeCode) {
        // eslint-disable-next-line eqeqeq
        const values = this.typeList.filter(item => item.code == typeCode)
        if (values.length > 0) {
          return values[0].value
        }
      },

      /**
       * 获取字典数据
       */
      sysDictTypeDropDown () {
        sysDictTypeDropDown({ code: 'common_status' }).then((res) => {
          this.statusDict = res.data
        })
        sysDictTypeDropDown({ code: 'metadata_type' }).then((res) => {
          this.typeList = []
          res.data.forEach(item => {
            if (item.parentId === null || item.parentId === '0') {
              this.typeList.push(item)
            }
          })
        })
      },

      metadataTableDelete (record) {
        metadataTableDelete(record).then((res) => {
          if (res.success) {
            this.$message.success('删除成功')
            this.$refs.table.refresh()
          } else {
            this.$message.error('删除失败：' + res.message)
          }
        }).catch((err) => {
          this.$message.error('删除错误：' + err.message)
        })
      },

      toggleAdvanced () {
        this.advanced = !this.advanced
      },
      handleOk () {
        this.$refs.table.refresh()
      },
      exportInfo() {
        if (this.dataCount === 0) {
          this.$message.warning('没有要导出的数据！')
        } else {
          const param = '?name=' + (this.queryParam.name === undefined ? '' : this.queryParam.name) +
            '&code=' + (this.queryParam.code === undefined ? '' : this.queryParam.code) +
            '&typeCode=' + (this.queryParam.typeCode === undefined ? '' : this.queryParam.typeCode) +
            '&token=' + this.token
          metadataTableExportExcel(param)
        }
      },
      downloadTemplate() {
        const param = '?token=' + this.token
        metadataTableDownloadTemplate(param)
      }
    }

  }
</script>

<style lang="less">
  .table-operator {
    margin-bottom: 18px;
  }
  button {
    margin-right: 8px;
  }

</style>
