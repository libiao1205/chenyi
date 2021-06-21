<template>
  <a-modal
    title="字典值管理"
    :width="900"
    :visible="visible"
    :footer="null"
    @cancel="handleCancel"
  >
    <a-card :bordered="false">
      <div class="table-page-search-wrapper" v-if="hasPerm('sysDictData:page')">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="字典值" >
                <a-input v-model="queryParam.value" allow-clear placeholder="请输入字典值"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="唯一编码" >
                <a-input v-model="queryParam.code" allow-clear placeholder="请输入唯一编码"/>
              </a-form-item>
            </a-col>
            <a-col :md="!advanced && 8 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="loadData">查询</a-button>
                <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <div class="table-operator" v-if="hasPerm('sysDictData:add')" >
        <a-button type="primary" icon="plus" @click="$refs.addForm.add({typeId: typeId, parentId: 0})">新增数据</a-button>
      </div>

      <el-table
        ref="table"
        :data="table.data"
        :header-cell-style="{background:'#fafafa',color:'rgba(0, 0, 0, 0.85)',fontWeight:'500'}"
        style="width: 100%"
        row-key="id"
        :border="false"
        lazy
        :load="load"
        :key="symbolKey"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
        <el-table-column
          prop="value"
          label="字典值"
          align="left">
        </el-table-column>
        <el-table-column
          prop="code"
          label="唯一编码"
          align="left">
        </el-table-column>
        <el-table-column
          prop="sort"
          label="排序"
          align="left">
        </el-table-column>
        <el-table-column
          prop="remark"
          label="备注"
          align="left">
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态"
          align="left">
        </el-table-column>
        <el-table-column
          label="操作"
          width="140"
          align="left">
          <template slot-scope="scope">
            <a v-if="hasPerm('sysDictData:edit')" @click="$refs.editForm.edit(scope.row)">编辑</a>
            <a-divider type="vertical" v-if="hasPerm('sysDictData:edit') & hasPerm('sysDictData:delete')" />
            <a-dropdown v-if="hasPerm('sysDictData:edit') || hasPerm('sysDictData:grantRole') || hasPerm('sysDictData:grantData') || hasPerm('sysDictData:delete')">
              <a class="ant-dropdown-link">
                更多 <a-icon type="down" />
              </a>
              <a-menu slot="overlay">
                <a-menu-item v-if="hasPerm('sysDictData:edit')">
                  <a v-if="hasPerm('sysDictData:add')" @click="$refs.addForm.add({typeId: typeId, parentId: scope.row.id, parentName: scope.row.value})">新增下级字段</a>
                </a-menu-item>
                <a-menu-item v-if="hasPerm('sysDictData:delete')">
                  <a-popconfirm placement="topRight" title="确认删除？" @confirm="() => sysDictDataDelete(scope.row)">
                    <a>删除</a>
                  </a-popconfirm>
                </a-menu-item>
              </a-menu>
            </a-dropdown>
          </template>
        </el-table-column>
      </el-table>
      <div  style="display: flex; flex-direction: row-reverse; margin-top: 16px">
        <Pagination
          :total="table.pageInfo.totalRows"
          :showTotal="table.pageInfo.showTotal"
          :showSizeChanger="table.pageInfo.showSizeChanger"
          :pageSizeOptions="table.pageInfo.pageSizeOptions"
          :defaultPageSize="table.pageInfo.pageSize"
          :defaultCurrent="table.pageInfo.pageNo"
          @showSizeChange="table.pageInfo.onShowSizeChange"
          @change="table.pageInfo.onChange"
          />
      </div>
      <add-form ref="addForm" @ok="handleOk" />
      <edit-form ref="editForm" @ok="handleOk" />
    </a-card>
  </a-modal>
</template>

