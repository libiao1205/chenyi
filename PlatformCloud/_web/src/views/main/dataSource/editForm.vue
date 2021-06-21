<template>
  <a-modal
    title="编辑数据源"
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
        <a-form-item
          style="display: none;"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input v-decorator="['id']" />
        </a-form-item>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item
              label="数据源类型"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              has-feedback
            >
              <a-select style="width: 100%" :disabled= "true " placeholder="请选择数据源类型" v-decorator="['type', {rules: [{ required: true, message: '请选择数据源类型！' }]}]" >
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
              <a-input placeholder="请输入编码" :disabled= "true " v-decorator="['code', {rules: [{required: true, message: '请输入编码！'}]}]" />
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
                  :beforeUpload="beforeUpload"
                  :fileList="uploadProps.fileList"
                  :remove="removeFile"
                  @change="uploadProps.onChange">
                  <a-button style="background-color: #23a2cd;border:1px solid #23a2cd;color: whitesmoke" v-if="item.code == 'excelFile' || item.code == 'marcFile'" icon="upload">{{ item.value }}</a-button>
                </a-upload>
              </div>
              <a-input v-if="item.code != 'password' && item.code != 'marcFile' && item.code != 'excelFile'" :placeholder="'请输入'+item.value" v-decorator="[item.code, {rules: [{required: true, message: '请输入'+item.value+'！'}]}]" />
              <a-input v-if="item.code == 'password'" type="password" :placeholder="'请输入'+item.value" v-decorator="[item.code, {rules: [{required: true, message: '请输入'+item.value+'！'}]}]" />
            </a-form-item>
            <a-form-item
              :label="item.value"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              has-feedback
              style="display: none"
            >
              <a-input v-if="item.code == 'marcFile'"  v-decorator="[item.code]" />
              <a-input v-if="item.code == 'excelFile'"  v-decorator="[item.code]" />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item
              label="数据集"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              has-feedback
            >
              <a-select style="width: 100%" showSearch placeholder="请选择数据集" v-decorator="['dataCollectCode']" >
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
  import {
    getFindColumnByCode,
    dataSourceEdit,
    getFile,
    dataSourceTestConnect
  } from '@/api/modular/main/dataSource'
  import { getDataCollectList } from '@/api/modular/main/dataCollect'
  import { mapGetters, mapState } from 'vuex'
  import { Transfer } from 'ant-design-vue/es'
  export default {
    components: {
      Transfer
    },
    computed: {
      ...mapState({
        statusList: state => state.constant.statusList,
        dataSourceType: state => state.constant.dataSourceType
      }),
      ...mapGetters({
        getDataSource: 'constant/getDataSource',
        getDataSourceTypeName: 'constant/getDataSourceTypeName',
        getMarcDataSourceCode: 'constant/getMarcDataSourceCode',
        getExcelDataSourceCode: 'constant/getExcelDataSourceCode',
        getAsDataSourceCode: 'constant/getAsDataSourceCode',
        getFedoraDataSourceCode: 'constant/getFedoraDataSourceCode',
        getMarcFileCode: 'constant/getMarcFileCode',
        getExcelFileCode: 'constant/getExcelFileCode'
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
        visibleDef: false,
        form: this.$form.createForm(this),
        dataSourceColumns: [],
        dataCollectData: [],
        render: (item) => {
          return item.title
        },
        uploadProps: {
          name: 'file',
          onChange(info) {
            _this.isNewFile = true
            if (info.file.status === 'done') {
              _this.$message.success('执行成功')
            } else if (info.file.status === 'error') {
              _this.$message.error(`导入失败`)
            }
          },
          // 已上传的文件
          fileList: []
        },
        submitData: {
          file: {},
          fileName: ''
        },
        // 是否上传新文件
        isNewFile: false,
        symbolKey: '1',
        dataSourceTypeCode: ''
      }
    },
    methods: {
      // 初始化方法
      edit (record) {
        this.isNewFile = false
        this.submitData.file = {}
        this.submitData.name = ''
        this.uploadProps.fileList = []
        this.symbolKey = new Date().getTime().toString()
        this.changeDataSourceType(record.type)
        this.visible = true
        getFindColumnByCode(record).then((res) => {
            const entity = {}
            entity.id = record.id
            entity.type = record.type
            entity.code = record.code
            entity.dataCollectCode = record.dataCollectCode
            entity.name = record.name
            entity.connect = record.connect
            entity.sort = record.sort
            this.dataSourceTypeCode = record.type
            res.data.forEach(item => {
              entity[item.code] = item.value
            })
            const arr = res.data.filter((item) => {
              return item.code === this.getMarcFileCode() || item.code === this.getExcelFileCode()
            })
            if (arr.length > 0) {
              // **********************获取已经上传的文件，在页面显示**********************
              getFile({ uri: arr[0].value }).then((res) => {
                if (res.data !== null) {
                  this.uploadProps.fileList = [ { name: res.data.filename, uid: new Date().getTime() } ]
                }
              })
            }
            this.form.setFieldsValue(
              entity
            )
        })
        this.init(record)
      },
      init(record) {
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
            this.update(values)
          } else {
            this.confirmLoading = false
            this.$message.error(res.message)
          }
        }).catch((err) => {
          this.$message.error('添加错误：' + err.message)
          this.confirmLoading = false
        })
      },
      update(values) {
        let flag = false
        // 数据源字段
        const arr = []
        if (this.dataSourceColumns !== undefined) {
          this.dataSourceColumns.forEach(item => {
            if (values[item.code] === null) {
              flag = true
            }
            arr.push({ dataSourceCode: values.code, code: item.code, value: values[item.code] })
          })
        }
        if (flag) {
          this.$message.error('编辑失败！')
          this.confirmLoading = false
          return
        }
        values.dataSourceColumns = arr
        values.connect = 0
        dataSourceEdit(values).then((res) => {
          if (res.success) {
            this.$message.success('编辑成功')
            this.visible = false
            this.confirmLoading = false
            this.$emit('ok', values)
          } else {
            this.$message.error('编辑失败：' + res.message)
          }
        }).finally((res) => {
          this.confirmLoading = false
        })
      },
      handleSubmit () {
        const { form: { validateFields } } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            if ((values.type === this.getExcelDataSourceCode() || values.type === this.getMarcDataSourceCode()) &&
              (Object.keys(this.submitData.file).length > 0 || this.isNewFile)) {
              this.uploadFile(values)
            } else {
              this.update(values)
            }
          } else {
            this.confirmLoading = false
          }
        })
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
      handleCancel () {
        this.form.resetFields()
        this.visible = false
      },
      changeDataSourceType(code) {
        this.dataSourceColumns = this.getDataSource(code)
      },
      // 上传之前
      beforeUpload(file, fileList) {
        this.submitData.fileName = file.name
        this.submitData.file = file
        this.uploadProps.fileList = [file]
        // 返回false会阻止上传
        return false
      },
      removeFile() {
        this.isNewFile = true
        this.submitData.fileName = {}
        this.submitData.file = {}
        this.uploadProps.fileList = []
        return true
      }
    }
  }
</script>
<style  lang="less">
  .ant-transfer-list{
    width: 328px;
    height: 400px;
  }
  .ant-upload-list{
    position: absolute;
  }
  .ant-upload-list-item-name{
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    width: 90%;
  }

</style>
