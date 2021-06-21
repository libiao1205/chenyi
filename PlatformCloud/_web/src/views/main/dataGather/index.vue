<template>
  <a-card :bordered="false" >
    <a-spin :spinning="loading">
      <div class="table-page-search-wrapper">
        <a-form :form="form">
          <a-row :gutter="48">
            <a-form-item
              style="display: none;"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              has-feedback
            >
              <a-input :v-model="100" v-decorator="['sort']" />
            </a-form-item>
            <a-col :md="4" :sm="24">
              <span class="table-page-search-submitButtons">
                <a-upload
                  style="float: left;margin-right: 60px"
                  :action="uploadProps.action"
                  :headers="{Authorization: 'Bearer ' + token}"
                  :beforeUpload="beforeUpload"
                  :fileList="uploadProps.fileList"
                  :remove="removeFile"
                  @change="uploadProps.onChange">
                  <a-button type="primary" style="background-color: #23a2cd;border:1px solid #23a2cd" icon="upload">导入采集文件</a-button>
                </a-upload>
                <a-button type="primary"  v-if="hasPerm('dataGather:add')" @click="dataGatherAdd()">添加到采集列表</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <s-table
        ref="table"
        size="default"
        :columns="columns"
        :data="loadData"
        :alert="true"
        :showLoading="false"
        :rowKey="(record) => record.id"
      >
        <span slot="gatherType" slot-scope="text">
          {{ gatherStatusFilter(text) }}
        </span>
        <span slot="gatherStatus" slot-scope="text">
          <a-badge :status="text===0? 'processing':'default'" />
          <a>{{ statusFilter(text) }}</a>
        </span>
        <span slot="action" slot-scope="text, record">
          <a v-if="hasPerm('dataGather:updateGatherStatus')" @click="dataGatherUpdateGatherStatus(record)">{{ record.gatherType == 0 ? '自动' : '手动' }}</a>
          <a-divider type="vertical" v-if="hasPerm('dataGather:updateGatherStatus') & (hasPerm('dataGather:delete') || hasPerm('dataGather:exec') || hasPerm('dataGather:downFolder'))" />
          <a-dropdown v-if="hasPerm('dataGather:delete') || hasPerm('dataGather:exec')">
            <a class="ant-dropdown-link">
              更多 <a-icon type="down" />
            </a>
            <a-menu slot="overlay">

              <a-menu-item v-if="hasPerm('dataGather:exec')">
                <a-popconfirm v-if="record.gatherStatus == 1 || record.gatherStatus == 2" placement="topRight" title="已经执行过采集任务，确定再执行一次吗？" @confirm="() => exec(record)">
                  <a>立即执行</a>
                </a-popconfirm>
                <a v-if="record.gatherStatus != 1 && record.gatherStatus != 2" @click="exec(record)">立即执行</a>
              </a-menu-item>
              <a-menu-item v-if="hasPerm('dataGather:downFolder')">
                <a @click="downGatherContent(record)">下载采集内容</a>
              </a-menu-item>
              <a-menu-item v-if="hasPerm('dataGather:getGatherLog')">
                <a @click="getGatherLog(record)">查看采集日志</a>
              </a-menu-item>
              <a-menu-item v-if="hasPerm('dataGather:delete')">
                <a-popconfirm v-if="hasPerm('dataGather:delete')" placement="topRight" title="确认删除？" @confirm="() => dataGatherDelete(record)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </s-table>
      <show-log ref="showLog" @ok="setTiming" />
    </a-spin>
  </a-card>