<script>
  import { STable } from '@/components'
  import { sysDictDataPage, sysDictDataSearch, sysDictDataDelete, sysDictDataChildren } from '@/api/modular/system/dictDataManage'
  import { sysDictTypeDropDown } from '@/api/modular/system/dictManage'
  import addForm from './addForm'
  import editForm from './editForm'
  import { Table, TableColumn } from 'element-ui'
  import 'element-ui/lib/theme-chalk/index.css'
  import Pagination from 'ant-design-vue/es/pagination/Pagination'

  export default {
    components: {
      STable,
      addForm,
      editForm,
      elTable: Table,
      elTableColumn: TableColumn,
      Pagination
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
            title: '字典值',
            dataIndex: 'value'
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
            title: '备注',
            dataIndex: 'remark',
            width: 200
          },
          {
            title: '状态',
            dataIndex: 'status',
            scopedSlots: { customRender: 'status' }
          }
        ],
        table: {
          ids: [],
          data: [],
          pageInfo: {
            pageNo: 1,
            pageSize: 10,
            showSizeChanger: true, // 显示可改变每页数量
            pageSizeOptions: ['10', '20', '30', '40'], // 每页数量选项
            showTotal: (total, range) => {
              if (range[0] <= 0 && range[1] <= 0) {
                const num1 = _this.table.pageInfo.pageNo
                const num2 = _this.table.pageInfo.totalRows < _this.table.pageInfo.pageSize ? _this.table.pageInfo.totalRows : _this.table.pageInfo.pageSize
                return num1 + '-' + num2 + '共' + total + '条'
              } else {
                return range[0] + '-' + range[1] + '共' + total + '条'
              }
            },
            onShowSizeChange: (current, size) => {
              _this.table.pageInfo.pageSize = size
              _this.loadData()
            },
            onChange: (page, pageSize) => {
              _this.table.pageInfo.pageNo = page
              _this.table.pageInfo.pageSize = pageSize
              _this.loadData()
            }
          }
        },
        visible: false,
        typeId: [],
        selectedRowKeys: [],
        selectedRows: [],
        statusDict: [],
        symbolKey: '1',
        loadIndex: -1
      }
    },

    created () {
      this.sysDictTypeDropDown()
      if (this.hasPerm('sysDictData:edit') || this.hasPerm('sysDictData:delete')) {
        this.columns.push({
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        })
      }
    },
    methods: {
      loadData() {
        this.symbolKey = new Date().getTime().toString()
        this.loadIndex = -1
        this.table.ids = []
        this.queryParam.typeId = this.typeId
        if ((this.queryParam.value !== undefined && this.queryParam.value !== '') || (this.queryParam.code !== undefined && this.queryParam.code !== '')) {
          this.search()
          return
        }
        this.queryParam = {
          ...this.queryParam,
          pageNo: this.table.pageInfo.pageNo,
          pageSize: this.table.pageInfo.pageSize
        }
        sysDictDataPage(Object.assign(this.queryParam)).then((res) => {
          this.table.data = []
          res.data.rows.forEach(row => {
            row.parentId = 0
            row.hasChildren = true
          })
          this.table.data = res.data.rows
          this.table.pageInfo.pageNo = res.data.pageNo
          this.table.pageInfo.pageSize = res.data.pageSize
          this.table.pageInfo.totalPage = res.data.totalPage
          this.table.pageInfo.totalRows = res.data.totalRows
        })
      },
      search() {
        sysDictDataSearch(this.queryParam).then((res) => {
          if (res.data.pageResult.rows === null) {
            this.table.data = []
            return
          }
          this.table.data = []
          res.data.pageResult.rows.forEach(row => {
            row.parentId = 0
            row.hasChildren = true
          })
          this.table.data = res.data.pageResult.rows
          this.table.ids = res.data.ids
          this.table.pageInfo.pageNo = res.data.pageResult.pageNo
          this.table.pageInfo.pageSize = res.data.pageResult.pageSize
          this.table.pageInfo.totalPage = res.data.pageResult.totalPage
          this.table.pageInfo.totalRows = res.data.pageResult.totalRows
          this.$nextTick(() => {
            this.loadTree()
          })
        })
      },
      loadTree() {
        if (this.loadIndex + 1 < this.table.ids.length) {
          this.loadIndex += 1
          const childrenIds = this.table.ids[this.loadIndex]
          // 只筛选有子级的id
          if (childrenIds.length > 1) {
            this.table.data.forEach((item) => {
                // 比对当前数据行是否和最后一个id相等，如果相等，就让该行展开，最后一个id就是根节点
                if (item.id === childrenIds[childrenIds.length - 1]) {
                  this.toggle_child(item)
                }
            })
          } else {
            this.loadTree()
          }
        }
      },
      toggle_child(row) {
        this.$refs['table'].store.loadOrToggle(row)
      },
      // 打开此页面首先加载此方法
      index (record) {
        this.visible = true
        this.typeId = record.id
        this.queryParam.typeId = record.id
        try {
          this.loadData()
          // this.$refs.table.refresh()
        } catch (e) {
          // 首次进入界面，因表格加载顺序，会抛异常，我们不予理会
        }
      },

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

      handleCancel () {
        this.queryParam = {}
        this.visible = false
      },
      sysDictDataDelete (record) {
        sysDictDataDelete(record).then((res) => {
          if (res.success) {
            this.$message.success('删除成功')
            this.loadData()
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
        this.loadData()
      },
      onSelectChange (selectedRowKeys, selectedRows) {
        this.selectedRowKeys = selectedRowKeys
        this.selectedRows = selectedRows
      },
      load(tree, treeNode, resolve) {
        this.queryParam.parentId = tree.id
        sysDictDataChildren(this.queryParam).then((res) => {
          res.data.forEach(row => {
              row.hasChildren = true
          })
          resolve(res.data)
          this.$nextTick(() => {
            if (this.table.ids.length > 0) {
              let flag = true
              const idArr = this.table.ids[this.loadIndex]
                idArr.forEach((item, index) => {
                  const row = res.data.filter((e) => {
                    // 一个是命中的节点，所以不用展开
                    return item === e.id && index !== 0
                  })
                  if (row.length > 0) {
                    flag = false
                    this.toggle_child(row[0])
                  }
                })
              if (flag && (this.loadIndex < this.table.ids.length - 1)) {
                this.loadTree()
              }
            }
          })
        })
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
