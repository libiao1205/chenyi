<template>
  <a-card :bordered="false" >
    <a style="position: relative;margin-left: -6px;top:-14px;" href="javascript:history.back(-1)">
      <svg
        width="1.4rem"
        height="1.4rem"
        t="1618391263236"
        class="icon"
        viewBox="0 0 1024 1024"
        version="1.1"
        xmlns="http://www.w3.org/2000/svg"
        p-id="1784" >
        <path d="M695.6 256.6H405.9V101.2c0-16.2-11.4-22.7-25.3-14.6l-370 213.7c-14 8-14 21.1 0 29.2l370 213.6c13.9 8 25.3 1.5 25.3-14.6V373.2h289.7c116.8 0 211.8 95 211.8 211.9s-95 212-211.8 212H222c-32.3 0-58.3 26-58.3 58.3s26 58.4 58.3 58.4h473.6c181.1 0 328.5-147.4 328.5-328.6 0-181.3-147.4-328.6-328.5-328.6z" fill="#2c2c2c" p-id="1785"></path>
      </svg>
    </a>
    <a-spin :spinning="loading">
      <div class="table-page-search-wrapper" v-if="hasPerm('metadata:metadataColumn:page')">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="名称">
                <a-input v-model="queryParam.name" allow-clear placeholder="请输入类型名称"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="唯一编码">
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
      <div class="table-operator" v-if="hasPerm('metadata:metadataColumn:add')" >
        <a-button type="primary" v-if="hasPerm('metadata:metadataColumn:add')" icon="plus" @click="$refs.addForm.add({typeCode: typeCode, tableCode: tableCode, tableName: tableName})">新增元数据</a-button>
      </div>
      <el-table
        ref="table"
        :data="table.data"
        :header-cell-style="{background:'#fafafa',color:'rgba(0, 0, 0, 0.85)',fontWeight:'500'}"
        style="width: 100%"
        row-key="id"
        :border="false"
        :key="symbolKey"
        :expand-row-keys="[0]"
        lazy
        :load="load"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
        <el-table-column
          label="名称"
          prop="name"
          align="left">
          <template slot-scope="scope">
            <span @click="toggle_child(scope.row)">{{ scope.row.name }} </span>
          </template>
        </el-table-column>
        <el-table-column
          prop="code"
          label="唯一编码"
          align="left">
        </el-table-column>
        <el-table-column
          prop="tableName"
          label="元数据表"
          align="left">
        </el-table-column>
        <el-table-column
          prop="dataTypeValue"
          label="数据类型"
          align="left">
          <template slot-scope="scope">
            <span>{{ dataTypeFilter(scope.row.dataTypeCode) }} </span>
          </template>
        </el-table-column>
        <el-table-column
          prop="length"
          label="字段长度"
          align="left">
        </el-table-column>
        <el-table-column
          prop="isNull"
          label="是否非空"
          align="left">
          <template slot-scope="scope">
            <span>{{ scope.row.isNull == 0 ? '否' : '是' }} </span>
          </template>
        </el-table-column>
        <el-table-column
          prop="sort"
          label="排序"
          align="left">
        </el-table-column>
        <el-table-column
          prop="moreColumnStr"
          label="是否多列"
          align="left">
        </el-table-column>
        <el-table-column
          v-if="table.isAction"
          label="操作"
          width="140"
          align="left">
          <template slot-scope="scope">
            <a v-if="hasPerm('metadata:metadataColumn:edit')" @click="$refs.editForm.edit({typeCode: typeCode, tableCode: tableCode, tableName: tableName, scope:scope.row})">编辑</a>
            <a-divider type="vertical" v-if="hasPerm('metadata:metadataColumn:edit') & (hasPerm('metadata:metadataColumn:delete') || hasPerm('metadata:metadataColumn:add'))" />
            <a-dropdown v-if="hasPerm('metadata:metadataColumn:delete') || hasPerm('metadata:metadataColumn:add')">
              <a class="ant-dropdown-link">
                更多 <a-icon type="down" />
              </a>
              <a-menu slot="overlay">
                <a-menu-item v-if="hasPerm('metadata:metadataColumn:add') && dataTypeFilter(scope.row.dataTypeCode) == 'json'">
                  <a v-if="hasPerm('metadata:metadataColumn:add')" @click="$refs.addForm.add({typeCode: typeCode, tableCode: tableCode, tableName: tableName, code:scope.row.code, name:scope.row.name})">新增下级字段</a>
                </a-menu-item>
                <a-menu-item v-if="hasPerm('sysDictData:treeByCode')">
                  <a v-if="hasPerm('sysDictData:treeByCode')" @click="$refs.metadataConstraintForm.metadataConstraint({code:scope.row.code})">添加字段约束</a>
                </a-menu-item>
                <a-menu-item v-if="hasPerm('metadata:metadataColumn:delete')">
                  <a-popconfirm placement="topRight" title="确认删除？" @confirm="() => metadataTypeDelete(scope.row)">
                    <a>删除</a>
                  </a-popconfirm>
                </a-menu-item>
              </a-menu>
            </a-dropdown>
          </template>
        </el-table-column>
      </el-table>
      <div style="display: flex; flex-direction: row-reverse; margin-top: 16px">
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
      <metadata-constraint-form ref="metadataConstraintForm" />
    </a-spin>
  </a-card>
