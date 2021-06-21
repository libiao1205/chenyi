<template>
  <a-modal
    title="新增数据源"
    :width="900"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
    :maskClosable="false"
  >
    <template slot="footer">
      <a-button key="back" @click="handleCancel">
        关闭
      </a-button>
      <a-button key="connect" @click="testConnect" v-if="dataSourceTypeCode == getAsDataSourceCode() || dataSourceTypeCode == getFedoraDataSourceCode()">
        测试连接
      </a-button>
      <a-button key="submit" type="primary" @click="handleSubmit">
        确定
      </a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item
              label="数据源类型"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              has-feedback
            >
              <a-select style="width: 100%" placeholder="请选择数据源类型" v-decorator="['type', {rules: [{ required: true, message: '请选择数据源类型！' }]}]" >
                <a-select-option v-for="(item,index) in dataSourceType" :key="index" :value="item.code" @click="changeDataSourceType(item.code)">{{ item.value }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item
              label="别名"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              has-feedback
            >
              <a-input placeholder="请输入别名" v-decorator="['name', {rules: [{required: true, message: '请输入别名！'}]}]" />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item
              label="唯一编码"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              has-feedback
            >
              <a-input placeholder="请输入编码" v-decorator="['code', {rules: [{required: true, message: '请输入编码！'}]}]" />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24" v-for="(item,index) in dataSourceColumns" :key="index">
            <a-form-item
              :label="item.value"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              has-feedback
            >
              <div :key="symbolKey">
                <a-upload
                  style="float: left;margin-right: 60px"
                  :action="uploadProps.action"
                  :beforeUpload="beforeUpload"
                  @change="uploadProps.onChange">
                  <a-button style="background-color: #23a2cd;border:1px solid #23a2cd;color: whitesmoke" v-if="item.code == 'excelFile' || item.code == 'marcFile'" icon="upload">{{ item.value }}</a-button>
                </a-upload>
              </div>
              <a-input v-if="item.code != 'password' && item.code != 'marcFile' && item.code != 'excelFile'" :placeholder="'请输入'+item.value" v-decorator="[item.code, {rules: [{required: true, message: '请输入'+item.value+'！'}]}]" />
              <a-input v-if="item.code == 'password'" type="password" :placeholder="'请输入'+item.value" v-decorator="[item.code, {rules: [{required: true, message: '请输入'+item.value+'！'}]}]" />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item
              label="数据集"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              has-feedback
            >
              <a-select style="width: 100%" showSearch @search="onSearch" placeholder="请选择数据集" v-decorator="['dataCollectCode']" >
                <a-select-option v-for="(item,index) in dataCollectData" :key="index" :value="item.code">{{ item.value }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="排序"
            >
              <a-input-number placeholder="请输入排序" style="width: 100%" v-decorator="['sort', { initialValue: 100 }]" :min="1" :max="1000" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
import { dataSourceAdd, dataSourceTestConnect } from '@/api/modular/main/dataSource'
  import { mapState, mapGetters } from 'vuex'
  import { Transfer } from 'ant-design-vue/es'
import { getDataCollectList } from '@/api/modular/main/dataCollect'
  export default {
    components: {
      Transfer
    },
    computed: {
      ...mapState({
        dataSourceType: state => state.constant.dataSourceType,
        statusList: state => state.constant.statusList
      }),
      ...mapGetters({
        getDataSource: 'constant/getDataSource',
        getMarcDataSourceCode: 'constant/getMarcDataSourceCode',
        getExcelDataSourceCode: 'constant/getExcelDataSourceCode',
        getAsDataSourceCode: 'constant/getAsDataSourceCode',
        getFedoraDataSourceCode: 'constant/getFedoraDataSourceCode'
      })
    },
    data () {
      const _this = this
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 15 }
        },
        visible: false,
        confirmLoading: false,
        form: this.$form.createForm(this),
        dataSourceColumns: [],
        dataCollectData: [],
        targetKeys: [],
        onChange: (newTargetKeys, direction, moveKeys) => {
          this.targetKeys = newTargetKeys
        },
        render: (item) => {
          return item.title
        },
        uploadProps: {
          name: 'file',
          action: process.env.VUE_APP_API_BASE_URL + '/main/dataSource/exec',
          onChange(info) {
            if (info.file.status !== 'uploading') {
              console.log(info.file, info.fileList)
            }
            if (info.file.status === 'done') {
              _this.$message.success('执行成功')
            } else if (info.file.status === 'error') {
              _this.$message.error(`导入失败`)
            }
          }
        },
        submitData: {
          file: {},
          fileName: ''
        },
        symbolKey: '1',
        dataSourceTypeCode: ''
      }
    },
    methods: {
      // 初始化方法
      add () {
        this.visible = true
        this.init()
      },
      init(record) {
        this.targetKeys = []
        getDataCollectList().then((res) => {
          this.dataCollectData = []
          res.data.forEach(row => {
            const data = {
              code: row.code,
              value: row.name
            }
            this.dataCollectData.push(data)
          })
        })
      },
      uploadFile(values) {
        if (Object.keys(this.submitData.file).length === 0) {
          this.confirmLoading = false
          this.$message.error('文件不许为空,请选择要上传的文件！')
          return
        }
        const formData = new FormData()
        formData.append('file', this.submitData.file)
        formData.append('type', values.type)
        this.$http.post(process.env.VUE_APP_API_BASE_URL + '/main/dataSource/addFile', formData,
          {
            headers: { 'Content-Type': 'multipart/form-data' },
            transformRequest: [
              function(d) {
                return d
              }
            ]
          }).then(res => {
          if (res.success) {
            if (values.type === this.getExcelDataSourceCode()) {
              values.excelFile = res.data
            } else {
              values.marcFile = res.data
            }
            this.save(values)
          } else {
            this.confirmLoading = false
            this.$message.error(res.message)
          }
        }).catch((err) => {
          this.$message.error('添加错误：' + err.message)
          this.confirmLoading = false
        })
      },
      handleSubmit () {
        const { form: { validateFields } } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            if (values.type === this.getExcelDataSourceCode() || values.type === this.getMarcDataSourceCode()) {
              this.uploadFile(values)
            } else {
              this.save(values)
            }
          } else {
            this.confirmLoading = false
          }
        })
      },
      save(values) {
        // 数据源字段
        const arr = []
        if (!this.dataSourceColumns === undefined) {
          this.dataSourceColumns.forEach(item => {
            arr.push({ dataSourceCode: values.code, code: item.code, value: values[item.code] })
          })
        }
        values.dataSourceColumns = arr
        values.connect = 0

        // 数据集
        const collects = []
        console.log(this.targetKeys)
        this.targetKeys.forEach(collectCode => {
          collects.push({ dataSourceCode: values.code, dataCollectCode: collectCode })
        })
        values.dataSourceDataCollects = collects
        dataSourceAdd(values).then((res) => {
          this.confirmLoading = false
          if (res.success) {
            this.$message.success('新增成功')
            this.handleCancel()
            this.$emit('ok', values)
          } else {
            this.$message.error('新增失败：' + res.message)
          }
        }).finally((res) => {
          this.confirmLoading = false
        })
      },
      handleCancel () {
        this.form.resetFields()
        this.visible = false
      },
      testConnect() {
        const { form: { validateFields } } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            dataSourceTestConnect(values).then((res) => {
              if (res.data !== '') {
                this.$message.success('连接成功')
              } else {
                this.$message.error('连接失败,请检查连接属性')
              }
              this.confirmLoading = false
            }).finally((res) => {
              this.confirmLoading = false
            })
          } else {
            this.confirmLoading = false
          }
        })
      },
      changeDataSourceType(code) {
        this.dataSourceTypeCode = code
        this.symbolKey = new Date().getTime().toString()
        this.dataSourceColumns = this.getDataSource(code)
      },
      onSearch(val) {
        console.log(val)
      },
      // 上传之前
      beforeUpload(file, fileList) {
        this.submitData.fileName = file.name
        this.submitData.file = file
        // 返回false会阻止上传
        return false
      }
    }
  }
</script>
<style  lang="less">
  .ant-transfer-list{
    width: 328px;
    height: 400px;
  }
  .ant-upload-list-item-name{
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    width: 90%;
  }
</style>