</template>
<script>
  import { STable } from '@/components'
  import { getDataGatherPage, downGatherConetnt, dataGatherUpdateGatherStatus, dataGatherDelete, dataGatherExec, getGatherLog } from '@/api/modular/main/dataGather'
  import { sysDictTypeDropDown } from '@/api/modular/system/dictManage'
  import { Upload } from 'ant-design-vue/es'
  import showLog from './showLog'
  import { mapState } from 'vuex'
  export default {
    name: 'DataGather',
    computed: {
      ...mapState({
        token: state => state.user.token
      })
    },
    components: {
      showLog,
      STable,
      Upload
    },
    data () {
      const _this = this
      return {
        conten: [1, 1, 1],
        form: this.$form.createForm(this),
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 15 }
        },
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          return getDataGatherPage(Object.assign(parameter, this.queryParam)).then((res) => {
            return res.data
          })
        },
        submitData: {
          file: {},
          fileName: ''
        },
        // 表头
        columns: [
          {
            title: '文件名称',
            dataIndex: 'fileName'
          },
          {
            title: '执行方式',
            dataIndex: 'gatherType',
            scopedSlots: { customRender: 'gatherType' }
          },
          {
            title: '状态',
            dataIndex: 'gatherStatus',
            scopedSlots: { customRender: 'gatherStatus' }
          }
        ],
        uploadProps: {
          name: 'file',
          action: process.env.VUE_APP_API_BASE_URL + '/main/dataGather/exec',
          onChange(info) {
            if (info.file.status !== 'uploading') {
              console.log(info.file, info.fileList)
            }
            if (info.file.status === 'done') {
              _this.$message.success('执行成功')
            } else if (info.file.status === 'error') {
              _this.$message.error(`导入失败`)
            }
          },
          fileList: []
        },
        queryParam: {},
        loading: false,
        timing: null
      }
    },
    created () {
      this.sysDictTypeDropDown()
      if (this.hasPerm('dataGather:updateGatherStatus') || this.hasPerm('dataGather:delete') || this.hasPerm('dataGather:exec') || this.hasPerm('dataGather:downFolder')) {
        this.columns.push({
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        })
      }
      // 启动定时器
      this.setTiming()
    },
    beforeDestroy() {
      // 页面关闭时清除定时器
      clearInterval(this.timing)
    },
    methods: {
      /**
       * 新增数据采集项
       */
      dataGatherAdd() {
        if (Object.keys(this.submitData.file).length === 0) {
          this.$message.error('文件不许为空，请选择要上传的文件')
          return
        }
        this.loading = true
        const formData = new FormData()
        formData.append('file', this.submitData.file)
        this.$http.post(process.env.VUE_APP_API_BASE_URL + '/main/dataGather/add', formData,
          {
            headers: { 'Content-Type': 'multipart/form-data' },
            transformRequest: [
              function(d) {
                return d
              }
            ]
          }).then(res => {
          this.loading = false
          if (res.success) {
            this.$message.success('添加成功')
            this.$refs.table.refresh()
            this.removeFile()
          } else {
            this.$message.error('添加失败：' + res.message)
          }
        }).catch((err) => {
          this.$message.error('添加错误：' + err.message)
          this.loading = false
        })
      },
      /**
       * 更新数据采集项
       */
      dataGatherUpdateGatherStatus(record) {
        this.loading = true
        dataGatherUpdateGatherStatus(record).then((res) => {
          this.loading = false
          if (res.success) {
            this.$message.success('更新成功')
            this.$refs.table.refresh()
          } else {
            this.$message.error('更新失败：' + res.message)
          }
        }).catch((err) => {
          this.$message.error('更新错误：' + err.message)
          this.loading = false
        })
      },
      /**
       * 删除数据采集项
       */
      dataGatherDelete(record) {
        this.loading = true
        dataGatherDelete(record).then((res) => {
          this.loading = false
          if (res.success) {
            this.$message.success('删除成功')
            this.$refs.table.refresh()
          } else {
            this.$message.error('删除失败：' + res.message)
          }
        }).catch((err) => {
          this.$message.error('删除错误：' + err.message)
          this.loading = false
        })
      },
      // 执行已保存的采取项
      exec (record) {
        if (record.gatherStatus === 0) {
          this.$message.warning('正在采取中')
          return
        }
        this.loading = true
        dataGatherExec(record).then((res) => {
          this.loading = false
          if (res.success) {
            this.$message.success('开始采集，可通过采集日志查看进度')
            this.$refs.table.refresh()
          } else {
            this.$message.error('采集失败：' + res.message)
          }
        }).catch((err) => {
          this.$message.error('采集错误：' + err.message)
          this.loading = false
        })
      },
      // 获取采集日志
      getGatherLog (record) {
        getGatherLog(record).then((res) => {
          // 先清除本页面定时器
          clearInterval(this.timing)
          this.$refs.showLog.show(record, res)
          this.loading = false
        }).catch((err) => {
          this.$message.error('采集错误：' + err.message)
          this.loading = false
        })
      },
      // 下载采集结果
      downGatherContent(record) {
        if (record.gatherStatus === 0) {
          this.$message.warning('正在采取中，采取结束后才能下载')
        } else {
          const param = '?id=' + record.id + '&token=' + this.token
          downGatherConetnt(param)
        }
      },
      statusFilter (status) {
        if (status === 0) {
          return '正在采集'
        } else if (status === 1) {
          return '采集成功'
        } else if (status === 2) {
          return '采集失败'
        } else {
          return '未采集'
        }
      },
      gatherStatusFilter (gatherStatus) {
        if (gatherStatus === 0) {
          return '手动'
        } else {
          return '自动'
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
      // 上传之前
      beforeUpload(file, fileList) {
        console.log('fileList', file)
        this.submitData.fileName = file.name
        this.submitData.file = file
        this.uploadProps.fileList = [file]
        return false
      },
      removeFile() {
        this.submitData.fileName = {}
        this.submitData.file = {}
        this.uploadProps.fileList = []
        return true
      },
      setTiming() {
        this.timing = setInterval(() => {
          this.$refs.table.refresh()
        }, 3000)
      }
    }
  }
</script>
<style scoped>
.ant-upload-list-item-name{
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  width: 90%;
}
</style>