</template>

<script>
  import editForm from './editForm'
  import addForm from './addForm'
  import metadataConstraintForm from './metadataConstraintForm'
  import { Table, TableColumn } from 'element-ui'
  import 'element-ui/lib/theme-chalk/index.css'
  import Pagination from 'ant-design-vue/es/pagination/Pagination'
  import { metadataColumnPage, metadataColumnSearchPage, metadataColumnChildrenList, metadataColumnDelete } from '@/api/modular/main/metadataColumn'
  import { sysDictDataChildren } from '@/api/modular/system/dictDataManage'
  export default {
    name: 'Index',
    components: {
      editForm,
      addForm,
      metadataConstraintForm,
      elTable: Table,
      elTableColumn: TableColumn,
      Pagination
    },
    data() {
      const _this = this
      return {
        // 高级搜索 展开/关闭
        advanced: false,
        loading: false,
        // 查询参数
        queryParam: {},
        table: {
          data: [],
          ids: [],
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
          },
          isAction: false
        },
        symbolKey: '1',
        dataTypeList: [],
        typeCode: '',
        tableCode: '',
        tableName: '',
        loadIndex: -1
      }
    },
    mounted() {
      this.typeCode = this.$route.query.typeCode
      this.tableCode = this.$route.query.tableCode
      this.tableName = this.$route.query.tableName
      this.loadData()
    },
    created() {
      if (this.hasPerm('metadata:metadataColumn:edit') || this.hasPerm('metadata:metadataColumn:delete') || this.hasPerm('metadata:metadataColumn:add')) {
        this.table.isAction = true
      }
    },
    methods: {
      loadData() {
        this.symbolKey = new Date().getTime().toString()
        this.loadIndex = -1
        this.table.ids = []
        this.initDataType()
        this.queryParam.tableCode = this.tableCode
        if ((this.queryParam.name !== undefined && this.queryParam.name !== '') || (this.queryParam.code !== undefined && this.queryParam.code !== '')) {
          this.search()
          return
        }
        this.queryParam.pageNo = this.table.pageInfo.pageNo
        this.queryParam.pageSize = this.table.pageInfo.pageSize
        metadataColumnPage(this.queryParam).then((res) => {
          res.data.rows.forEach((item) => {
            item.moreColumnStr = item.moreColumn === 1 ? '是' : '否'
            item.hasChildren = true
            item.tableName = this.tableName
          })
          this.table.data = res.data.rows
          this.table.pageInfo.pageNo = res.data.pageNo
          this.table.pageInfo.pageSize = res.data.pageSize
          this.table.pageInfo.totalPage = res.data.totalPage
          this.table.pageInfo.totalRows = res.data.totalRows
        })
      },
      search() {
        metadataColumnSearchPage(this.queryParam).then((res) => {
          if (res.data.pageResult.rows === null) {
            this.table.data = []
            return
          }
          res.data.pageResult.rows.forEach((item) => {
            item.moreColumnStr = item.moreColumn === 1 ? '是' : '否'
            const type = this.dataTypeList.filter((e) => {
              return e.code === item.dataTypeCode
            })
            item.hasChildren = true
            item.tableName = this.tableName
            if (type.length > 0) {
              item.dataTypeValue = type[0].value
            }
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
      // 打开此页面首先加载此方法
      load(tree, treeNode, resolve) {
        metadataColumnChildrenList({ parentCode: tree.code }).then((res) => {
          res.data.forEach((item) => {
            item.moreColumnStr = item.moreColumn === 1 ? '是' : '否'
            const type = this.dataTypeList.filter((e) => {
              return e.code === item.dataTypeCode
            })
            item.hasChildren = true
            item.tableName = this.tableName
            if (type.length > 0) {
              item.dataTypeValue = type[0].value
            }
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
      },
      toggle_child(row) {
        this.$refs['table'].store.loadOrToggle(row)
      },
      handleOk () {
        this.loadData()
      },
      metadataTypeDelete(record) {
        metadataColumnDelete(record).then((res) => {
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
      handleCancel () {
        this.queryParam = {}
      },
      initDataType() {
        sysDictDataChildren({ typeId: 0, code: this.typeCode }).then((res) => {
          res.data.forEach((row) => {
            this.dataTypeList.push({ id: row.id, code: row.code, value: row.value })
          })
        })
      },
      dataTypeFilter(dataType) {
        const type = this.dataTypeList.filter((e) => {
          return e.code === dataType
        })
        if (type.length > 0) {
          return type[0].value
        }
      }
    }
  }
</script>

<style scoped>
</style>
