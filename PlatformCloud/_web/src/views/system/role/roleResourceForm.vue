<template>
  <a-modal
    title="授权资源"
    :width="900"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <div class="table-page-search-wrapper" v-if="hasPerm('sysDictData:page')">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="24">
            <a-form-item label="元数据类型" >
              <a-select style="width: 100%" v-model="queryParam.typeCode" placeholder="请选择元数据类型" >
                <a-select-option v-for="(item,index) in metadataTypeList" :key="index" :value="item.code">{{ item.value }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="6 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="{ float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="loadData">查询</a-button>
                <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
              </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <a-spin :spinning="formLoading">
      <el-table
        :data="table.data"
        :header-cell-style="{background:'#fafafa',color:'rgba(0, 0, 0, 0.85)',fontWeight:'500'}"
        style="width: 100%"
        row-key="id"
        :border="false"
        :key="symbolKey"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
        <el-table-column
          prop="name"
          label="元数据表"
          align="left">
        </el-table-column>
        <el-table-column
          prop="type"
          label="权限"
          align="left">
          <template slot-scope="scope">
            <a-checkbox  :checked="scope.row.permissionMap != undefined && scope.row.permissionMap.visible" @change="changePermission(scope.row,'visible')">
              可见
            </a-checkbox>
            <a-checkbox  :checked="scope.row.permissionMap != undefined && scope.row.permissionMap.read" @change="changePermission(scope.row,'read')">
              可浏览
            </a-checkbox>
            <a-checkbox  :checked="scope.row.permissionMap != undefined && scope.row.permissionMap.download" @change="changePermission(scope.row,'download')">
              可下载
            </a-checkbox>
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
    </a-spin>
  </a-modal>
</template>

<script>
  import { sysRoleGrantMetadataColumn } from '@/api/modular/system/roleManage'
  import { sysDictTypeDropDown } from '@/api/modular/system/dictManage'
  import { metadataTableFindColumnTree } from '@/api/modular/main/metadataTable'
  import { Table, TableColumn } from 'element-ui'
  import 'element-ui/lib/theme-chalk/index.css'
  import Pagination from 'ant-design-vue/es/pagination/Pagination'
  export default {
    components: {
      elTable: Table,
      elTableColumn: TableColumn,
      Pagination
    },
    data () {
      const _this = this
      return {
        // 查询参数
        queryParam: {},
        labelCol: {
          style: { 'padding-right': '20px' },
          xs: { span: 24 },
          sm: { span: 5 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 15 }
        },
        visible: false,
        confirmLoading: false,
        formLoading: true,
        form: this.$form.createForm(this),
        metadataTypeList: [],
        submitData: [],
        roleCode: '',
        symbolKey: '1',
        table: {
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
        }
      }
    },
    methods: {
      // 初始化方法
      roleResource (record) {
        this.visible = true
        this.formLoading = false
        this.roleCode = record.code
        this.queryParam = {}
        this.sysDictTypeDropDown()
        this.$nextTick(() => {
          this.loadData()
        })
      },

      /**
       * 获取字典数据
       */
      sysDictTypeDropDown () {
        sysDictTypeDropDown({ code: 'metadata_type' }).then((res) => {
          this.metadataTypeList = []
          res.data.forEach(item => {
            if (item.parentId === null || item.parentId === '0') {
              this.metadataTypeList.push(item)
            }
          })
        })
      },

      /**
       * 此角色已有资源列表
       */
      loadData () {
        this.symbolKey = new Date().getTime().toString()
        this.queryParam.pageNo = this.table.pageInfo.pageNo
        this.queryParam.pageSize = this.table.pageInfo.pageSize
        this.queryParam.roleCode = this.roleCode
        metadataTableFindColumnTree(this.queryParam).then((res) => {
          res.data.rows.forEach(item => {
            item.permissionMap = { visible: false, read: false, download: false }
            if (item.children !== undefined && item.children.length > 0) {
              item.children.forEach(e => {
                if (e.permissionMap.visible) {
                  item.permissionMap.visible = true
                }
                if (e.permissionMap.read) {
                  item.permissionMap.read = true
                }
                if (e.permissionMap.download) {
                  item.permissionMap.download = true
                }
              })
            }
          })
          this.table.data = res.data.rows
          this.table.pageInfo.pageNo = res.data.pageNo
          this.table.pageInfo.pageSize = res.data.pageSize
          this.table.pageInfo.totalPage = res.data.totalPage
          this.table.pageInfo.totalRows = res.data.totalRows
        })
      },
      handleSubmit () {
        this.submitData = []
        this.table.data.forEach(item => {
          if (item.children !== undefined && item.children.length > 0) {
            this.getSubmitData(item.children)
          }
        })
        sysRoleGrantMetadataColumn({ code: this.roleCode, grantMetadataPermissionList: this.submitData }).then((res) => {
          this.confirmLoading = false
          if (res.success) {
            this.$message.success('授权成功')
            this.handleCancel()
          } else {
            this.$message.error('授权失败：' + res.message)
          }
        }).finally((res) => {
          this.confirmLoading = false
        })
      },
      getSubmitData(children) {
        children.forEach(item => {
          const num = (item.permissionMap.visible ? 1 : 0) + (item.permissionMap.read ? 2 : 0) + (item.permissionMap.download ? 128 : 0)
          if (num > 0) {
            const data = { metadataColumnCode: item.code, permission: num }
            this.submitData.push(data)
          }
          if (item.children !== undefined) {
            this.getSubmitData(item.children)
          }
        })
      },
      handleCancel () {
        this.form.resetFields()
        this.visible = false
      },
      changePermission(row, type) {
        let flag
        if (type === 'visible') {
          row.permissionMap.visible = !row.permissionMap.visible
          flag = row.permissionMap.visible
        } else if (type === 'read') {
          row.permissionMap.read = !row.permissionMap.read
          flag = row.permissionMap.read
        } else if (type === 'download') {
          row.permissionMap.download = !row.permissionMap.download
          flag = row.permissionMap.download
        }
        if (row.children !== undefined) {
          this.findChildren(row.children, type, flag)
        }
      },
      findChildren(children, type, flag) {
        children.forEach(item => {
          if (type === 'visible') {
            item.permissionMap.visible = flag
          } else if (type === 'read') {
            item.permissionMap.read = flag
          } else if (type === 'download') {
            item.permissionMap.download = flag
          }
          if (item.children !== undefined) {
            this.findChildren(item.children, type, flag)
          }
        })
      }
    }
  }
</script>
