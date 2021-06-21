/* eslint-disable eqeqeq */
<template>
  <a-card :bordered="false" >
    <a-spin :spinning="loading">
      <div class="table-page-search-wrapper" v-if="hasPerm('dataCollect:page')">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="数据集名称">
                <a-input v-model="queryParam.name" allow-clear placeholder="请输入数据集名称"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="唯一编码">
                <a-input v-model="queryParam.code" allow-clear placeholder="请输入唯一编码"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons">
                <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <div class="table-operator" v-if="hasPerm('dataCollect:add')" >
        <a-button type="primary" v-if="hasPerm('dataCollect:add')" icon="plus" @click="$refs.addForm.add()">新增数据集</a-button>
      </div>
      <s-table
        ref="table"
        size="default"
        :columns="columns"
        :data="loadData"
        :alert="true"
        :rowKey="(record) => record.id"
      >
        <span slot="status" slot-scope="text">
          {{ statusFilter(text) }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a v-if="hasPerm('dataCollect:edit')" @click="$refs.editForm.edit(record)">编辑</a>
          <a-divider type="vertical" v-if="hasPerm('dataCollect:edit') & hasPerm('dataCollect:delete')" />
          <a-popconfirm v-if="hasPerm('dataCollect:delete')" placement="topRight" title="确认删除？" @confirm="() => dataCollectDelete(record)">
            <a>删除</a>
          </a-popconfirm>
        </span>
      </s-table>
      <add-form ref="addForm" @ok="handleOk" />
      <edit-form ref="editForm" @ok="handleOk" />
    </a-spin>
  </a-card>
</template>
<script>
  import { STable } from '@/components'
  // eslint-disable-next-line no-unused-vars,standard/object-curly-even-spacing
  import { getDataCollectPage, dataCollectDelete } from '@/api/modular/main/dataCollect'
  import { sysDictTypeDropDown } from '@/api/modular/system/dictManage'
  import editForm from './editForm'
  import addForm from './addForm'
  export default {
    name: 'MetadataType',
    components: {
      STable,
      editForm,
      addForm
    },
    data () {
      return {
        // description: '面包屑说明',
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 }
        },
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
            dataIndex: 'code'
          },
          {
            title: '排序',
            dataIndex: 'sort'
          },
          {
            title: '状态',
            dataIndex: 'status',
            scopedSlots: { customRender: 'status' }
          }
        ],
        // 加载数据方法 必须为 Promise 对象

        loadData: parameter => {
          return getDataCollectPage(Object.assign(parameter, this.queryParam)).then((res) => {
            return res.data
          })
        },
        loading: false,
        statusDict: [],
        activeDict: []
      }
    },
    created () {
      this.sysDictTypeDropDown()
      if (this.hasPerm('dataCollect:edit') || this.hasPerm('dataCollect:delete')) {
        this.columns.push({
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        })
      }
    },
    methods: {
      statusFilter (status) {
        // eslint-disable-next-line eqeqeq
        const values = this.statusDict.filter(item => item.code == status)
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
      },
      handleOk () {
        this.$refs.table.refresh()
      },
      /**
       * 删除应用
       */
      dataCollectDelete (record) {
        this.loading = true
        dataCollectDelete(record).then((res) => {
          this.loading = false
          if (res.success) {
            this.$message.success('删除成功')
            this.$refs.table.refresh()
          } else {
            this.$message.error('删除失败：' + res.message)
          }
        }).catch((err) => {
          this.$message.error('删除错误：' + err.message)
        })
      }
    }
  }
</script>
<style scoped>
  .table-operator {
    margin-bottom: 18px;
  }
  button {
    margin-right: 8px;
  }
</style>
